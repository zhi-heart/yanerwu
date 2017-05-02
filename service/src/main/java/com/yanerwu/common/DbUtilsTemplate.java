package com.yanerwu.common;

import com.yanerwu.annotation.Column;
import com.yanerwu.annotation.Id;
import com.yanerwu.annotation.Table;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zuz on 2017/4/27.
 */
public class DbUtilsTemplate {

    private static final Logger logger = LogManager.getLogger(DbUtilsTemplate.class);
    /**
     * 操作数据库类别-插入
     */
    private static final String ANNOTATION_TABLE_SELECT = "SELECT";
    /**
     * 操作数据库类别-插入
     */
    private static final String ANNOTATION_TABLE_INSERT = "INSERT";
    /**
     * 操作数据库类别-修改
     */
    private static final String ANNOTATION_TABLE_UPDATE = "UPDATE";
    private static final String FIELD_KEY_MAP = "FIELD_KEY_MAP";
    private static final String FIELD_ID_MAP = "FIELD_ID_MAP";
    private BasicDataSource dataSource;
    private QueryRunner queryRunner;

    /**
     * 执行sql语句
     *
     * @param sql sql语句
     * @return 受影响的行数
     */
    public int update(String sql) {
        return update(sql, null);
    }

    /**
     * 执行sql语句 <code>
     * executeUpdate("update user set username = 'kitty' where username = ?", "hello kitty");
     * </code>
     *
     * @param sql   sql语句
     * @param param 参数
     * @return 受影响的行数
     */
    public int update(String sql, Object param) {
        return update(sql, new Object[]{param});
    }

    /**
     * 执行sql语句
     *
     * @param sql    sql语句
     * @param params 参数数组
     * @return 受影响的行数
     */
    public int update(String sql, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        int affectedRows = 0;
        try {
            if (params == null) {
                affectedRows = queryRunner.update(sql);
            } else {
                affectedRows = queryRunner.update(sql, params);
            }
        } catch (SQLException e) {
            logger.error("Error occured while attempting to update data", e);
        }
        return affectedRows;
    }

    /**
     * 执行批量sql语句
     *
     * @param sql    sql语句
     * @param params 二维参数数组
     * @return 受影响的行数的数组
     */
    public int[] batchUpdate(String sql, Object[][] params) {
        queryRunner = new QueryRunner(dataSource);
        int[] affectedRows = new int[0];
        try {
            affectedRows = queryRunner.batch(sql, params);
        } catch (SQLException e) {
            logger.error("Error occured while attempting to batch update data", e);
        }
        return affectedRows;
    }

    /**
     * 执行查询，将每行的结果保存到一个Map对象中，然后将所有Map对象保存到List中
     *
     * @param sql sql语句
     * @return 查询结果
     */
    public List<Map<String, Object>> find(String sql) {
        return find(sql, null, null);
    }

    /**
     * 执行查询，将每行的结果保存到一个Map对象中，然后将所有Map对象保存到List中
     *
     * @param sql   sql语句
     * @param param 参数
     * @return 查询结果
     */
    public List<Map<String, Object>> find(String sql, Object param) {
        return find(sql, new Object[]{param}, null);
    }

