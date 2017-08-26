package com.yanerwu.book;

import com.yanerwu.base.BaseTest;
import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.entity.BookDetail;
import com.yanerwu.utils.Tools;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author Zuz
 * @Date 2017/8/26 18:53
 * @Description
 */
public class SimTest extends BaseTest {

    @Autowired
    private DbUtilsTemplate bookTemplate;

    @Test
    public void sim() {
        String sql = "select * from book_detail where book_id=3";
        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
        List<BookDetail> bookDetails = bookTemplate.find(BookDetail.class, sql, processor);

        for (BookDetail bd : bookDetails) {
            String s = Tools.matcherReplace("。(.*[八,一,中,文,Ｗ,．,８,１,Ｚ,Ｗ,Ｃ,Ｏ]+.*Ｍ)", bd.getContent());
            bd.setContent(s);
            bookTemplate.update(bd);
        }
    }
}
