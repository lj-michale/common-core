package common.core.site.session;

import common.core.app.cache.CacheException;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.StopWatch;
import common.core.web.context.WebSetting;

public abstract class AbstractSessionProvider implements  SessionProvider{

    protected final Logger logger = LoggerFactory.getLogger(SessionProvider.class);

    protected final String getCacheKey(String sessionId, String ip) {
        return new StringBuffer("session:").append(sessionId).toString();
    }

    protected final int expirationTime() {
        return WebSetting.get().getSessionTimeOut();
    }

    @Override
    public SessionData createSessionData(String id, String ip) {
        SessionData sessionData = new SessionData(id, ip);
        sessionData.setCreationTime(System.currentTimeMillis());
        sessionData.setMaxInactiveInterval(this.expirationTime());
        return sessionData;
    }

    @Override
    public SessionData loadSessionData(String id, String ip) {
        SessionData sessionData = null;
        String sessionKey = getCacheKey(id, ip);
        StopWatch watch = new StopWatch();
        try {
            sessionData = generateSessionData(sessionKey, expirationTime());
        } catch (Exception e) {
            throw new CacheException(e);
        } finally {
            logger.debug("loadSessionData, cachedKey={},hit={}, elapsedTime={}", sessionKey, null != sessionData, watch.elapsedTime());
        }
        return sessionData;
    }

    @Override
    public void updateSessionData(SessionData sessionData) {
        AssertErrorUtils.assertNotNull(sessionData.getId(), "Session id is null");
        String sessionKey = getCacheKey(sessionData.getId(), sessionData.getIp());
        StopWatch watch = new StopWatch();
        try {
            modifySessionData(sessionKey, sessionData);
        } catch (Exception e) {
            throw new CacheException(e);
        } finally {
            logger.debug("updateSessionData, Key={},expirationTime={}, elapsedTime={}", sessionKey, expirationTime(), watch.elapsedTime());

        }
    }
     /**
     *从缓存读取sessionData
     */
    protected abstract  SessionData generateSessionData(String sessionKey, int expirationTime) throws Exception;

    /**
     *修改缓存sessionData
     */
    protected abstract  void  modifySessionData(String sessionKey, SessionData sessionData) throws Exception;
}
