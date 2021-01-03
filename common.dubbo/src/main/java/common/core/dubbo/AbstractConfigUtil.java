package common.core.dubbo;

import com.alibaba.dubbo.config.AbstractConfig;

public class AbstractConfigUtil extends AbstractConfig {

	private static final long serialVersionUID = 9032124848883530519L;

	protected static void appendProperties(AbstractConfig config) {
		AbstractConfig.appendProperties(config);
	}

}
