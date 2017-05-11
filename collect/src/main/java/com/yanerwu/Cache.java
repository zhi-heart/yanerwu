package com.yanerwu;

import com.yanerwu.entity.MvList;
import org.springframework.scheduling.annotation.Scheduled;
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

}
