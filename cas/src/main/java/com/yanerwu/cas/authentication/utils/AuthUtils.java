package com.yanerwu.cas.authentication.utils;

import com.yanerwu.cas.authentication.cache.UserCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Zuz
 * @Date 2017/5/25 10:01
 * @Description
 */
@Component
public class AuthUtils {
    private static final String SELECT_APP_ROLE_SQL = "SELECT a.USER_ID,b.*,e.*,GROUP_CONCAT(d.APP_AUTH_ID) AUTH_ID_LIST FROM WDZJ_UR_REF a LEFT JOIN WDZJ_ROLE b ON a.ROLE_ID = b.ROLE_ID LEFT JOIN WDZJ_ROLE_AUTH c ON a.ROLE_ID = c.ROLE_ID LEFT JOIN WDZJ_APP_AUTH d ON d.APP_AUTH_ID = c.APP_AUTH_ID LEFT JOIN WDZJ_APP e ON d.APP_ID = e.APP_ID GROUP BY a.USER_ID,b.ROLE_ID,e.APP_ID";

    @NotNull
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AdminInfoVo getPerson(String uid) {
        AdminInfoVo user = UserCache.USERS_OF_LOGIN_NAME_MAP.get(uid);

        UserAuth userAuth = getAppAuthorities(user.getAdminUserId(), "cms");
        user.setUserAuth(userAuth);

        Map<String, List<Map<String, Object>>> allAuth = getAppAuthorities("cms");
        user.setAllAuth(allAuth);

        return user;
    }

    public Map<String, List<Map<String, Object>>> getAppAuthorities(String appCode) {
        Map<String, List<Map<String, Object>>> appAuthorities = new HashMap<>();
        String sql = "select a.*,b.APP_CODE from WDZJ_APP_AUTH a LEFT JOIN WDZJ_APP b ON a.APP_ID=b.APP_ID WHERE APP_CODE=?";
        List<Map<String, Object>> authorities = this.selectBySql(sql, new Object[]{appCode});
        appAuthorities.put("authorities", authorities);
        return appAuthorities;
    }

    public UserAuth getAppAuthorities(int uid, String appCode) {
        if (uid <= 0 || StringUtils.isBlank(appCode)) {
            return null;
        }

        List<Map<String, Object>> appRoles = this.selectBySql(SELECT_APP_ROLE_SQL + " HAVING a.USER_ID=? AND e.APP_CODE =?", new Object[]{uid, appCode});
        if (null == appRoles || appRoles.size() <= 0) {
            return null;
        }

        Map<String, Object> appAuthorities = new HashMap<>();
        Map<String, Long> authIds = new HashMap<>();
        List<String> roleCodes = new ArrayList<>();
        String sql = "SELECT * FROM WDZJ_APP_AUTH WHERE APP_AUTH_ID in(%s)";
        for (Map<String, Object> appRole : appRoles) {
            roleCodes.add(appRole.get("roleCode").toString());
            authIds.putAll(parseForMap(appRole.get("authIdList").toString(), Long.class));
        }
        List<Map<String, Object>> authorities = this.selectBySql(String.format(sql, parseForString(authIds)), new Object[]{});
        List<Map<String, Object>> authoritiesTree = parseForTree(authorities, "appAuthId", "appAuthPid", "children");

        UserAuth userAuth = new UserAuth();
        userAuth.setRoleCode(StringUtils.join(roleCodes, ","));
        userAuth.setAuthorities(authorities);
        userAuth.setAuthoritiesTree(authoritiesTree);

        return userAuth;
    }

    protected List<Map<String, Object>> selectBySql(String sql, Object[] params) {
        return this.jdbcTemplate.query(sql, params, new RowMapper<Map<String, Object>>() {
            @Override
            public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, Object> bean = new HashMap<String, Object>();
                ResultSetMetaData rd = rs.getMetaData();
                int columns = rd.getColumnCount();
                for (int i = 1; i <= columns; i++) {
                    String columnName = rd.getColumnName(i);
                    bean.put(parseCamelCase(columnName), rs.getString(columnName));
                }
                return bean;
            }
        });
    }

    private <T> Map<String, T> parseForMap(String source, Class<T> requiredType) {
        Map m = new HashMap<>();
        for (String s : source.split(",")) {
            m.put(s, Long.class.equals(requiredType) ? Long.parseLong(s) : s);
        }
        return m;
    }

    /**
     * 取掉下划线,驼峰式命名
     *
     * @param unnamed
     * @return
     */
    private String parseCamelCase(String unnamed) {
        String lowerCaseStr = unnamed.toLowerCase();
        String[] noDashArray = StringUtils.split(lowerCaseStr, '_');
        StringBuilder noDash = new StringBuilder(noDashArray[0]);
        for (int i = 1; i < noDashArray.length; i++) {
            noDash.append(StringUtils.capitalize(noDashArray[i]));
        }
        return noDash.toString();
    }

    private List<Map<String, Object>> parseForTree(List<Map<String, Object>> nodes, String idKey, String pIdKey, String childKey) {
        if (null != nodes && nodes.size() <= 0) {
            return null;
        }

        List<Map<String, Object>> root = new ArrayList<>();
        Map<String, Object> tmpMap = new HashMap<>();
        for (Map<String, Object> node : nodes) {
            tmpMap.put(node.get(idKey).toString(), node);
        }
        for (Map<String, Object> node : nodes) {
            if (tmpMap.containsKey(node.get(pIdKey).toString()) && !node.get(idKey).equals(node.get(pIdKey))) {
                if (!((Map) tmpMap.get(node.get(pIdKey).toString())).containsKey(childKey)) {
                    ((Map) tmpMap.get(node.get(pIdKey).toString())).put(childKey, new ArrayList<>());
                }
                ((List) ((Map) tmpMap.get(node.get(pIdKey).toString())).get(childKey)).add(node);
            } else {
                root.add(node);
            }
        }
        return root;
    }

    private String parseForString(Map source) {
        List<String> ls = new ArrayList<>();
        for (Object key : source.keySet()) {
            ls.add(source.get(key).toString());
        }
        return StringUtils.join(ls, ",");
    }
}
