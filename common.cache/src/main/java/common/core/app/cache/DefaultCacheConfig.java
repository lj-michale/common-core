package common.core.app.cache;

import org.springframework.beans.BeansException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.assertion.util.AssertErrorUtils;

public abstract class DefaultCacheConfig implements CachingConfigurer, ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	private static final Logger logger = LoggerFactory.getLogger(DefaultCacheConfig.class);

	@Bean
	@Override
	public CacheManager cacheManager() {
		CacheManager cacheManager = createCacheManager();
		addCaches(new CacheRegistryImpl(cacheManager));
		return cacheManager;
	}

	private CacheManager createCacheManager() {
		CacheSettings settings = cacheSettings();
		String provider = settings.getCacheProvider();
 		AssertErrorUtils.assertNotNull(provider, "please config CacheProvider");
		try {
			return (CacheManager) applicationContext.getAutowireCapableBeanFactory().createBean(Class.forName(provider));
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new DefaultCacheKeyGenerator();
	}

	@Bean
	public abstract CacheSettings cacheSettings();

	protected abstract void addCaches(CacheRegistry registry);

}
