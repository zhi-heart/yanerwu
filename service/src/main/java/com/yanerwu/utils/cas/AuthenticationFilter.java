package com.yanerwu.utils.cas;

import org.jasig.cas.client.authentication.DefaultGatewayResolverImpl;
import org.jasig.cas.client.authentication.GatewayResolver;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;

import com.alibaba.fastjson.JSON;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;

public class AuthenticationFilter extends AbstractCasFilter {
	
    /**
     * The URL to the CAS Server login.
     */
    private String casServerLoginUrl;

    /**
     * Whether to send the renew request or not.
     */
    private boolean renew = false;

    /**
     * Whether to send the gateway request or not.
     */
    private boolean gateway = false;
    
    /**
     * 过滤地址集合
     */
    private Set<String> exclusionSet = null;
	
    /**
     * 过滤地址
     */
	private String exclusions = null;
    
    private GatewayResolver gatewayStorage = new DefaultGatewayResolverImpl();

    protected void initInternal(final FilterConfig filterConfig) throws ServletException {
        if (!isIgnoreInitConfiguration()) {
            super.initInternal(filterConfig);
            //获取需要过滤拦截地址,走cas 配置文件
            ResourceBundle sys=ResourceBundle.getBundle("cas");
            
            setCasServerLoginUrl(sys.getString("cas.server.login.url"));
            log.trace("Loaded CasServerLoginUrl parameter: " + this.casServerLoginUrl);
            setRenew(parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));
            log.trace("Loaded renew parameter: " + this.renew);
            setGateway(parseBoolean(getPropertyFromInitParams(filterConfig, "gateway", "false")));
            log.trace("Loaded gateway parameter: " + this.gateway);
            super.setServerName(sys.getString("local.server.name"));
            
            setExclusions(sys.getString("exclusions"));
            if((exclusions != null) && exclusions.trim().length() > 0) {
    			String[] exclusionArray = exclusions.split(",");
    			if(exclusionArray != null && exclusionArray.length > 0){
    				exclusionSet = new HashSet<String>();
    				for (String exclusionUrl : exclusionArray) {
    					exclusionSet.add(exclusionUrl);
    				}
    			}
    		}
            
            final String gatewayStorageClass = getPropertyFromInitParams(filterConfig, "gatewayStorageClass", null);

            if (gatewayStorageClass != null) {
                try {
                    this.gatewayStorage = (GatewayResolver) Class.forName(gatewayStorageClass).newInstance();
                } catch (final Exception e) {
                    log.error(e,e);
                    throw new ServletException(e);
                }
            }
        }
    }

    public void init() {
        super.init();
        CommonUtils.assertNotNull(this.casServerLoginUrl, "casServerLoginUrl cannot be null.");
    }

    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final HttpSession session = request.getSession(false);
        final Assertion assertion = session != null ? (Assertion) session.getAttribute(CONST_CAS_ASSERTION) : null;
        
        //排除接口
        if(isExclusion(request)){
        	filterChain.doFilter(request, response);
			return;
		}
        
        if (assertion != null) {
            filterChain.doFilter(request, response);
            return;
        }

        final String serviceUrl = constructServiceUrl(request, response);
        final String ticket = CommonUtils.safeGetParameter(request,getArtifactParameterName());
        final boolean wasGatewayed = this.gatewayStorage.hasGatewayedAlready(request, serviceUrl);

        if (CommonUtils.isNotBlank(ticket) || wasGatewayed) {
            filterChain.doFilter(request, response);
            return;
        }

        final String modifiedServiceUrl;

        log.debug("no ticket and no assertion found");
        if (this.gateway) {
            log.debug("setting gateway attribute in session");
            modifiedServiceUrl = this.gatewayStorage.storeGatewayInformation(request, serviceUrl);
        } else {
            modifiedServiceUrl = serviceUrl;
        }

        if (log.isDebugEnabled()) {
            log.debug("Constructed service url: " + modifiedServiceUrl);
        }
        
        final String urlToRedirectTo = CommonUtils.constructRedirectUrl(this.casServerLoginUrl, getServiceParameterName(), modifiedServiceUrl, this.renew, this.gateway);

        if (log.isDebugEnabled()) {
            log.debug("redirecting to \"" + urlToRedirectTo + "\"");
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(request.getHeader("X-Requested-With") != null  
        		&& "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())){
        	response.setCharacterEncoding("utf-8");
    		try {
    			Map<String, Object> map=new HashMap<>();
    			map.put("statusCode", 302);
    			map.put("message", "session已过期,请重新登录!");
    			PrintWriter out = response.getWriter();
    			out.print(JSON.toJSONString(map));
    			out.close();
    		} catch (IOException e) {
    			
    		}
        }else{
        	response.sendRedirect(urlToRedirectTo);
        }
    }
    
    /**
     * 判断请求地址是否拦截
     * @param request
     * @return
     * @throws IOException
     * @throws ServletException
     */
    private boolean isExclusion(HttpServletRequest request) throws IOException,
	ServletException {
		String servletPath = request.getServletPath();
		//返回true不需要拦截,返回false需要拦截
		if(exclusionSet == null) {
			return true;
		}
		for (String path : exclusionSet) {
			if(Pattern.compile(path).matcher(servletPath).find())
			return true;
		}
		
		return false;
	}

    public final void setRenew(final boolean renew) {
        this.renew = renew;
    }

    public final void setGateway(final boolean gateway) {
        this.gateway = gateway;
    }

    public final void setCasServerLoginUrl(final String casServerLoginUrl) {
        this.casServerLoginUrl = casServerLoginUrl;
    }
    
    public final void setGatewayStorage(final GatewayResolver gatewayStorage) {
    	this.gatewayStorage = gatewayStorage;
    }

	public void setExclusions(String exclusions) {
		this.exclusions = exclusions;
	}
}
