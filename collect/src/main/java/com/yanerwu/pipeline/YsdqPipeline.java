package com.yanerwu.pipeline;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.entity.MvList;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @Author Zuz
 * @Date 2017/5/8 00:32
 * @Description
 */
public class YsdqPipeline implements Pipeline{

    private DbUtilsTemplate yanerwuTemplate;

    public YsdqPipeline(DbUtilsTemplate yanerwuTemplate){
        this.yanerwuTemplate=yanerwuTemplate;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        MvList m=resultItems.get("m");
        if (null != m) {
            yanerwuTemplate.insert(m);
        }
    }
}
