package com.yanerwu;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.entity.MvList;
import io.netty.util.internal.ConcurrentSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
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

    @Scheduled(cron = "0 0 * * * ?")
    @PostConstruct
    private void cacheMovieWord() {
        String sql = "select text from es_word where char_length(text)<=10";
        List<Map<String, Object>> maps = yanerwuTemplate.find(sql);
        for (Map<String, Object> m : maps) {
            movieWordSet.add(String.valueOf(m.get("text")).trim());
        }
    }

    @Autowired
    private DbUtilsTemplate yanerwuTemplate;
}
