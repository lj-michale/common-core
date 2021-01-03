package common.core.dubbo;

import com.alibaba.dubbo.config.AbstractConfig;
import com.alibaba.dubbo.config.ProviderConfig;

public class ProviderBean extends ProviderConfig {

	private static final long serialVersionUID = 7403034518469945489L;

	public ProviderBean() {
		super();
		AbstractConfig.appendProperties(this);
	}

}
