package io.github.picodotdev.blogbitix.drools.rest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.drools.commands.SetActiveAgendaGroup;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.api.runtime.KieSession;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.internal.command.CommandFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.picodotdev.blogbitix.drools.domain.LoanApplication;

@RestController
@RequestMapping("/loan")
public class LoanController {

    private KieContainer kieContainer;
    private DMNRuntime dmnRuntime;

    public LoanController(KieContainer kieContainer, DMNRuntime dmnRuntime) {
        this.kieContainer = kieContainer;
        this.dmnRuntime = dmnRuntime;
    }

    @PostMapping("/rule")
    public ResponseEntity<LoanResponse> rule(@RequestBody LoanRequest loanRequest) {
        System.out.println("Applicant  id: " + loanRequest.getApplicant().getId());
        System.out.println("Applicant  age: " + loanRequest.getApplicant().getAge());

        List<Command> commands = Arrays.asList(
            CommandFactory.newInsert(loanRequest.getApplicant(), "applicant"),
            CommandFactory.newInsert(loanRequest.getLoanApplication(), "application"),
            new SetActiveAgendaGroup("applicationGroup"),
            CommandFactory.newFireAllRules());

        KieSession kieSession = kieContainer.newKieSession();
        ExecutionResults executionResults = kieSession.execute(CommandFactory.newBatchExecution(commands));
        LoanApplication application = (LoanApplication) executionResults.getResults().get("application");

        System.out.println("Application: " + application);

        return ResponseEntity.ok(new LoanResponse(application));
    }

    @PostMapping("/decision")
    public ResponseEntity<LoanResponse> decision(@RequestBody LoanRequest loanRequest) {
        System.out.println("Applicant  id: " + loanRequest.getApplicant().getId());
        System.out.println("Applicant  age: " + loanRequest.getApplicant().getAge());

        String namespace = "https://kie.org/dmn/_C83DFD16-A42A-46BE-A843-370444580E0F";
        String modelName = "loan-application-age-limit";

        DMNModel dmnModel = dmnRuntime.getModel(namespace, modelName);

        DMNContext dmnContext = dmnRuntime.newContext();
        dmnContext.set("Applicant", loanRequest.getApplicant());
        dmnContext.set("Application", loanRequest.getLoanApplication());
        DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);

        HashMap<String, Object> result = (HashMap) dmnResult.getDecisionResults().getFirst().getResult();
        LoanApplication application = loanRequest.getLoanApplication();
        application.setApproved((boolean) result.get("approved"));
        application.setExplanation((String) result.get("explanation"));

        return ResponseEntity.ok(new LoanResponse(application));
    }
}
