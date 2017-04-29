<#include "/java_copyright.include">
<#include "/custom.include"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

import static cn.org.rapid_framework.util.ObjectUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wangdaizhijia.shuju.entity.Page;
import com.wangdaizhijia.shuju.common.DbUtilsTemplate;

<#include "/java_imports.include">
@Service
public class ${className}Service{

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private DbUtilsTemplate shujuOfflineTemplate;

	public Page findPage(${className}Query query,Page page) {
		
		List<Object> params=new ArrayList<>();
		
        StringBuilder sql2 = new StringBuilder("select * from ${dataBaseName}.${table.sqlName} t where 1=1 ");
        <#list table.columns as column>
        <#if column.isDateTimeColumn>
        if(isNotEmpty(query.get${column.columnName}Begin())) {
            sql2.append(" and  t.${column} >= ? ");
            params.add(query.get${column.columnName}Begin());
        }
        if(isNotEmpty(query.get${column.columnName}End())) {
            sql2.append(" and  t.${column} <= ? ");
            params.add(query.get${column.columnName}End());
        }
        <#else>
        if(isNotEmpty(query.get${column.columnName}())) {
            sql2.append(" and  t.${column} = ? ");
            params.add(query.get${column.columnName}());
        }
        </#if>
        </#list>
        if(isNotEmpty(query.getSortColumns())) {
            sql2.append(" order by :sortColumns ");
        }	
        return shujuOfflineTemplate.findPage(page, sql2.toString(), params.toArray(), ${className}.class);
	}
	
	/**
	 * 新增
	 */
	public int save(${className} ${classNameLower}){
		return shujuOfflineTemplate.save(${classNameLower});
	}
	
	/**
	 * 修改
	 */
	public int update(${className} ${classNameLower}){
		return shujuOfflineTemplate.update(${classNameLower});
	}
	
	/**
	 * 删除
	 */
	public int delete(${className} ${classNameLower}){
		return shujuOfflineTemplate.delete(${classNameLower});
	}
	
	public <T> T getById(${className} ${classNameLower}){
		return (T) shujuOfflineTemplate.getById(${classNameLower});
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
