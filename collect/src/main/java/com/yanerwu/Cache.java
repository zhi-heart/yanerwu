package com.yanerwu;

import com.yanerwu.entity.MvList;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author Zuz
 * @Date 2017/5/8 11:49
 * @Description
 */
public class Cache {
    public static ConcurrentMap<String, MvList> map = new ConcurrentHashMap<>();
}
