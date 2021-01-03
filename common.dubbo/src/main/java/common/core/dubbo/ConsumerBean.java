package common.core.dubbo;

import com.alibaba.dubbo.config.AbstractConfig;
import com.alibaba.dubbo.config.ConsumerConfig;

public class ConsumerBean extends ConsumerConfig {

	private static final long serialVersionUID = 8929103111273970825L;

	public ConsumerBean() {
		super();
		AbstractConfig.appendProperties(this);
	}

}
