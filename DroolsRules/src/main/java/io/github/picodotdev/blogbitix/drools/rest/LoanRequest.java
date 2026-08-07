package io.github.picodotdev.blogbitix.drools.rest;

import io.github.picodotdev.blogbitix.drools.domain.Applicant;
import io.github.picodotdev.blogbitix.drools.domain.LoanApplication;

public class LoanRequest {

    private Applicant applicant;
    private LoanApplication loanApplication;

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public LoanApplication getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
    }
}
