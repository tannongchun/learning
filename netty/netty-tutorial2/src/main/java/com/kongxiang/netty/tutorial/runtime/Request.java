package com.kongxiang.netty.tutorial.runtime;

import java.io.Serializable;

/**
 * @version 1.0
 * @description:
 * @projectName: com.kongxiang.netty.tutorial.runtime
 * @className: netty-tutorial2
 * @author:谭农春
 * @createTime:2018/11/14 20:52
 */
public class Request implements Serializable {

    private String id;
    private String name;
    private String requestMessage;

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

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }
}
