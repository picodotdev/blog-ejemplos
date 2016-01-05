package io.github.picodotdev.plugintapestry.services.transaction;

import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.hibernate.HibernateSessionSource;
import org.apache.tapestry5.ioc.services.PerthreadManager;
import org.hibernate.Session;

public class HibernateSessionManagerImpl implements HibernateSessionManager {

	private Session session;

	public HibernateSessionManagerImpl(HibernateSessionSource source, PerthreadManager manager) {
		this.session = source.create();
		
		manager.addThreadCleanupCallback(new Runnable() {
			@Override
			public void run() {
				cleanup();
			}
		});
	}

	public void abort() {
		session.getTransaction().rollback();
	}

	public void commit() {
		session.getTransaction().commit();
	}

	public Session getSession() {
		return session;
	}

	private void cleanup() {
		session.close();
	}
}