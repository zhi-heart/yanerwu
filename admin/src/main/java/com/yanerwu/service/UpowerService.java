package com.yanerwu.service;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.common.Page;
import com.yanerwu.entity.Upower;
import com.yanerwu.utils.Tools;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zuz
 * @version 1.0
 * @Description Upower
 */@Service
public class UpowerService{

    private Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private DbUtilsTemplate yanerwuTemplate;

	public Page findPage(Upower query,Page page) {
		List<Object> params=new ArrayList<>();
        StringBuilder sql2 = new StringBuilder("select * from movie.u_power t where 1=1 ");
        if(Tools.isNotEmpty(query.getId())) {
            sql2.append(" and  t.id = ? ");
            params.add(query.getId());
        }
        if(Tools.isNotEmpty(query.getName())) {
            sql2.append(" and  t.name = ? ");
            params.add(query.getName());
        }
        if(Tools.isNotEmpty(query.getPowerPath())) {
            sql2.append(" and  t.power_path = ? ");
            params.add(query.getPowerPath());
        }
        if(Tools.isNotEmpty(query.getParentUpowerId())) {
            sql2.append(" and  t.parent_u_power_id = ? ");
            params.add(query.getParentUpowerId());
        }
        if(Tools.isNotEmpty(query.getPowerType())) {
            sql2.append(" and  t.power_type = ? ");
            params.add(query.getPowerType());
        }
        if(Tools.isNotEmpty(query.getRemark())) {
            sql2.append(" and  t.remark = ? ");
            params.add(query.getRemark());
        }
        if (StringUtils.isNotBlank(page.getOrderField())) {
            sql2.append("order by ? ?");
            params.add(page.getOrderField());
            params.add(page.getOrderDirection());
        }	
        return yanerwuTemplate.findPage(page, sql2.toString(), params.toArray(), Upower.class);
	}
	
	/**
	 * 新增
	 */
	public Object save(Upower upower){
        return yanerwuTemplate.insert(upower);
	}
	
	/**
	 * 修改
	 */
	public int update(Upower upower){
		return yanerwuTemplate.update(upower);
	}
	
	/**
	 * 删除
	 */
	public int delete(Upower upower){
		return yanerwuTemplate.delete(upower);
	}
	
	public <T> T getById(Upower upower){
		return (T) yanerwuTemplate.getById(upower);
	}
	
	
}
