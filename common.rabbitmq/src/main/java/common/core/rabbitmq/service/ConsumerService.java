package common.core.rabbitmq.service;


public interface ConsumerService{
	
	public Boolean exec(String msg);

	public String getServiceName();
}
