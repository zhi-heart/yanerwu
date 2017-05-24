<#include "/custom.include">
<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign actionExtension = "html">
package ${basepackage}.action;

import com.alibaba.fastjson.JSON;
import com.yanerwu.common.Page;
import com.yanerwu.entity.${className};
import com.yanerwu.service.LogService;
import com.yanerwu.service.${className}Service;
import com.yanerwu.utils.Tools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

<#include "/java_imports.include"/>
@Component
@RequestMapping(value = "/${className}")
public class ${className}Action extends BaseAction{

    private Logger logger = LogManager.getLogger(getClass());

	//navTab rel="${className}"
	private static final String NAV_TAB_REL="${className}";

	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;

	@Autowired
	private ${className}Service ${classNameLower}Service;
	@Autowired
	private LogService logService;

	private ${className} ${classNameLower}=new ${className}();

	private boolean bool=false;
	private String message=DWZ_STATUS_SUCCESS;

    /**
     * 执行搜索
     *
     * @return
     */
    @RequestMapping(value = "/list.html")
    public String list(${className} ${classNameLower}, Page page) {
        attribute();
        page = ${classNameLower}Service.findPage(${classNameLower}, page);
        request.setAttribute("page", page);
        return "${className}/list";
    }

	/**
	 * 进入更新页面
	 * @return
	 */
	@RequestMapping(value = "/info.html", method = RequestMethod.GET)
	public String infoShow(${className} ${classNameLower}) {

        ${classNameLower} = ${classNameLower}Service.getById(${classNameLower});
        request.setAttribute("entity", ${classNameLower});
        return "${className}/info";
	}

	/**
	 * 保存更新
	 */
    @ResponseBody
	@RequestMapping(value = "/info.html", method = RequestMethod.POST)
	public String infoEdit(${className} ${classNameLower}) {
		try {
            if(Tools.isEmpty(${classNameLower}.getId())){
                ${classNameLower}Service.save(upower);
            }else{
                <#list table.columns as column>
                <#if column.pk>
                        this.${classNameLower}=${classNameLower}Service.getById(${classNameLower});
                <#else>
                        this.${classNameLower}.set${column.columnName}(${classNameLower}.get${column.columnName}());
                </#if>
                </#list>
            }
			${classNameLower}Service.update(this.${classNameLower});
			this.bool=true;
		} catch (Exception e) {
            message = String.format("%s 错误:[%s]", DWZ_STATUS_ERROR, e.getMessage());
			logger.error("", e);
		}finally {
            //记录日志
            logService.put(request, "${table.tableAlias} > 修改", JSON.toJSONString(${classNameLower}), bool ? 0 : 1);
            //返回信息
            return retImessage(bool ? DWZ_STATUS_SUCCESS_CODE : DWZ_STATUS_ERROR_CODE, message, bool);
		}
	}


	<#list table.columns as column>
	<#if column.pk>
	/**删除对象*/
    @ResponseBody
	@RequestMapping(value = "/delete.html", method = RequestMethod.POST)
	public String delete(${column.javaType} [] items) {
		try {
			for(int i = 0; i < items.length; i++) {
				${classNameLower}Service.delete(new ${className}(items[i]));
			}
			bool=true;
		} catch (Exception e) {
			message=String.format("%s 错误:[%s]", DWZ_STATUS_ERROR,e.getMessage());
			logger.error("", e);
		}finally {
            //记录日志
            logService.put(request, "${table.tableAlias} > 删除", JSON.toJSONString(items), bool ? 0 : 1);
            //返回信息
            return retImessage(bool ? DWZ_STATUS_SUCCESS_CODE : DWZ_STATUS_ERROR_CODE, message, false);
		}
	}
	</#if>
	</#list>
}