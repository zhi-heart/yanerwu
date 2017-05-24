package com.yanerwu.cas.authentication.ret;

import com.alibaba.fastjson.JSON;
import com.yanerwu.cas.authentication.cache.UserCache;
import com.yanerwu.cas.authentication.utils.AdminInfoVo;
import com.yanerwu.entity.Uuser;
import org.jasig.services.persondir.IPersonAttributes;
import org.jasig.services.persondir.support.AttributeNamedPersonImpl;
import org.jasig.services.persondir.support.StubPersonAttributeDao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Zuz
 * @Date 2017/5/22 16:20
 * @Description 填充返回字段,json格式
 */
public class RetPersonAttributeDao extends StubPersonAttributeDao {
    @Override
    public IPersonAttributes getPerson(String uid) {

        Map<String, List<Object>> attributes = new HashMap<>();
        AdminInfoVo user = UserCache.USERS_OF_LOGIN_NAME_MAP.get(uid);
        attributes.put("adminInfo", Collections.singletonList(JSON.toJSONString(user)));

        return new AttributeNamedPersonImpl(attributes);
    }
}
