package io.github.picodotdev.plugintapestry.services.transaction;

import java.lang.reflect.Method;

import org.apache.tapestry5.ioc.MethodAdviceReceiver;

public class TransactionAdvisorImpl implements TransactionAdvisor {
	private TransactionService service;

	public TransactionAdvisorImpl(TransactionService service) {
		this.service = service;
	}

	public void addAdvice(final MethodAdviceReceiver receiver) {
		for (Method method : receiver.getInterface().getMethods()) {
			Transactional transactional = method.getAnnotation(Transactional.class);

			if (transactional != null) {
				adviceMethod(buildTransactionDefinition(transactional), method, receiver);
			}
		}
	}

	private void adviceMethod(TransactionDefinition definition, Method method, MethodAdviceReceiver receiver) {
		switch (definition.getPropagation()) {
			case REQUIRED:
				receiver.adviseMethod(method, new RequiredTransactionAdvice(definition, service));
				break;
			case NESTED:
				receiver.adviseMethod(method, new NestedTransactionAdvice(definition, service));
				break;
			case MANDATORY:
				receiver.adviseMethod(method, new MandatoryTransactionAdvice(service));
				break;
			case NEVER:
				receiver.adviseMethod(method, new NeverTransactionAdvice(service));
				break;
			case SUPPORTS:
				break;
		}
	}

	private TransactionDefinition buildTransactionDefinition(Transactional transactional) {
		return new TransactionDefinition(transactional.propagation(), (transactional.isolation() == -1) ? null : transactional.isolation(), transactional.readonly(),
				(transactional.timeout() == -1) ? null : transactional.timeout());
	}
}