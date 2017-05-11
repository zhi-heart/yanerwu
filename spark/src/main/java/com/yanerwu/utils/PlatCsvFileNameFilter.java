package com.yanerwu.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 * @Author Zuz
 * @Date 2017/5/10 16:15
 * @Description
 */
public class PlatCsvFileNameFilter implements FilenameFilter {

    private String rule;

    public PlatCsvFileNameFilter(String rule){
        this.rule = rule;
    }

    @Override
    public boolean accept(File dir, String name) {
//        System.out.println(String.format("%s/%s",dir,name));
        if(Pattern.compile(rule).matcher(name).find()){
            return true;
        }
        return false;
    }
}
