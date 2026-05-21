package io.github.picodotdev.plugintapestry.services.hibernate;

import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PreDeleteEvent;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.picodotdev.plugintapestry.services.spring.DummyService;

public class ProductoEventAdapter extends HibernateEventAdapter {

	private static final long serialVersionUID = 1L;

	@Autowired
	private DummyService dummy;
	
	public void setDummy(DummyService dummy) {
		this.dummy = dummy;		
	}
	
	@Override
	public void onPostDelete(PostDeleteEvent event) {
		dummy.process("postDelete", event.getEntity());
	}

	@Override
	public boolean onPreDelete(PreDeleteEvent event) {
		dummy.process("preDelete", event.getEntity());
		return false;
	}

	@Override
	public void onPostUpdate(PostUpdateEvent event) {
		dummy.process("postUpdate", event.getEntity());
	}

	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {
		dummy.process("preUpdate", event.getEntity());
		return false;
	}

	@Override
	public void onPostInsert(PostInsertEvent event) {
		dummy.process("postInsert", event.getEntity());
	}

	@Override
	public boolean onPreInsert(PreInsertEvent event) {
		dummy.process("preInsert", event.getEntity());
		return false;
	}
}