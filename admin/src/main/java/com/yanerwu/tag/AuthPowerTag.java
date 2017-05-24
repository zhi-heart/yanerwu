package com.yanerwu.tag;

import com.yanerwu.cache.PowerCache;
import com.yanerwu.entity.Uuser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class AuthPowerTag extends TagSupport {
    public final static String INCLUDEPOWER_YES = "YES";
    public final static String INCLUDEPOWER_NO = "NO";
    private static final long serialVersionUID = 1L;
    private String powerPath;
    private String includePower = INCLUDEPOWER_YES;

    public String getIncludePower() {
        return includePower;
    }

    public void setIncludePower(String includePower) {
        this.includePower = includePower;
    }

    public String getPowerPath() {
        return powerPath;
    }

    public void setPowerPath(String powerPath) {
        this.powerPath = powerPath;
    }

    @Override
    public int doStartTag() throws JspException {
        // TODO Auto-generated method stub
        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();

        String loginName = request.getUserPrincipal().getName();

        Uuser user = PowerCache.USERS_OF_LOGIN_NAME_MAP.get(loginName);
        if (user != null) {
            //用户权限是否包含此权限
            if (user.getPowerPaths().containsKey(powerPath)) {
                //如果判断的 是包含此权限才展示
                if (INCLUDEPOWER_YES.equalsIgnoreCase(includePower))
                    return Tag.EVAL_BODY_INCLUDE;
                else
                    return Tag.SKIP_BODY;
            }
        }
        return Tag.SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return Tag.EVAL_PAGE;
    }


}
