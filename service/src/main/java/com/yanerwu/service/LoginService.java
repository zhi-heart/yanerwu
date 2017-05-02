//package com.yanerwu.service;
//
//import com.yanerwu.common.DbUtilsTemplate;
//import com.yanerwu.common.Page;
//import com.yanerwu.entity.Upower;
//import com.yanerwu.entity.Uuser;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
///**
//* @author zuozhi
//* @version 创建时间：2016年7月29日 上午11:49:38
//* @Desc
//*/
//public interface LoginService {
//
//	/**
//	 * 缓存全部权限
//	 */
//	void init();
//
//	/**
//	 * 获取当前登录用户信息
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	Uuser getUserInfo(HttpServletRequest request, HttpServletResponse response);
//
//	Uuser getUserInfo(HttpServletRequest request);
//
//	String getMd5Pwd(String loginName, String loginPwd);
//
//	/**
//	 * 静态方法，便于作为工具类
//	 * @param plainText
//	 * @return
//	 */
//	String getMd5(String plainText);
//
//	/**
//	 * 添加用户
//	 * @param request
//	 */
//	void addUser(HttpServletRequest request);
//
//	/**
//	 * 删除用户
//	 * @param request
//	 */
//	void deleteUser(HttpServletRequest request);
//
//	/**
//	 * 获取用户列表
//	 * @return
//	 */
//	List<Uuser> getUuserList();
//
//	/**
//	 * 根据id查找用户
//	 * @param userId
//	 * @return
//	 */
//	Uuser getUuserById(String userId);
//
//	/**
//	 * 根据用户id获取权限
//	 * @param userId
//	 * @return
//	 */
//	List<Upower> getUPwerByUserId(String userId);
//
//	/**
//	 * 根据用户id获取权限
//	 * @param powers
//	 * @return
//	 */
//	Map<String, Upower> getUPwerPathByUserId(List<Upower> powers);
//
//	/**
//	 *  用户权限配置
//	 * @param userId
//	 * @param powers
//	 */
//	void saveUserPower(String userId, String[] powers);
//
//	Page getPowerList(Page page);
//
//	/**
//	 * 添加权限
//	 * @param p
//	 */
//	void savePower(Upower p);
//
//	/**
//	 * 添加用户
//	 * @param u
//	 */
//	void saveUser(Uuser u);
//
//	void editUser(Uuser u, boolean updatePwd);
//
//	int userDelete(String id);
//
//	int powerDelete(String id);
//
//	/**
//	 *
//	 * @param db
//	 * @param page
//	 * @param sql
//	 * @return
//	 */
//	Page<?> getPageData(DbUtilsTemplate db, Page<?> page, String sql);
//
//}