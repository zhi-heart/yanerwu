package com.yanerwu.action;

import com.alibaba.fastjson.JSON;
import com.yanerwu.common.Page;
import com.yanerwu.entity.Upower;
import com.yanerwu.service.LogService;
import com.yanerwu.service.UpowerService;
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
 * @Description Upower
 */@Component
@RequestMapping(value = "/Upower")
public class UpowerAction extends BaseAction{

    private Logger logger = LogManager.getLogger(getClass());

	//navTab rel="Upower"
	private static final String NAV_TAB_REL="Upower";

	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;

	@Autowired
	private UpowerService upowerService;
	@Autowired
	private LogService logService;

	private Upower upower=new Upower();

	private boolean bool=false;
	private String message=DWZ_STATUS_SUCCESS;

    /**
     * 执行搜索
     *
     * @return
     */
    @RequestMapping(value = "/list.html")
    public String list(Upower upower, Page page) {
        attribute();
        page = upowerService.findPage(upower, page);
        request.setAttribute("page", page);
        return "Upower/list";
    }

	/**
	 * 进入更新页面
	 * @return
	 */
	@RequestMapping(value = "/info.html", method = RequestMethod.GET)
	public String infoShow(Upower upower) {

        upower = upowerService.getById(upower);
        request.setAttribute("entity", upower);
        return "Upower/info";
	}

	/**
	 * 保存更新
	 */
    @ResponseBody
	@RequestMapping(value = "/info.html", method = RequestMethod.POST)
	public String infoEdit(Upower upower) {
		try {
            if(Tools.isEmpty(upower.getId())){
                upowerService.save(upower);
            }else{
                        this.upower=upowerService.getById(upower);
                        this.upower.setName(upower.getName());
                        this.upower.setPowerPath(upower.getPowerPath());
                        this.upower.setParentUpowerId(upower.getParentUpowerId());
                        this.upower.setPowerType(upower.getPowerType());
                        this.upower.setRemark(upower.getRemark());
            }
			upowerService.update(this.upower);
			this.bool=true;
		} catch (Exception e) {
            message = String.format("%s 错误:[%s]", DWZ_STATUS_ERROR, e.getMessage());
			logger.error("", e);
		}finally {
            //记录日志
            logService.put(request, "Upower > 修改", JSON.toJSONString(upower), bool ? 0 : 1);
            //返回信息
            return retImessage(bool ? DWZ_STATUS_SUCCESS_CODE : DWZ_STATUS_ERROR_CODE, message, bool);
		}
	}


	/**删除对象*/
    @ResponseBody
	@RequestMapping(value = "/u_power/delete.html", method = RequestMethod.POST)
	public String delete(Integer [] items) {
		try {
			for(int i = 0; i < items.length; i++) {
				upowerService.delete(new Upower(items[i]));
			}
			bool=true;
		} catch (Exception e) {
			message=String.format("%s 错误:[%s]", DWZ_STATUS_ERROR,e.getMessage());
			logger.error("", e);
		}finally {
            //记录日志
            logService.put(request, "Upower > 删除", JSON.toJSONString(items), bool ? 0 : 1);
            //返回信息
            return retImessage(bool ? DWZ_STATUS_SUCCESS_CODE : DWZ_STATUS_ERROR_CODE, message, false);
		}
	}
}