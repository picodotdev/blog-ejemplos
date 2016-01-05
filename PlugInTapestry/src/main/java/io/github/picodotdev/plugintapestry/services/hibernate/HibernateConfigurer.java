package io.github.picodotdev.plugintapestry.services.hibernate;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateConfigurer {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ProductoEventAdapter productoEventAdapter;
	
	@PostConstruct
	public void registerListeners() {
		SessionFactoryImpl sfi = (SessionFactoryImpl) sessionFactory;
	    EventListenerRegistry elr = sfi.getServiceRegistry().getService(EventListenerRegistry.class);

        elr.setListeners(EventType.PRE_INSERT, productoEventAdapter);
        elr.setListeners(EventType.PRE_UPDATE, productoEventAdapter);
        elr.setListeners(EventType.PRE_DELETE, productoEventAdapter);
        elr.setListeners(EventType.POST_INSERT, productoEventAdapter);
        elr.setListeners(EventType.POST_UPDATE, productoEventAdapter);
        elr.setListeners(EventType.POST_DELETE, productoEventAdapter);
	}
}