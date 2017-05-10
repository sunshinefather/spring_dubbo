package org.ibase4j.core.support.mq.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.support.email.Email;
import org.ibase4j.core.util.EmailUtil;
/**
 * 发送邮件监听
 * @ClassName:  SendEmailListener   
 * @Description:TODO   
 * @author: sunshine  
 * @date:   2017年5月10日 上午11:53:20
 */
public class SendEmailListener implements MessageListener {
	private final Logger logger = LogManager.getLogger();

	public void onMessage(Message message) {
		try {
			ObjectMessage msg = ((ObjectMessage) message);
			Email email = (Email) msg.getObject();
			logger.info("@sunshine邮件将发送到：" + email.getSendTo());
			boolean result = EmailUtil.sendEmail(email);
			if(result){
				msg.acknowledge();
			}
		} catch (JMSException e) {
			logger.error(e);
		}
	}
}
