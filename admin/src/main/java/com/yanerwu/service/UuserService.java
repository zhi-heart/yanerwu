package com.yanerwu.service;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.common.Page;
import com.yanerwu.entity.Uuser;
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
 * @since 1.0
 */
@Service
public class UuserService {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private DbUtilsTemplate yanerwuTemplate;

    public Page findPage(Uuser query, Page page) {

        List<Object> params = new ArrayList();

        StringBuilder sql2 = new StringBuilder("select * from yanerwu.u_user t where 1=1 ");
        if (Tools.isNotEmpty(query.getId())) {
            sql2.append(" and  t.id = ? ");
            params.add(query.getId());
        }
        if (Tools.isNotEmpty(query.getLoginName())) {
            sql2.append(" and  t.login_name = ? ");
            params.add(query.getLoginName());
        }
        if (Tools.isNotEmpty(query.getLoginPwd())) {
            sql2.append(" and  t.login_pwd = ? ");
            params.add(query.getLoginPwd());
        }
        if (Tools.isNotEmpty(query.getName())) {
            sql2.append(" and  t.name = ? ");
            params.add(query.getName());
        }
        if (Tools.isNotEmpty(query.getPhone())) {
            sql2.append(" and  t.phone = ? ");
            params.add(query.getPhone());
        }
        if (Tools.isNotEmpty(query.getCreateDate())) {
            sql2.append(" and  t.create_date = ? ");
            params.add(query.getCreateDate());
        }
        if (Tools.isNotEmpty(query.getValid())) {
            sql2.append(" and  t.valid = ? ");
            params.add(query.getValid());
        }
        if (StringUtils.isNotBlank(page.getOrderField())) {
            sql2.append("order by ? ?");
            params.add(page.getOrderField());
            params.add(page.getOrderDirection());
        }

        return yanerwuTemplate.findPage(page, sql2.toString(), params.toArray(), Uuser.class);
    }

    /**
     * 新增
     */
    public Object save(Uuser uuser) {
        return yanerwuTemplate.insert(uuser);
    }

    /**
     * 修改
     */
    public int update(Uuser uuser) {
        return yanerwuTemplate.update(uuser);
    }

    /**
     * 删除
     */
    public int delete(Uuser uuser) {
        return yanerwuTemplate.delete(uuser);
    }

    public <T> T getById(Uuser uuser) {
        return (T) yanerwuTemplate.getById(uuser);
    }

}
