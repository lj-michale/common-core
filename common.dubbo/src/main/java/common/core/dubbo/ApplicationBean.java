package common.core.dubbo;

import com.alibaba.dubbo.config.AbstractConfig;
import com.alibaba.dubbo.config.ApplicationConfig;

public class ApplicationBean extends ApplicationConfig {
	private static final long serialVersionUID = -2115221940720904102L;

	public ApplicationBean() {
		super();
	}

	public ApplicationBean(String name) {
		super(name);
		AbstractConfig.appendProperties(this);
	}

}
