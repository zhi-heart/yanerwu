<#include "/custom.include">
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign actionExtension = "shtml">
package com.travelsky.tdp.pkgStock.view.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.org.rapid_framework.beanutils.BeanUtils;
import cn.org.rapid_framework.web.scope.Flash;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ModelDriven;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.travelsky.tdp.pkgStock.view.base.BaseAction;

<#include "/java_imports.include">
@Results( { 
	@Result(name = "QUERY_JSP", type = "dispatcher", location = "${jspFileBasePath}/query.jsp", params = {}),
	@Result(name = "LIST_JSP", type = "dispatcher", location = "${jspFileBasePath}/list.jsp", params = {}),
	@Result(name = "CREATE_JSP", type = "dispatcher", location = "${jspFileBasePath}/create.jsp", params = {}),
	@Result(name = "EDIT_JSP", type = "dispatcher", location = "${jspFileBasePath}/edit.jsp", params = {}),
	@Result(name = "SHOW_JSP", type = "dispatcher", location = "${jspFileBasePath}/show.jsp", params = {}),
	@Result(name = "LIST_ACTION", type = "redirect", location = "/action/${className}!list.${actionExtension}", params = {})
})
public class ${className}Action extends BaseAction implements Preparable,ModelDriven{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	
	//forward paths
	protected static final String QUERY_JSP = "QUERY_JSP";
	protected static final String LIST_JSP= "LIST_JSP";
	protected static final String CREATE_JSP = "CREATE_JSP";
	protected static final String EDIT_JSP = "EDIT_JSP";
	protected static final String SHOW_JSP = "SHOW_JSP";
	//redirect paths,startWith: !
	protected static final String LIST_ACTION = "LIST_ACTION";
	
	private ${className}Manager ${classNameLower}Manager;
	
	private ${className} ${classNameLower};
	<#list table.compositeIdColumns as column>
	${column.javaType} id = null;
	</#list>
	private String[] items;

	public void prepare() throws Exception {
		if (isNullOrEmptyString(id)) {
			${classNameLower} = new ${className}();
		} else {
			${classNameLower} = (${className})${classNameLower}Manager.getById(id);
		}
	}
	
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void set${className}Manager(${className}Manager manager) {
		this.${classNameLower}Manager = manager;
	}	
	
	public Object getModel() {
		return ${classNameLower};
	}
	
	<#list table.compositeIdColumns as column>
	public void set${column.columnName}(${column.javaType} val) {
		this.id = val;
	}
	</#list>	

	public void setItems(String[] items) {
		this.items = items;
	}
	
	/** 执行搜索 */
	public String list() {
		${className}Query query = newQuery(${className}Query.class,DEFAULT_SORT_COLUMNS);
		
		Page page = ${classNameLower}Manager.findPage(query);
		savePage(page,query);
		return LIST_JSP;
	}
	
	/** 查看对象*/
	public String show() {
		return SHOW_JSP;
	}
	
	/** 进入新增页面*/
	public String create() {
		return CREATE_JSP;
	}
	
	/** 保存新增对象 */
	public String save() {
		${classNameLower}Manager.save(${classNameLower});
		Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}
	
	/**进入更新页面*/
	public String edit() {
		return EDIT_JSP;
	}
	
	/**保存更新对象*/
	public String update() {
		${classNameLower}Manager.update(this.${classNameLower});
		Flash.current().success(UPDATE_SUCCESS);
		return LIST_ACTION;
	}
	
	/**删除对象*/
	public String delete() {
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			<#if table.compositeId>
			${className}Id id = (${className}Id)copyProperties(${className}Id.class,params);
			<#else>
				<#list table.compositeIdColumns as column>
			${column.javaType} id = new ${column.javaType}((String)params.get("${column.columnNameLower}"));
				</#list>
			</#if>
			${classNameLower}Manager.removeById(id);
		}
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}

}
