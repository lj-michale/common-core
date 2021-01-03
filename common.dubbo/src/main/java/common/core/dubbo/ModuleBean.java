package common.core.dubbo;

import com.alibaba.dubbo.config.AbstractConfig;
import com.alibaba.dubbo.config.ModuleConfig;

public class ModuleBean extends ModuleConfig {

	private static final long serialVersionUID = -1893304177415471839L;

	public ModuleBean() {
		super();
		AbstractConfig.appendProperties(this);
	}

	public ModuleBean(String name) {
		super(name);
		AbstractConfig.appendProperties(this);
	}

}
