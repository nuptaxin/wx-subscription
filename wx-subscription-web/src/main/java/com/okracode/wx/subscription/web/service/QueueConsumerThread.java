package com.okracode.wx.subscription.web.service;

import com.okracode.wx.subscription.web.util.DataQueue;
import javax.annotation.Resource;
import org.apache.log4j.Logger;

import com.okracode.wx.subscription.web.bean.recv.RecvTextMessage;
import com.okracode.wx.subscription.web.dao.TextMessageDao;
import org.springframework.stereotype.Component;

/**
 * @ClassName: QueueConsumerThread
 * @Description: TODO
 * @author renzx
 * @date May 4, 2017
 */
@Component
public class QueueConsumerThread {
    private static final Logger LOG = Logger.getLogger(QueueConsumerThread.class);
    @Resource
    private TextMessageDao textMessageDao;

    public QueueConsumerThread() {
        new Thread(()->{
            while (true) {
                try {
                    RecvTextMessage msg = DataQueue.queue.take();
                    textMessageDao.insertOneRecvMsg(msg);
                    LOG.debug("向数据库成功插入一组内容");
                } catch (InterruptedException e) {
                    LOG.error("向数据库插入数据出错", e);
                }
            }
        }).start();
    }
}