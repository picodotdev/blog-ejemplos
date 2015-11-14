package io.github.picodotdev.blogbitix.springboot;

import java.time.LocalDateTime;
import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.Errors;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import io.github.picodotdev.blogbitix.springboot.jooq.Keys;
import io.github.picodotdev.blogbitix.springboot.jooq.Tables;
import io.github.picodotdev.blogbitix.springboot.jooq.tables.records.DepartmentRecord;
import io.github.picodotdev.blogbitix.springboot.jooq.tables.records.EmployeeDepartmentRecord;
import io.github.picodotdev.blogbitix.springboot.jooq.tables.records.EmployeeRecord;
import io.github.picodotdev.blogbitix.springboot.misc.RecordContainer;
import io.github.picodotdev.blogbitix.springboot.service.AppService;

@SpringBootApplication
public class Main implements CommandLineRunner {

	@Autowired
	private DSLContext context;
	
	@Autowired
	private AppService service;

	@Override
	public void run(String... args) {
        System.out.printf("Number employees: %d%n", service.countEmployees());
        System.out.printf("Number departments: %d%n", service.countDepartments());

        System.out.println();
        System.out.println("# Relations (with 1+N problem)");
        DepartmentRecord department = service.findDepartment(1l);
        List<EmployeeDepartmentRecord> eds = department.fetchChildren(Keys.DEPARTMENT_ID);
        for (EmployeeDepartmentRecord ed : eds) {
            EmployeeRecord employee = ed.fetchParent(Keys.EMPLOYEE_ID);    
            System.out.printf("%s %s%n", employee.getName(), department.getName());
        }

        System.out.println();
        System.out.println("# Multipletables (no 1+N)");
        List<RecordContainer> data = service.findDepartmentEmployees(1l);
        data.stream().forEach((RecordContainer c) -> {
            System.out.printf("%s %s%n", c.getEmployee().getName(), c.getDepartment().getName());
        });

        System.out.println();
        System.out.println("# Validation");
        EmployeeRecord employee = context.newRecord(Tables.EMPLOYEE);
        employee.setBirthday(LocalDateTime.now().plusDays(1));
        Errors errors = service.validate(employee);
        errors.getFieldErrors().stream().forEach(error -> {
            System.out.printf("%s, %s, %s%n", error.getField(), error.getCode(), error.getArguments(), error.getRejectedValue());
        });
        errors.getGlobalErrors().stream().forEach(error -> {
            System.out.printf("%s, %s, %s%n", error.getObjectName(), error.getCode(), error.getArguments());
        });
	}

	public static void main(String[] args) throws Exception {
		SpringApplication application = new SpringApplication(Main.class);
		//application.setApplicationContextClass(AnnotationConfigApplicationContext.class);
		application.setApplicationContextClass(AnnotationConfigWebApplicationContext.class);
		SpringApplication.run(Main.class, args);
	}
}
