<#include "/custom.include">
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign actionExtension = "html">
package ${basepackage}.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.wangdaizhijia.shuju.entity.Page;
import com.alibaba.fastjson.JSON;
import com.wangdaizhijia.shuju.service.LogService;

<#include "/java_imports.include">

@Component
public class ${className}Action extends BaseAction{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	//navTab rel=""
	private static final String NAV_TAB_REL="${classNameLower}";
	
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	
	@Autowired
	private ${className}Service ${classNameLower}Service;
	@Autowired
	private LogService logService;
	
	private ${className} ${classNameLower}=new ${className}();

	private boolean bool=false;
	private String message=DWZ_RET_SUCCESS;
	
	/**
	 * 执行搜索
	 * @return
	 */
	@RequestMapping(value = "/${table.sqlName}/list.html")
	public String list(Page page) {
		${className}Query query = newQuery(${className}Query.class,DEFAULT_SORT_COLUMNS);
		page = ${classNameLower}Service.findPage(query,page);
		savePage(page,query);
		return "${className}/list";
	}
	
	/**
	 * 保存新增对象
	 * @param ${classNameLower}
	 */
	@RequestMapping(value = "/${table.sqlName}/add.html", method = RequestMethod.POST)
	public void save(${className} ${classNameLower}) {
		try {
			int updResult=${classNameLower}Service.save(${classNameLower});
			if(updResult>0){
				bool=true;
			}
		} catch (Exception e) {
			message=String.format("%s 错误:[%s]", DWZ_RET_FAIL,e.getMessage());
			logger.error("", e);
		}finally {
			//记录日志
			logService.put(request, "${table.tableAlias} > 新增", JSON.toJSONString(${classNameLower}), bool?0:1);
			//返回信息
			super.retImessage(bool?super.STATUS_SUCCESS:super.STATUS_ERROR, message,bool);
		}
	}
	
	<#list table.columns as column>
	<#if column.pk>
	/**
	 * 进入更新页面
	 * @return
	 */
	@RequestMapping(value = "/${table.sqlName}/info.html", method = RequestMethod.GET)
	public String infoShow() {
		${className}Query ${classNameLower}Query = newQuery(${className}Query.class,DEFAULT_SORT_COLUMNS);
		
		<#list table.columns as column>
		<#if column.pk>
		${className} ${classNameLower}=${classNameLower}Service.getById(new ${className}(${classNameLower}Query.get${column.columnName}()));
        </#if>
		</#list>
		
		request.setAttribute("${classNameLower}", ${classNameLower});
		return "${className}/info";
	}
	</#if>
	</#list>
	
	/**
	 * 保存更新
	 */
	@RequestMapping(value = "/${table.sqlName}/info.html", method = RequestMethod.POST)
	public void infoEdit(${className} ${classNameLower}) {
		try {
			<#list table.columns as column>
			<#if column.pk>
			this.${classNameLower}=${classNameLower}Service.getById(${classNameLower});
			<#else>
			this.${classNameLower}.set${column.columnName}(${classNameLower}.get${column.columnName}());
			</#if>
			</#list>
			${classNameLower}Service.update(this.${classNameLower});
			this.bool=true;
		} catch (Exception e) {
			message=String.format("%s 错误:[%s]", DWZ_RET_FAIL,e.getMessage());
			logger.error("", e);
		}finally {
			//记录日志
			logService.put(request, "${table.tableAlias} > 新增", JSON.toJSONString(getAllParams()), bool?0:1);
			//返回信息
			super.retImessage(bool?super.STATUS_SUCCESS:super.STATUS_ERROR, message,bool);
		}
	}
	
	
	<#list table.columns as column>
	<#if column.pk>
	/**删除对象*/
	@RequestMapping(value = "/${table.sqlName}/delete.html", method = RequestMethod.POST)
	public void delete(${column.javaType} [] items) {
		try {
			for(int i = 0; i < items.length; i++) {
				${classNameLower}Service.delete(new ${className}(items[i]));
			}
			bool=true;
		} catch (Exception e) {
			message=String.format("%s 错误:[%s]", DWZ_RET_FAIL,e.getMessage());
			logger.error("", e);
		}finally {
			//记录日志
			logService.put(request, "${table.tableAlias} > 删除", JSON.toJSONString(items), bool?0:1);
			//返回信息
			super.retImessage(bool?super.STATUS_SUCCESS:super.STATUS_ERROR, message);
		}
	}
	</#if>
	</#list>
}