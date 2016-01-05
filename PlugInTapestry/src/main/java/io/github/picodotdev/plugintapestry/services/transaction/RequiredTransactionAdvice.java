package io.github.picodotdev.plugintapestry.services.transaction;

import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;

public class RequiredTransactionAdvice implements MethodAdvice {

	private TransactionDefinition definition;
	private TransactionService service;

	public RequiredTransactionAdvice(TransactionDefinition definition, TransactionService service) {
		this.definition = definition;
		this.service = service;
	}

	public void advise(MethodInvocation invocation) {
		boolean isNew = service.beginIfNoPresent(definition);

		try {
			invocation.proceed();

			if (isNew) {
				service.commit();
			}
		} catch (Exception e) {
			if (isNew) {
				service.rollback();
			}
			throw e;
		}
	}
}