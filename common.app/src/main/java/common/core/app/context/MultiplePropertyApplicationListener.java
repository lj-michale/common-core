package common.core.app.context;

import common.core.common.util.ApplicationContextUtil;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MultiplePropertyApplicationListener implements ApplicationListener<ApplicationPreparedEvent> {

	@Override
	public void onApplicationEvent(ApplicationPreparedEvent event) {
		ApplicationContextUtil.putApplicationContext(event.getApplicationContext());

		ConfigurableEnvironment env = event.getApplicationContext().getEnvironment();
		env.setIgnoreUnresolvableNestedPlaceholders(true);
		Map<String, Object> configs = new HashMap<>();
		Iterator<String> names = ConfigContext.getConfigs().stringPropertyNames().iterator();
		String name;
		while (names.hasNext()) {
			name = names.next();
			if(!env.containsProperty(name)){
				configs.put(name, ConfigContext.getConfigs().get(name));
			}
		}
		if (!configs.isEmpty()) {
			MapPropertySource mapPropertySource = new MapPropertySource(ConfigContext.class.getName(), configs);
			env.getPropertySources().addFirst(mapPropertySource);
		}
	}

}
