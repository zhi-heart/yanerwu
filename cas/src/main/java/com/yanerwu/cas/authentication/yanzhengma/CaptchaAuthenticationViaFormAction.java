package com.yanerwu.cas.authentication.yanzhengma;

import org.jasig.cas.authentication.Credential;
import org.jasig.cas.web.flow.AuthenticationViaFormAction;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.util.StringUtils;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author Zuz
 * @Date 2017/5/21
 * @Description 验证码验证
 */
public class CaptchaAuthenticationViaFormAction extends AuthenticationViaFormAction {

    public final Event validatorCaptcha(final RequestContext context, final Credential credential,
                                        final MessageContext messageContext) {

        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);
        HttpSession session = request.getSession();
        String captcha = (String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        session.removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

        UsernamePasswordCaptchaCredential upc = (UsernamePasswordCaptchaCredential) credential;
        String submitAuthcodeCaptcha = upc.getCaptcha();


        if (!StringUtils.hasText(submitAuthcodeCaptcha)) {
            messageContext.addMessage(new MessageBuilder().error().code("required.captcha").build());
            return new Event(this, ERROR);
        }
        if (submitAuthcodeCaptcha.equals(captcha)) {
            return new Event(this, SUCCESS);
        }

        messageContext.addMessage(new MessageBuilder().error().code("error.authentication.captcha.bad").build());

        return new Event(this, ERROR);
    }

}
