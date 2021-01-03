package commom.core.activemq.service;

import javax.jms.JMSException;

import commom.core.activemq.common.MQProduct;

public class MQProductDemo {
	public static void main(String[] args) throws JMSException {
		MQProduct mqp=new MQProduct();
		mqp.sendMessageByQueue("ice2", "ice second test666");
	}
	
	
}
