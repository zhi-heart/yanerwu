package com.yanerwu.wap.service;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.entity.Blog;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Zuz
 * @Date 2017/12/7 14:31
 * @Description
 */
@Service
public class BlogService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DbUtilsTemplate bookTemplate;

    public Blog getBlogByUuid(String uuid) {
        Blog blog = new Blog();
        BeanProcessor bean = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(bean);
        String sql = "select * from blog where uuid=?";
        List<Blog> blogs = bookTemplate.find(Blog.class, sql, uuid, processor);
        if (blogs.size() > 0) {
            blog = blogs.get(0);
        }
        return blog;
    }

}
