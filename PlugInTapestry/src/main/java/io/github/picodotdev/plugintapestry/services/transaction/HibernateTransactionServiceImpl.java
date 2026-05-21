package io.github.picodotdev.plugintapestry.services.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Stack;

import org.apache.tapestry5.ioc.services.PerthreadManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

public class HibernateTransactionServiceImpl implements TransactionService {

	private Session session;
	private Stack<Transaction> transactionStack;

	public HibernateTransactionServiceImpl(Session session, PerthreadManager manager) {
		this.session = session;
		this.transactionStack = new Stack<Transaction>();

		manager.addThreadCleanupCallback(new Runnable() {
			@Override
			public void run() {
				cleanup();
			}
		});
	}

	public boolean beginIfNoPresent(TransactionDefinition definition) {
		if (isWithinTransaction()) {
			return false;
		}
		begin(definition);
		return true;
	}

	public void begin(TransactionDefinition definition) {
		Transaction transaction = session.beginTransaction();
		configure(session, transaction, definition);
		transactionStack.push(transaction);
	}

	public void commit() {
		if (isWithinTransaction()) {
			transactionStack.pop().commit();
		}
	}

	public void rollback() {
		if (isWithinTransaction()) {
			transactionStack.pop().rollback();
		}
	}

	public boolean isWithinTransaction() {
		return !transactionStack.empty();
	}

	private void cleanup() {
		for (Transaction transaction : transactionStack) {
			transaction.rollback();
		}
	}

	private void configure(Session session, Transaction transaction, final TransactionDefinition definition) {
		if (definition.getReadOnly() != null) {
			session.setDefaultReadOnly(definition.getReadOnly());
		}
		if (definition.getTimeout() != null) {
			transaction.setTimeout(definition.getTimeout());
		}
		session.doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				if (definition.getReadOnly() != null) {
					connection.setReadOnly(definition.getReadOnly());
				}
				if (definition.getIsolation() != null) {
					connection.setTransactionIsolation(definition.getIsolation().intValue());
				}

			}
		});
	}
}