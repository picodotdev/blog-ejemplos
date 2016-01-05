package io.github.picodotdev.plugintapestry.services.transaction;

import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;

public class NestedTransactionAdvice implements MethodAdvice {

	private TransactionDefinition definition;
	private TransactionService service;

	public NestedTransactionAdvice(TransactionDefinition definition, TransactionService service) {
		this.definition = definition;
		this.service = service;
	}

	public void advise(MethodInvocation invocation) {
		try {
			service.begin(definition);
			invocation.proceed();
			service.commit();
		} catch (Exception e) {
			service.rollback();
			throw e;
		}
	}
}