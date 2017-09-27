package session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Î¬»¤È«¾Ösession
 * @author hekai
 *
 */
public class LocalSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
	    System.out.println("create");
		LocalSessions.addSession(se.getSession().getId(), se.getSession());
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
	    System.out.println("destory");
		LocalSessions.delSession(se.getSession().getId());
	}

}
