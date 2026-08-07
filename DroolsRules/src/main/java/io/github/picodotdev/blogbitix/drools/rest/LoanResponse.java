package io.github.picodotdev.blogbitix.drools.rest;

import io.github.picodotdev.blogbitix.drools.domain.LoanApplication;

public class LoanResponse {

    private LoanApplication loanApplication;

    public LoanResponse(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
    }

    public LoanApplication getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
    }
}
