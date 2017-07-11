package com.yanerwu.pipeline;

import com.yanerwu.Cache;
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
public class YsdqPipeline implements Pipeline {

    private DbUtilsTemplate yanerwuTemplate;

    public YsdqPipeline(DbUtilsTemplate yanerwuTemplate) {
        this.yanerwuTemplate = yanerwuTemplate;
    }

    public static void main(String[] args) {
        System.out.println("杨光的快乐生活(电影版)".replaceAll("(\\(.*\\))", ""));
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        MvList m = resultItems.get("m");
        if (null != m) {
            String sql = "delete from mv_list where original_url = ?";
            yanerwuTemplate.update(sql, m.getOriginalUrl());
            yanerwuTemplate.insert(m);
            String nameStr = m.getName();

            String insertSql = "insert into es_word (text, type) values (?,?)";

            for (String n : nameStr.replace("：", "/").split("/")) {
//                n = n.replaceAll("\\pP|\\pS", "");
                n = n.trim();
                if (!Cache.movieWordSet.contains(n)) {
                    yanerwuTemplate.update(insertSql, new Object[]{
                            n,
                            1
                    });
                    Cache.movieWordSet.add(n);
                }
            }

            for (String a : m.getActor().replace(" ", ",").split(",")) {
//                a = a.replaceAll("\\pP|\\pS", "");
                if (!Cache.movieWordSet.contains(a)) {
                    yanerwuTemplate.update(insertSql, new Object[]{
                            a,
                            2
                    });
                    Cache.movieWordSet.add(a);
                }
            }

        }
    }
}
