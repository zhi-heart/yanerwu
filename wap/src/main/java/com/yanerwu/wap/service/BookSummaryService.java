package com.yanerwu.wap.service;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.common.Page;
import com.yanerwu.entity.BookSummary;
import com.yanerwu.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zuz
 * @version 1.0
 * @Description 列表
 */
@Service
public class BookSummaryService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DbUtilsTemplate yanerwuTemplate;

    public Page findPage(BookSummary query, Page page) {
        List<Object> params = new ArrayList<>();
        StringBuilder sql2 = new StringBuilder("select * from book_summary t where 1=1 ");
        if (Tools.isNotEmpty(query.getId())) {
            sql2.append(" and  t.id = ? ");
            params.add(query.getId());
        }
        if (Tools.isNotEmpty(query.getQidianId())) {
            sql2.append(" and  t.qidian_id = ? ");
            params.add(query.getQidianId());
        }
        if (Tools.isNotEmpty(query.getQidianUrl())) {
            sql2.append(" and  t.qidian_url = ? ");
            params.add(query.getQidianUrl());
        }
        if (Tools.isNotEmpty(query.getBiqugeId())) {
            sql2.append(" and  t.biquge_id = ? ");
            params.add(query.getBiqugeId());
        }
        if (Tools.isNotEmpty(query.getBiqugeUrl())) {
            sql2.append(" and  t.biquge_url = ? ");
            params.add(query.getBiqugeUrl());
        }
        if (Tools.isNotEmpty(query.getName())) {
            sql2.append(" and  t.name like ? ");
            params.add(String.format("%%%s%%",query.getName()));
        }
        if (Tools.isNotEmpty(query.getTypeId())) {
            sql2.append(" and  t.type_id = ? ");
            params.add(query.getTypeId());
        }
        if (Tools.isNotEmpty(query.getType())) {
            sql2.append(" and  t.type = ? ");
            params.add(query.getType());
        }
        if (Tools.isNotEmpty(query.getStatus())) {
            sql2.append(" and  t.status = ? ");
            params.add(query.getStatus());
        }
        if (Tools.isNotEmpty(query.getSummary())) {
            sql2.append(" and  t.summary = ? ");
            params.add(query.getSummary());
        }
        if (Tools.isNotEmpty(query.getAuthor())) {
            sql2.append(" and  t.author = ? ");
            params.add(query.getAuthor());
        }
        if (Tools.isNotEmpty(query.getCreateTime())) {
            sql2.append(" and  t.create_time = ? ");
            params.add(query.getCreateTime());
        }
        if (Tools.isNotEmpty(query.getUpdateTime())) {
            sql2.append(" and  t.update_time = ? ");
            params.add(query.getUpdateTime());
        }
        sql2.append("order by rank_cnt desc");
        return yanerwuTemplate.findPage(page, sql2.toString(), params.toArray(), BookSummary.class);
    }

    public <T> T getById(BookSummary bookSummary) {
        return (T) yanerwuTemplate.getById(bookSummary);
    }


}
