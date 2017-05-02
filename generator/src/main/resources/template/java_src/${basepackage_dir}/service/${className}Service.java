<#include "/java_copyright.include">
<#include "/custom.include"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.common.Page;
import com.yanerwu.entity.${className};
import com.yanerwu.utils.Tools;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

<#include "/java_imports.include"/>
@Service
public class ${className}Service{

    private Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private DbUtilsTemplate yanerwuTemplate;

	public Page findPage(${className} query,Page page) {
		List<Object> params=new ArrayList<>();
        StringBuilder sql2 = new StringBuilder("select * from ${dataBaseName}.${table.sqlName} t where 1=1 ");
        <#list table.columns as column>
        <#if column.isDateTimeColumn>
        if(Tools.isNotEmpty(query.get${column.columnName}Begin())) {
            sql2.append(" and  t.${column} >= ? ");
            params.add(query.get${column.columnName}Begin());
        }
        if(Tools.isNotEmpty(query.get${column.columnName}End())) {
            sql2.append(" and  t.${column} <= ? ");
            params.add(query.get${column.columnName}End());
        }
        <#else>
        if(Tools.isNotEmpty(query.get${column.columnName}())) {
            sql2.append(" and  t.${column} = ? ");
            params.add(query.get${column.columnName}());
        }
        </#if>
        </#list>
        if (StringUtils.isNotBlank(page.getOrderField())) {
            sql2.append("order by ? ?");
            params.add(page.getOrderField());
            params.add(page.getOrderDirection());
        }	
        return yanerwuTemplate.findPage(page, sql2.toString(), params.toArray(), ${className}.class);
	}
	
	/**
	 * 新增
	 */
	public Object save(${className} ${classNameLower}){
        return yanerwuTemplate.insert(${classNameLower});
	}
	
	/**
	 * 修改
	 */
	public int update(${className} ${classNameLower}){
		return yanerwuTemplate.update(${classNameLower});
	}
	
	/**
	 * 删除
	 */
	public int delete(${className} ${classNameLower}){
		return yanerwuTemplate.delete(${classNameLower});
	}
	
	public <T> T getById(${className} ${classNameLower}){
		return (T) yanerwuTemplate.getById(${classNameLower});
	}
	
	
<#list table.columns as column>
	<#if column.unique && !column.pk>
	@Transactional(readOnly=true)
	public ${className} getBy${column.columnName}(${column.javaType} v) {
		return ${classNameLower}Dao.getBy${column.columnName}(v);
	}	
	
	</#if>
</#list>
}
