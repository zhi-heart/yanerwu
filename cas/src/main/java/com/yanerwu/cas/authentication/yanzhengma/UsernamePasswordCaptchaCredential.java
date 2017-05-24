package com.yanerwu.cas.authentication.yanzhengma;

import org.jasig.cas.authentication.UsernamePasswordCredential;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author Zuz
 * @Date 2017/5/21
 * @Description 登录vo
 */
public class UsernamePasswordCaptchaCredential extends UsernamePasswordCredential {

    /**
     *
     */
    private static final long serialVersionUID = 8997403977917163501L;

    @NotNull
    @Size(min = 1, message = "required.captcha")
    private String captcha;

    @NotNull
    @Size(min = 1, message = "required.serviceBrand")
    private String serviceBrand;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getServiceBrand() {
        return serviceBrand;
    }

    public void setServiceBrand(String serviceBrand) {
        this.serviceBrand = serviceBrand;
    }


}
