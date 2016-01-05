package io.github.picodotdev.plugintapestry.services.transaction;

import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;

public class MandatoryTransactionAdvice implements MethodAdvice {
	 private TransactionService service;

	 public MandatoryTransactionAdvice(TransactionService service) {
		  this.service = service;
	 }

	 public void advise(MethodInvocation invocation) {
		  if (!service.isWithinTransaction()) {
				throw new RuntimeException("Debe haber una transacción");
		  }
		  invocation.proceed();
	 }
}