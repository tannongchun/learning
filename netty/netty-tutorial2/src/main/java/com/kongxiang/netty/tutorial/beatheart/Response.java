package com.kongxiang.netty.tutorial.beatheart;

import java.io.Serializable;

/**
 * @version 1.0
 * @description:
 * @projectName: com.kongxiang.netty.tutorial.runtime
 * @className: netty-tutorial2
 * @author:谭农春
 * @createTime:2018/11/14 20:50
 */
public class Response implements Serializable {

    private String id;
    private String name;
    private  String responseMessage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
