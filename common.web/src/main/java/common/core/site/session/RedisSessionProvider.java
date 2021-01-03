package common.core.site.session;

import common.core.app.monitor.status.ServiceStatus;
import common.core.app.monitor.status.ServiceStatusMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisSessionProvider extends AbstractSessionProvider implements ServiceStatusMonitor {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ServiceStatus getServiceStatus() throws Exception {
        RedisConnection redisConnection  = redisTemplate.getConnectionFactory().getConnection();
        if( null == redisConnection || redisConnection.isClosed())
            return  ServiceStatus.DOWN;
        return ServiceStatus.UP;
    }

    @Override
    public String getServiceName() {
        return "Redis Sessions";
    }

    @Override
    protected SessionData generateSessionData(String sessionKey, int expirationTime) throws Exception {
        SessionData sessionData = (SessionData) redisTemplate.opsForValue().get(sessionKey);
        if(null != sessionData && sessionData.getMaxInactiveInterval() != expirationTime){
            redisTemplate.opsForValue().set(sessionKey, sessionData, expirationTime, TimeUnit.SECONDS);
        }
        return sessionData;
    }

    @Override
    protected void modifySessionData(String sessionKey, SessionData sessionData) throws Exception {
        redisTemplate.opsForValue().set(sessionKey, sessionData, sessionData.getMaxInactiveInterval(), TimeUnit.SECONDS);
    }


}
