package com.yanerwu.cas.authentication.validate;

import org.jasig.cas.authentication.Credential;
import org.jasig.cas.web.flow.AuthenticationViaFormAction;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.binding.message.MessageContext;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Zuz
 * @Date 2017/5/23 19:17
 * @Description 登录成功之后验证, 暂时不用
 */
public class IpAuthenticationViaFormAction extends AuthenticationViaFormAction {

    public final Event validatorCaptcha(final RequestContext context, final Credential credential,
                                        final MessageContext messageContext) {


        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);

        return new Event(this, SUCCESS);
    }
}
