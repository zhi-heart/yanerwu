package com.yanerwu.book;

import com.yanerwu.base.BaseTest;
import com.yanerwu.service.BlogService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author Zuz
 * @Date 2017/12/7 13:53
 * @Description
 */
public class BlogTest extends BaseTest {
    @Autowired
    private BlogService blogService;

    @Test
    public void collect() {
        blogService.collect();
    }
}
