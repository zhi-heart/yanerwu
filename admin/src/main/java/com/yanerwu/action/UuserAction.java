package com.yanerwu.action;

import com.alibaba.fastjson.JSON;
import com.yanerwu.common.Page;
import com.yanerwu.entity.Uuser;
import com.yanerwu.service.LogService;
import com.yanerwu.service.UuserService;
import com.yanerwu.utils.Tools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Zuz
 * @version 1.0
 * @Description Uuser
 */
@Component
@RequestMapping(value = "/Uuser")
public class UuserAction extends BaseAction {

    //默认多列排序,example: username desc,createTime asc
    protected static final String DEFAULT_SORT_COLUMNS = null;
    //navTab rel="Uuser"
    private static final String NAV_TAB_REL = "Uuser";
    private Logger logger = LogManager.getLogger(getClass());
    @Autowired
    private UuserService uuserService;
    @Autowired
    private LogService logService;

    private Uuser uuser = new Uuser();

    private boolean bool = false;
    private String message = DWZ_STATUS_SUCCESS;

    /**
     * 执行搜索
     *
     * @return
     */
    @RequestMapping(value = "/list.html")
    public String list(Uuser uuser, Page page) {
        attribute();
        page = uuserService.findPage(uuser, page);
        request.setAttribute("page", page);
        return "Uuser/list";
    }

    /**
     * 进入更新页面
     *
     * @return
     */
    @RequestMapping(value = "/info.html", method = RequestMethod.GET)
    public String infoShow(Uuser uuser) {

        uuser = uuserService.getById(uuser);
        request.setAttribute("entity", uuser);
        return "Uuser/info";
    }

    /**
     * 保存更新
     */
    @ResponseBody
    @RequestMapping(value = "/info.html", method = RequestMethod.POST)
    public String infoEdit(Uuser uuser) {
        try {
            if (Tools.isEmpty(uuser.getId())) {
                uuserService.save(uuser);
            } else {
                this.uuser = uuserService.getById(uuser);
                this.uuser.setLoginName(uuser.getLoginName());
                this.uuser.setLoginPwd(uuser.getLoginPwd());
                this.uuser.setName(uuser.getName());
                this.uuser.setPhone(uuser.getPhone());
                this.uuser.setCreateDate(uuser.getCreateDate());
                this.uuser.setValid(uuser.getValid());
            }
            uuserService.update(this.uuser);
            this.bool = true;
        } catch (Exception e) {
            message = String.format("%s 错误:[%s]", DWZ_STATUS_ERROR, e.getMessage());
            logger.error("", e);
        } finally {
            //记录日志
            logService.put(request, "Uuser > 修改", JSON.toJSONString(uuser), bool ? 0 : 1);
            //返回信息
            return retImessage(bool ? DWZ_STATUS_SUCCESS_CODE : DWZ_STATUS_ERROR_CODE, message, bool);
        }
    }


    /**
     * 删除对象
     */
    @ResponseBody
    @RequestMapping(value = "/delete.html", method = RequestMethod.POST)
    public String delete(Integer[] items) {
        try {
            for (int i = 0; i < items.length; i++) {
                uuserService.delete(new Uuser(items[i]));
            }
            bool = true;
        } catch (Exception e) {
            message = String.format("%s 错误:[%s]", DWZ_STATUS_ERROR, e.getMessage());
            logger.error("", e);
        } finally {
            //记录日志
            logService.put(request, "Uuser > 删除", JSON.toJSONString(items), bool ? 0 : 1);
            //返回信息
            return retImessage(bool ? DWZ_STATUS_SUCCESS_CODE : DWZ_STATUS_ERROR_CODE, message, false);
        }
    }
}