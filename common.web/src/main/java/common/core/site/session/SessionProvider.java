package common.core.site.session;

public interface SessionProvider {
	public SessionData loadSessionData(String id,String ip);
	
	public SessionData createSessionData(String id,String ip);

	public void updateSessionData(SessionData sessionData);
}
