package com.yanerwu.service;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.entity.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zuozhi
 * @Desc
 */
@Service
public class LogService {
//    @Autowired
//    LoginService loginService;
    private Logger logger = LogManager.getLogger(getClass());
//    @Autowired
//    private DbUtilsTemplate monitorOfflineTemplate;

    public boolean put(Log l) {
//        String sql = "insert into shuju_operation_log (project, type, info, status) values (?,?,?,?,?)";
//        int result = monitorOfflineTemplate.update(sql, new Object[]{
//                l.getProject(),
//                l.getType(),
//                l.getInfo(),
//                l.getStatus()
//        });
//        if (result < 0) {
//            return false;
//        }
        return true;
    }

    public boolean put(HttpServletRequest request, String type, String info) {
        return this.put(request, type, info, 0);
    }

    public boolean put(HttpServletRequest request, String type, String info, int status) {
        return this.put(request, "monitor", type, info, status);
    }

    public boolean put(HttpServletRequest request, String project, String type, String info, int status) {
//        Uuser user = loginService.getUserInfo(request);
//        String sql = "insert into shuju_operation_log (project, type, info, status) values (?,?,?,?)";
//        int result = monitorOfflineTemplate.update(sql, new Object[]{
//                user.getId(),
//                project,
//                type,
//                info,
//                status
//        });
//        if (result < 0) {
//            return false;
//        }
        return true;
    }
}
