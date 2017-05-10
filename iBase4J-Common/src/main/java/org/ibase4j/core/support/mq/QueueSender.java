package org.ibase4j.core.support.mq;

import java.io.Serializable;
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * 队列消息发送类
 * @ClassName:  QueueSender   
 * @Description:TODO   
 * @author: sunshine  
 * @date:   2017年5月10日 上午11:47:08
 */
public class QueueSender implements QueueMessageSender{
	
    @Resource(name="jmsQueueTemplate")
	private JmsTemplate jmsTemplate;

	/**
	 * 发送一条消息到指定的队列（目标）
	 * @param queueName 队列名称
	 * @param message 消息内容
	 */
	@Override
	public void send(String queueName, final Serializable message) {
		jmsTemplate.send(queueName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(message);
			}
		});
	}
}
