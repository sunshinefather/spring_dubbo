package org.ibase4j.core.support.mq;

import java.io.Serializable;
/**
 * JMS定义发送消息
 * @ClassName:  MessageSender   
 * @Description:TODO   
 * @author: sunshine  
 * @date:   2017年5月10日 上午11:42:56
 */
public interface MessageSender {
	/**
	 * 发送
	 * @Title: send
	 * @Description: TODO  
	 * @param: @param destination
	 * @param: @param message      
	 * @return: void
	 * @author: sunshine  
	 * @throws
	 */
	void send(String destination, final Serializable message);
}
