package com.yanerwu.cas.authentication.cache;

import com.yanerwu.cas.authentication.utils.AdminInfoVo;
import com.yanerwu.common.DbUtilsTemplate;
import io.netty.util.internal.ConcurrentSet;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author Zuz
 * @Date 2017/5/20 11:53
 * @Description
 */
@Component
public class UserCache {

    //    public static final ConcurrentMap<Integer, Upower> POWERS_OF_ID_MAP = new ConcurrentHashMap<>();
//    public static final ConcurrentMap<String, Upower> POWERS_OF_PATH_MAP = new ConcurrentHashMap<>();
    public static final ConcurrentSet<AdminInfoVo> USERS_SET = new ConcurrentSet();
    public static final ConcurrentMap<Integer, AdminInfoVo> USERS_OF_ID_MAP = new ConcurrentHashMap<>();
    public static final ConcurrentMap<String, AdminInfoVo> USERS_OF_LOGIN_NAME_MAP = new ConcurrentHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(UserCache.class);
    @Autowired
    private DbUtilsTemplate userTemplate;


    @PostConstruct
    public void init() {
        cacheUser();
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void cacheUserTask() {
        cacheUser();
    }

    public void cacheUser() {
        //填充 用户 map
        String sql = "select * from WDZJ_ADMIN_INFO";
        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
        List<AdminInfoVo> adminInfoVos = userTemplate.find(AdminInfoVo.class, sql, processor);
        USERS_SET.clear();
        USERS_OF_ID_MAP.clear();
        USERS_OF_LOGIN_NAME_MAP.clear();
        for (AdminInfoVo u : adminInfoVos) {
            USERS_SET.add(u);
            USERS_OF_ID_MAP.put(u.getAdminUserId(), u);
            USERS_OF_LOGIN_NAME_MAP.put(u.getAdminUserName(), u);
        }
    }

//    @PostConstruct
//    public void init() {
//
//        try {
//            cachePower();
//            cacheUser();
//        } catch (Exception e) {
//            logger.error("用户加载出错,退出!", e);
//            System.exit(1);
//        }
//
//    }
//
//    /**
//     * 缓存权限
//     */
//    public void cachePower() {
//        //填充 权限map
//        String sql = "select * from u_power";
//        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
//        List<Upower> upowerList = userTemplate.find(Upower.class, sql, processor);
//        POWERS_OF_ID_MAP.clear();
//        POWERS_OF_PATH_MAP.clear();
//        for (Upower up : upowerList) {
//            POWERS_OF_ID_MAP.put(up.getId(), up);
//            POWERS_OF_PATH_MAP.put(up.getPowerPath(), up);
//        }
//    }
//
//    public void cacheUser() {
//        //填充 用户 map
//        String sql = "select * from u_user where valid='y'";
//        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
//        List<Uuser> uuserList = userTemplate.find(Uuser.class, sql, processor);
//        USERS_SET.clear();
//        USERS_OF_ID_MAP.clear();
//        USERS_OF_LOGIN_NAME_MAP.clear();
//        for (Uuser u : uuserList) {
//            //用户属性填充权限
//            List<Upower> upowerList = getUpowerByUserId(u.getId());
//
//            u.setUpowers(upowerList);
//
//            USERS_SET.add(u);
//            USERS_OF_ID_MAP.put(u.getId(), u);
//            USERS_OF_LOGIN_NAME_MAP.put(u.getLoginName(), u);
//        }
//    }
//
//    private List<Upower> getUpowerByUserId(Integer id) {
//        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
//        String sql = "select p.*,case when up.u_power_id > 0 then 'Y' else 'N' end valid from u_power p left join u_user2power up on p.id = up.u_power_id and up.u_user_id = ?";
//        return userTemplate.find(Upower.class, sql, id, processor);
//    }

}
