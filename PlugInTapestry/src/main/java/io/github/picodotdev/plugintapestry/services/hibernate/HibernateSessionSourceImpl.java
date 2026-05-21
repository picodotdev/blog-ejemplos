package io.github.picodotdev.plugintapestry.services.hibernate;

import org.apache.tapestry5.hibernate.HibernateSessionSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

public class HibernateSessionSourceImpl implements HibernateSessionSource {

	private SessionFactory sessionFactory;
	private Configuration configuration;

	public HibernateSessionSourceImpl(ApplicationContext context) {
		this.sessionFactory = (SessionFactory) context.getBean("sessionFactory");

		// http://stackoverflow.com/questions/2736100/how-can-i-get-the-hibernate-configuration-object-from-spring
		LocalSessionFactoryBean localSessionFactoryBean = (LocalSessionFactoryBean) context.getBean("&sessionFactory");
		this.configuration = localSessionFactoryBean.getConfiguration();
	}

	@Override
	public Session create() {
		return sessionFactory.openSession();
	}

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public Configuration getConfiguration() {
		return configuration;
	}
}