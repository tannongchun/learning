package com.kongxiang.netty.tutorial.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version 1.0
 * @description:
 * @projectName: com.kongxiang.netty.tutorial.utils
 * @className: netty-tutorial2
 * @author:谭农春
 * @createTime:2018/11/14 21:57
 */
public class Utils {

    public static  String  currentMS(){
        return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
