package es.com.blogspot.elblogdepicodev.plugintapestry.services;

...

public class AppModule {

	public static void bind(ServiceBinder binder) {
		...

		// Servicios para la gestión de transacciones
		binder.bind(HibernateSessionManager.class, HibernateSessionManagerImpl.class).scope(ScopeConstants.PERTHREAD).withId("AppHibernateSessionManager");
		binder.bind(TransactionAdvisor.class, TransactionAdvisorImpl.class);
		binder.bind(TransactionService.class, HibernateTransactionServiceImpl.class).scope(ScopeConstants.PERTHREAD);

		...
	}
	 
	public static void contributeServiceOverride(MappedConfiguration<Class,Object> configuration, @Local HibernateSessionManager sessionManager) {
		configuration.add(HibernateSessionManager.class, sessionManager);
	}

	...

	/**
	 * Dar soporte transaccional a los servicios con una interfaz que cumplan el patrón (los advices se aplican a los métodos de una interfaz).
	 */
	@Match({ "*DAO" })
	public static void adviseTransaction(TransactionAdvisor advisor, MethodAdviceReceiver receiver) {
		advisor.addAdvice(receiver);
	}
}