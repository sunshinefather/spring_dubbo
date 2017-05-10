package org.ibase4j.core.support.mq;

import java.io.Serializable;
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
/**
 * 主题发送类
 * @ClassName:  TopicSender   
 * @Description:TODO   
 * @author: sunshine  
 * @date:   2017年5月10日 上午11:45:10
 */
public class TopicSender implements TopicMessageSender {

	@Resource(name="jmsTopicTemplate")
	private JmsTemplate jmsTemplate;

	/**
	 * 发送一条消息到指定的订阅者（目标）
	 * @param topicName 主题名
	 * @param message 消息内容
	 */
	@Override
	public void send(String topicName, final Serializable message) {
		jmsTemplate.send(topicName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(message);
			}
		});
	}
}