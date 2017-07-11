package com.yanerwu;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.entity.MvList;
import io.netty.util.internal.ConcurrentSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author Zuz
 * @Date 2017/5/8 11:49
 * @Description
 */
@Component
public class Cache {

    public static ConcurrentMap<String, MvList> map = new ConcurrentHashMap<>();
    public static ConcurrentSet<String> movieWordSet = new ConcurrentSet<>();
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DbUtilsTemplate yanerwuTemplate;

//    @Scheduled(fixedDelay = 1000 * 60 * 60)
//    private void cacheMovieWord() {
//        long l = System.currentTimeMillis();
//        String sql = "select text from es_word where type=1 and char_length(text)<=10";
//        List<Map<String, Object>> maps = yanerwuTemplate.find(sql);
//        for (Map<String, Object> m : maps) {
//            movieWordSet.add(String.valueOf(m.get("text")).trim());
//        }
//        logger.info("缓存词库成功,耗时{}", System.currentTimeMillis() - l);
//    }
}
