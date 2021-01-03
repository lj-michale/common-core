package common.core.apollo;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.springframework.beans.BeansException;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * apollo配置自动更新
 */
@Component
public class PropertiesRefresher implements ApplicationContextAware {

    @Resource
    RefreshScope refreshScope;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private ApplicationContext applicationContext;

    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {
        refreshProperties(changeEvent);
    }

    public void  refreshProperties(ConfigChangeEvent changeEvent){
        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
        refreshScope.refreshAll();
    }
}
