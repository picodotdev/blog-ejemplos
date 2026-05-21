package io.github.picodotdev.plugintapestry.services.transaction;

public class TransactionDefinition {

	private Propagation propagation;
	private Integer isolation;
	private Boolean readOnly;
	private Integer timeout;

	public TransactionDefinition(Propagation propagation, Integer isolation, Boolean readOnly, Integer timeout) {
		this.propagation = propagation;
		this.isolation = isolation;
		this.readOnly = readOnly;
		this.timeout = timeout;
	}
	
	public Propagation getPropagation() {
		return propagation;
	}

	public void setPropagation(Propagation propagation) {
		this.propagation = propagation;
	}

	public Integer getIsolation() {
		return isolation;
	}

	public void setIsolation(Integer isolation) {
		this.isolation = isolation;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
}