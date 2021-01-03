package common.core.dubbo;

import com.alibaba.dubbo.config.AbstractConfig;
import com.alibaba.dubbo.config.MonitorConfig;

public class MonitorBean extends MonitorConfig {

	private static final long serialVersionUID = 8754135982129202642L;
	public MonitorBean() {
		super();
		AbstractConfig.appendProperties(this);
	}

	public MonitorBean(String name) {
		super(name);
		AbstractConfig.appendProperties(this);
	}

}
