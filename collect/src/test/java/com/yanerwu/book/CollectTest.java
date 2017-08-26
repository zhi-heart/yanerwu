package com.yanerwu.book;

import com.yanerwu.base.BaseTest;
import com.yanerwu.service.BiqugeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author Zuz
 * @Date 2017/7/7 16:52
 * @Description
 */
public class CollectTest extends BaseTest{

    @Autowired
    private BiqugeService biqugeService;

    @Test
    public void collectBookDetail() throws Exception {
        biqugeService.biqugeDetail(50);
    }

}
