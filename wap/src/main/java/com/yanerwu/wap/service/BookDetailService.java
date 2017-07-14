package com.yanerwu.wap.service;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.common.Page;
import com.yanerwu.entity.BookDetail;
import com.yanerwu.utils.Tools;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zuz
 * @version 1.0
 * @Description BookDetail
 */@Service
public class BookDetailService{

    private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DbUtilsTemplate yanerwuTemplate;

	public Page findPage(BookDetail query,Page page) {
		List<Object> params=new ArrayList<>();
        StringBuilder sql2 = new StringBuilder("select id,title from book_detail t where 1=1 ");
        if(Tools.isNotEmpty(query.getId())) {
            sql2.append(" and  t.id = ? ");
            params.add(query.getId());
        }
        if(Tools.isNotEmpty(query.getBookId())) {
            sql2.append(" and  t.book_id = ? ");
            params.add(query.getBookId());
        }
        if(Tools.isNotEmpty(query.getNo())) {
            sql2.append(" and  t.no = ? ");
            params.add(query.getNo());
        }
        if(Tools.isNotEmpty(query.getTitle())) {
            sql2.append(" and  t.title = ? ");
            params.add(query.getTitle());
        }
        if(Tools.isNotEmpty(query.getTitleMd5())) {
            sql2.append(" and  t.title_md5 = ? ");
            params.add(query.getTitleMd5());
        }
        if(Tools.isNotEmpty(query.getContent())) {
            sql2.append(" and  t.content = ? ");
            params.add(query.getContent());
        }
        if(Tools.isNotEmpty(query.getUpdateTime())) {
            sql2.append(" and  t.update_time = ? ");
            params.add(query.getUpdateTime());
        }
        if(Tools.isNotEmpty(query.getSourceUrl())) {
            sql2.append(" and  t.source_url = ? ");
            params.add(query.getSourceUrl());
        }
        if (StringUtils.isNotBlank(page.getOrderField())) {
            sql2.append(String.format("order by %s %s",page.getOrderField(),page.getOrderDirection()));
        }
        return yanerwuTemplate.findPage(page, sql2.toString(), params.toArray(), BookDetail.class);
	}
	
	public BookDetail findBookDetail(BookDetail bookDetail){
        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
        List<Object> params=new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select id,no,title,content,book_id from book_detail t where 1=1 ");
        if(Tools.isNotEmpty(bookDetail.getId())) {
            sql.append(" and  t.id = ? ");
            params.add(bookDetail.getId());
        }
        if(Tools.isNotEmpty(bookDetail.getBookId())) {
            sql.append(" and  t.book_id = ? ");
            params.add(bookDetail.getBookId());
        }
        if(Tools.isNotEmpty(bookDetail.getNo())) {
            sql.append(" and  t.no = ? ");
            params.add(bookDetail.getNo());
        }

        List<BookDetail> bookDetails = yanerwuTemplate.find(BookDetail.class, sql.toString(), params.toArray(), processor);
        if(bookDetails.size()==1){
            return bookDetails.get(0);
        }
        return null;
    }
	
	
}
