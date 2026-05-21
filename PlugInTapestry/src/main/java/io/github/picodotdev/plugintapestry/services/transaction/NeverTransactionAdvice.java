package io.github.picodotdev.plugintapestry.services.transaction;

import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;

public class NeverTransactionAdvice implements MethodAdvice {

	 private TransactionService service;

	 public NeverTransactionAdvice(TransactionService service) {
		  this.service = service;
	 }

	 public void advise(MethodInvocation invocation) {
		  if (service.isWithinTransaction()) {
				throw new RuntimeException("Hay una transacción activa y se require ninguna");
		  }
		  invocation.proceed();
	 }
}