    /**
     * 执行查询，将每行的结果保存到一个Map对象中，然后将所有Map对象保存到List中
     *
     * @param sql    sql语句
     * @param params 参数数组
     * @return 查询结果
     */
    public List<Map<String, Object>> find(String sql, Object[] params, RowProcessor processor) {
        queryRunner = new QueryRunner(dataSource);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            if (params == null) {
                if (processor == null) {
                    list = (List<Map<String, Object>>) queryRunner.query(sql, new MapListHandler());
                } else {
                    list = (List<Map<String, Object>>) queryRunner.query(sql, new MapListHandler(), processor);
                }

            } else {
                if (processor == null) {
                    list = (List<Map<String, Object>>) queryRunner.query(sql, new MapListHandler(), params);
                } else {
                    list = (List<Map<String, Object>>) queryRunner.query(sql, new MapListHandler(), params, processor);
                }

            }
        } catch (SQLException e) {
            logger.error("Error occured while attempting to query data", e);
        }
        return list;
    }

    /**
     * 执行查询，将每行的结果保存到Bean中，然后将所有Bean保存到List中
     *
     * @param entityClass 类名
     * @param sql         sql语句
     * @return 查询结果
     */
    public <T> List<T> find(Class<T> entityClass, String sql, RowProcessor processor) {
        return find(entityClass, sql, null, processor);
    }

    /**
     * 执行查询，将每行的结果保存到Bean中，然后将所有Bean保存到List中
     *
     * @param entityClass 类名
     * @param sql         sql语句
     * @param param       参数
     * @return 查询结果
     */
    public <T> List<T> find(Class<T> entityClass, String sql, Object param, RowProcessor processor) {
        return find(entityClass, sql, new Object[]{param}, processor);
    }

    /**
     * 执行查询，将每行的结果保存到Bean中，然后将所有Bean保存到List中
     *
     * @param entityClass 类名
     * @param sql         sql语句
     * @param params      参数数组
     * @return 查询结果
     */
    public <T> List<T> find(Class<T> entityClass, String sql, Object[] params, RowProcessor processor) {
        queryRunner = new QueryRunner(dataSource);
        List<T> list = new ArrayList<T>();
        try {
            if (params == null) {
                if (processor == null) {
                    list = (List<T>) queryRunner.query(sql, new BeanListHandler<T>(entityClass));
                } else {
                    list = (List<T>) queryRunner.query(sql, new BeanListHandler<T>(entityClass, processor));
                }

            } else {
                if (processor == null) {
                    list = (List<T>) queryRunner.query(sql, new BeanListHandler<T>(entityClass), params);
                } else {
                    list = (List<T>) queryRunner.query(sql, new BeanListHandler<T>(entityClass, processor), params);
                }
            }
        } catch (SQLException e) {
            logger.error("Error occured while attempting to query data", e);
        }
        return list;
    }

    /**
     * 查询出结果集中的第一条记录，并封装成对象
     *
     * @param entityClass 类名
     * @param sql         sql语句
     * @return 对象
     */
    public <T> T findFirst(Class<T> entityClass, String sql) {
        return findFirst(entityClass, sql, null);
    }

    /**
     * 查询出结果集中的第一条记录，并封装成对象
     *
     * @param entityClass 类名
     * @param sql         sql语句
     * @param param       参数
     * @return 对象
     */
    public <T> T findFirst(Class<T> entityClass, String sql, Object param) {
        return findFirst(entityClass, sql, new Object[]{param});
    }

    /**
     * 查询出结果集中的第一条记录，并封装成对象
     *
     * @param entityClass 类名
     * @param sql         sql语句
     * @param params      参数数组
     * @return 对象
     */
    public <T> T findFirst(Class<T> entityClass, String sql, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        Object object = null;
        try {
            if (params == null) {
                object = queryRunner.query(sql, new BeanHandler<T>(entityClass));
            } else {
                object = queryRunner.query(sql, new BeanHandler<T>(entityClass), params);
            }
        } catch (SQLException e) {
            logger.error("Error occured while attempting to query data", e);
        }
        return (T) object;
    }

    /**
     * 查询出结果集中的第一条记录，并封装成Map对象
     *
     * @param sql sql语句
     * @return 封装为Map的对象
     */
    public Map<String, Object> findFirst(String sql) {
        return findFirst(sql, null, null);
    }

    /**
     * 查询出结果集中的第一条记录，并封装成Map对象
     *
     * @param sql   sql语句
     * @param param 参数
     * @return 封装为Map的对象
     */
    public Map<String, Object> findFirst(String sql, Object param, RowProcessor processor) {
        return findFirst(sql, new Object[]{param}, processor);
    }

    /**
     * 查询出结果集中的第一条记录，并封装成Map对象
     *
     * @param sql    sql语句
     * @param params 参数数组
     * @return 封装为Map的对象
     */
    public Map<String, Object> findFirst(String sql, Object[] params, RowProcessor processor) {
        queryRunner = new QueryRunner(dataSource);
        Map<String, Object> map = null;
        try {
            if (params == null) {
                if (processor == null) {
                    map = (Map<String, Object>) queryRunner.query(sql, new MapHandler());
                } else {
                    map = (Map<String, Object>) queryRunner.query(sql, new MapHandler(), processor);
                }
            } else {
                if (processor == null) {
                    map = (Map<String, Object>) queryRunner.query(sql, new MapHandler(), params);
                } else {
                    map = (Map<String, Object>) queryRunner.query(sql, new MapHandler(), params, processor);
                }
            }
        } catch (SQLException e) {
            logger.error("Error occured while attempting to query data", e);
        }
        return map;
    }

    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     *
     * @param sql        sql语句
     * @param columnName 列名
     * @return 结果对象
     */
    public Object findBy(String sql, String columnName) {
        return findBy(sql, columnName, null);
    }

    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     *
     * @param sql        sql语句
     * @param columnName 列名
     * @param param      参数
     * @return 结果对象
     */
    public Object findBy(String sql, String columnName, Object param) {
        return findBy(sql, columnName, new Object[]{param});
    }

    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     *
     * @param sql        sql语句
     * @param columnName 列名
     * @param params     参数数组
     * @return 结果对象
     */
    public Object findBy(String sql, String columnName, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        Object object = null;
        try {
            if (params == null) {
                object = queryRunner.query(sql, new ScalarHandler<Object>(columnName));
            } else {
                object = queryRunner.query(sql, new ScalarHandler<Object>(columnName), params);
            }
        } catch (SQLException e) {
            logger.error("Error occured while attempting to query data", e);
        }
        return object;
    }

    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     *
     * @param sql         sql语句
     * @param columnIndex 列索引
     * @return 结果对象
     */
    public Object findBy(String sql, int columnIndex) {
        return findBy(sql, columnIndex, null);
    }

    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     *
     * @param sql         sql语句
     * @param columnIndex 列索引
     * @param param       参数
     * @return 结果对象
     */
    public Object findBy(String sql, int columnIndex, Object param) {
        return findBy(sql, columnIndex, new Object[]{param});
    }

    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     *
     * @param sql         sql语句
     * @param columnIndex 列索引
     * @param params      参数数组
     * @return 结果对象
     */
    public Object findBy(String sql, int columnIndex, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        Object object = null;
        try {
            if (params == null) {
                object = queryRunner.query(sql, new ScalarHandler<Object>(columnIndex));
            } else {
                object = queryRunner.query(sql, new ScalarHandler<Object>(columnIndex), params);
            }
        } catch (SQLException e) {
            logger.error("Error occured while attempting to query data", e);
        }
        return object;
    }

    public <T> List<T> query(String sql, ColumnListHandler<T> columnListHandler) {
        queryRunner = new QueryRunner(dataSource);
        List<T> object = null;
        try {
            object = queryRunner.query(sql, columnListHandler);
        } catch (SQLException e) {
            logger.error("Error occured while attempting to query data", e);
        }
        return object;
    }

    @SuppressWarnings("deprecation")
    public <T> List<T> query(String sql, Object[] objs, ColumnListHandler<T> columnListHandler) {
        queryRunner = new QueryRunner(dataSource);
        List<T> object = null;
        try {
            object = queryRunner.query(sql, objs, columnListHandler);
        } catch (SQLException e) {
            logger.error("Error occured while attempting to query data", e);
        }
        return object;
    }

    public Map<String, Object> query(String sql, MapHandler mapHandler) {
        queryRunner = new QueryRunner(dataSource);
        Map<String, Object> object = null;
        try {
            object = queryRunner.query(sql, mapHandler);
        } catch (SQLException e) {
            logger.error("Error occured while attempting to query data", e);
        }
        return object;
    }

    public Map<String, Object> query(String sql, Object[] objects, MapHandler mapHandler) {
        queryRunner = new QueryRunner(dataSource);
        Map<String, Object> object = null;
        try {
            object = queryRunner.query(sql, mapHandler, objects);
        } catch (SQLException e) {
            logger.error("Error occured while attempting to query data", e);
        }
        return object;
    }

    public BasicDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取表名
     *
     * @param info
     * @return
     */
    private <T> String getTableName(T info) {
        Class<? extends Object> cls = info.getClass();
        String tableName = null;
        Table table = cls.getAnnotation(Table.class);
        if (null != table) {
            tableName = table.name();
        } else {
            throw new RuntimeException("未找到注解类@table");
        }
        return tableName;
    }

    /**
     * 根据对象删除
     *
     * @param info
     */
    public <T> int delete(T info) {
        String tableName = this.getTableName(info);
        Map<String, Object> delMap = new LinkedHashMap();
        Field[] fields = info.getClass().getDeclaredFields();
        // 提取主键
        for (Field f : fields) {
            if (f.getAnnotations().length > 0) {
                Column column = f.getAnnotation(Column.class);
                if (null != column) {
                    try {
                        f.setAccessible(true);// 修改访问权限
                        Id id = f.getAnnotation(Id.class);
                        if (null != id) {
                            delMap.put(column.name(), f.get(info));
                        }
                    } catch (IllegalArgumentException e) {
                        logger.error("", e);
                    } catch (IllegalAccessException e) {
                        logger.error("", e);
                    }
                }
            }
        }
        // 拼装sql
        List<Object> params = new ArrayList();
        StringBuffer delStr = new StringBuffer();
        for (String k : delMap.keySet()) {
            delStr.append(String.format(" and %s=?", k));
            params.add(delMap.get(k));
        }
        String sql = String.format("delete from %s where 1=1 %s", tableName, delStr);
        return this.update(sql, params.toArray());
    }

    /**
     * 根据对象修改
     *
     * @param info
     */
    public <T> int update(T info) {
        String tableName = this.getTableName(info);

        // 提取属性
        Map<String, Map<String, Object>> fieldMap = this.getField(info, ANNOTATION_TABLE_UPDATE);
        Map<String, Object> idMap = fieldMap.get(FIELD_ID_MAP);
        Map<String, Object> updMap = fieldMap.get(FIELD_KEY_MAP);

        // 开始拼接sql
        List<Object> params = new ArrayList();
        StringBuffer updStr = new StringBuffer();
        StringBuffer keyStr = new StringBuffer();
        // 处理set
        for (String k : updMap.keySet()) {
            updStr.append(String.format(" %s=?,", k));
            params.add(updMap.get(k));
        }
        updStr.delete(updStr.length() - 1, updStr.length());
        // 处理主键where
        for (String k : idMap.keySet()) {
            keyStr.append(String.format(" and %s=?", k));
            params.add(idMap.get(k));
        }
        // 拼接sql
        String sql = String.format("update %s set %s where 1=1 %s", tableName, updStr, keyStr);
        return this.update(sql, params.toArray());
    }

    /**
     * 批量保存对象
     *
     * @param ts
     */
    public <T> List<Object> insert(List<T> ts) {
        if (null == ts || ts.size() < 1) {
            return null;
        }

        Object[][] objs = new Object[ts.size()][];
        String sql = null;

        for (int i = 0; i < ts.size(); i++) {
            T t = ts.get(i);

            String tableName = this.getTableName(t);

            // 提取属性
            Map<String, Object> insMap = this.getField(t, ANNOTATION_TABLE_INSERT).get(FIELD_KEY_MAP);

            // 开始拼接sql
            List<Object> params = new ArrayList();
            StringBuffer key = new StringBuffer();
            StringBuffer value = new StringBuffer();
            for (String k : insMap.keySet()) {
                // 拼接要插入的值 加`可以防止mysql关键字异常
                key.append(String.format("`%s`,", k));
                // 拼接value
                value.append("?,");
                params.add(insMap.get(k));
            }
            key.delete(key.length() - 1, key.length());
            value.delete(value.length() - 1, value.length());
            sql = String.format("insert into %s(%s) values(%s)", tableName, key, value);
            objs[i] = params.toArray();
        }
        return this.insert(sql, objs);

    }

    /**
     * 保存对象
     *
     * @param info
     */
    public <T> Object insert(T info) {
        List<T> infos = new ArrayList();
        infos.add(info);
        return this.insert(infos).get(0);
    }

    @SuppressWarnings("unchecked")
    public <T> T getById(T t) {
        Map<String, Object> idMap = getField(t, ANNOTATION_TABLE_SELECT).get(FIELD_ID_MAP);
        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());

        // 开始拼接sql
        List<Object> params = new ArrayList();
        StringBuffer keyStr = new StringBuffer();
        // 处理主键where
        for (String k : idMap.keySet()) {
            keyStr.append(String.format(" and %s=?", k));
            params.add(idMap.get(k));
        }
        // 拼接sql
        String sql = String.format("select * from %s where 1=1 %s", getTableName(t), keyStr);
        List<? extends Object> list = this.find(t.getClass(), sql, params.toArray(), processor);
        if (list.size() > 0) {
            return (T) list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 获取属性
     *
     * @param t
     * @param type
     * @return
     */
    private <T> Map<String, Map<String, Object>> getField(T t, String type) {
        Map<String, Map<String, Object>> fieldMap = new LinkedHashMap();
        Map<String, Object> keyMap = new LinkedHashMap();
        Map<String, Object> idMap = new LinkedHashMap();

        Class<? extends Object> cls = t.getClass();

        // 提取属性
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            if (f.getAnnotations().length > 0) {
                Column column = f.getAnnotation(Column.class);
                if (null != column) {
                    try {
                        f.setAccessible(true);// 修改访问权限
                        Id id = f.getAnnotation(Id.class);
                        if (null != id) {
                            //主键
                            idMap.put(column.name(), f.get(t));
                        } else {
                            //非主键
                            switch (type) {
                                case ANNOTATION_TABLE_SELECT:
                                    //判断是字段是否需要插入
                                    if (column.insertable()) {
                                        //获取属性值
                                        keyMap.put(column.name(), f.get(t));
                                    }
                                    break;
                                case ANNOTATION_TABLE_INSERT:
                                    //时间传入空字符串异常  暂时将  字段名带 time,date的 如果字符串为'' 则改为null,暂时没想到好方法1
                                    if (StringUtils.isBlank(String.valueOf(f.get(t)))) {
                                        continue;
                                    }
                                    //判断是字段是否需要插入
                                    if (column.insertable()) {
                                        //获取属性值
                                        keyMap.put(column.name(), f.get(t));
                                    }
                                    break;
                                case ANNOTATION_TABLE_UPDATE:
                                    //时间传入空字符串异常  暂时将  字段名带 time,date的 如果字符串为'' 则改为null,暂时没想到好方法1
                                    if (StringUtils.isBlank(String.valueOf(f.get(t)))) {
                                        continue;
                                    }
                                    //判断字段是否需要修改
                                    if (column.updatable()) {
                                        keyMap.put(column.name(), f.get(t));
                                    }
                                    break;
                                default:
                                    keyMap.put(column.name(), f.get(t));
                                    break;
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        logger.error("", e);
                    } catch (IllegalAccessException e) {
                        logger.error("", e);
                    }
                }
            }
        }
        fieldMap.put(FIELD_KEY_MAP, keyMap);
        fieldMap.put(FIELD_ID_MAP, idMap);
        return fieldMap;
    }

    /**
     * 插入数据获取主键
     *
     * @param sql
     * @param params
     * @return
     */
    public Object insert(String sql, Object[] params) {
        Object batchResult = null;
        queryRunner = new QueryRunner(dataSource);
        try {
            Object[][] objs = new Object[1][];
            objs[0] = params;
            Object[] insertBatch = queryRunner.insertBatch(sql, new ScalarHandler<Object[]>(), objs);
            batchResult = insertBatch[0];
        } catch (SQLException e) {
            logger.error("", e);
        }
        return batchResult;
    }

    /**
     * 插入数据获取主键
     *
     * @param sql
     * @param params
     * @return
     */
    public List<Object> insert(String sql, Object[][] params) {
        List<Object> batchResult = null;
        queryRunner = new QueryRunner(dataSource);
        try {
            batchResult = queryRunner.insertBatch(sql, new BeanListHandler<Object>(Object.class), params);
        } catch (SQLException e) {
            logger.error("", e);
        }
        return batchResult;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> Page<T> findPage(Page<T> page, String sql, Object[] params, Class cls) {

        String totalSql = String.format("select count(1) count from (%s) t", sql);
        try {
            Long total = 0L;
            Map<String, Object> countMap = this.findFirst(totalSql, params, null);
            if (countMap.containsKey("count")) {
                total = (Long) countMap.get("count");
            }

            // 设置总条数
            page.setTotalCount(total);
            if (total > 0) {
                // 一共有多少页
                page.setPageNumShown(total / page.getNumPerPage() + 1);
                // 设置当前页数
                page.setCurrentPage(page.getPageNum());
                String limit = String.format("limit %s,%s", page.getNumPerPage() * (page.getPageNum() - 1), page.getNumPerPage());
                sql = String.format("%s %s", sql, limit);

                BeanProcessor bean = new GenerousBeanProcessor();
                RowProcessor processor = new BasicRowProcessor(bean);
                List result = find(cls, sql, params, processor);
                page.setResult(result);
            } else {
                //如果总条数为0,给一个空的list
//				page.setResult(new ArrayList<>());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return page;

    }

}