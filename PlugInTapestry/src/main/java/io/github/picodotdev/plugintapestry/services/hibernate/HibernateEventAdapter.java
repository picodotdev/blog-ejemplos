package io.github.picodotdev.plugintapestry.services.hibernate;

import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.event.spi.PreDeleteEvent;
import org.hibernate.event.spi.PreDeleteEventListener;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;

public class HibernateEventAdapter implements PreInsertEventListener, PostInsertEventListener, PreUpdateEventListener, PostUpdateEventListener, PreDeleteEventListener,
		PostDeleteEventListener {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean requiresPostCommitHanding(EntityPersister persister) {
		return false;
	}

	@Override
	public void onPostDelete(PostDeleteEvent event) {
	}

	@Override
	public boolean onPreDelete(PreDeleteEvent event) {
		return false;
	}

	@Override
	public void onPostUpdate(PostUpdateEvent event) {
	}

	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {
		return false;
	}

	@Override
	public void onPostInsert(PostInsertEvent event) {
	}

	@Override
	public boolean onPreInsert(PreInsertEvent event) {
		return false;
	}
}