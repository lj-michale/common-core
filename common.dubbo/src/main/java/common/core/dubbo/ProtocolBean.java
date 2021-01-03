package common.core.dubbo;

import com.alibaba.dubbo.config.AbstractConfig;
import com.alibaba.dubbo.config.ProtocolConfig;

public class ProtocolBean extends ProtocolConfig {
	private static final long serialVersionUID = -3383724508712009089L;
	public ProtocolBean() {
		super();
		AbstractConfig.appendProperties(this);
	}

	public ProtocolBean(String name) {
		super(name);
		AbstractConfig.appendProperties(this);
	}

}
