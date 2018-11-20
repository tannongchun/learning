package com.kongxinag.redistutorial.extendlog;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.AppenderBase;
import com.google.common.util.concurrent.AtomicLongMap;
import com.kongxinag.redistutorial.util.DateUtil;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class RedisAppender extends AppenderBase<ILoggingEvent> {
    // 使用guava的AtomicLongMap,用于并发计数
    public static final AtomicLongMap<String> ATOMIC_LONG_MAP = AtomicLongMap.create();

    // 重写接收日志事件方法
    @Override
    protected void append(ILoggingEvent event) {
// 只监控error级别日志
        if (event.getLevel() == Level.ERROR) {
            IThrowableProxy throwableProxy = event.getThrowableProxy();
            // 确认抛出异常
            if (throwableProxy != null) {
                // 以每分钟为key，记录每分钟异常数量
                String key = DateUtil.formatDate(new Date(), "yyyyMMddHHmm");
                long errorCount = ATOMIC_LONG_MAP.incrementAndGet(key);
                if (errorCount > 10) {
                    // 超过10次触发报警代码
                    System.err.println("send bering .....");
                }
                // 清理历史计数统计，防止极端情况下内存泄露
                for (String oldKey : ATOMIC_LONG_MAP.asMap().keySet()) {
                    if (!key.equals(oldKey)) {
                        ATOMIC_LONG_MAP.remove(oldKey);
                    }
                }
            }
        }
    }
}