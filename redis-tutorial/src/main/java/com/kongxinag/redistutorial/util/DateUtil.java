package com.kongxinag.redistutorial.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version 1.0
 * @description:
 * @projectName: com.kongxinag.redistutorial.util
 * @className: redis-tutorial
 * @author:谭农春
 * @createTime:2018/11/4 17:16
 */
public class DateUtil {

    private static  final SimpleDateFormat _formate = new SimpleDateFormat();

    /**
     *  格式化日期
     * @param date
     * @param formate
     * @return
     */
    public static String  formatDate(Date date , String formate){
        _formate.applyPattern(formate);
        return  _formate.format(date);
    }
}
