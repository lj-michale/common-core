package common.core.dubbo;

import com.alibaba.dubbo.config.AbstractConfig;
import com.alibaba.dubbo.config.RegistryConfig;

public class RegistryBean extends RegistryConfig {

	private static final long serialVersionUID = -4488957829845275594L;
	public RegistryBean() {
		super();
		AbstractConfig.appendProperties(this);
	}

	public RegistryBean(String name) {
		super(name);
		AbstractConfig.appendProperties(this);
	}

}
