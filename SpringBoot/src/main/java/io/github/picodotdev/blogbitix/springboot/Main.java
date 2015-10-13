package io.github.picodotdev.blogbitix.springboot;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.Errors;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import io.github.picodotdev.blogbitix.springboot.jooq.Tables;
import io.github.picodotdev.blogbitix.springboot.jooq.tables.records.EmployeeRecord;
import io.github.picodotdev.blogbitix.springboot.misc.RecordContainer;
import io.github.picodotdev.blogbitix.springboot.service.AppService;

@SpringBootApplication
public class Main implements CommandLineRunner {

	@Autowired
	private DSLContext context;
	
	@Autowired
	private AppService dao;

	@Override
	public void run(String... args) {
		System.out.printf("Number employees: %d%n", dao.countEmployees());
		System.out.printf("Number departments: %d%n", dao.countDepartments());
		
		EmployeeRecord employee = context.newRecord(Tables.EMPLOYEE);
		Errors errors = dao.validate(employee);
		errors.getFieldErrors().stream().forEach(error -> {
			System.out.printf("%s, %s, %s%n", error.getField(), error.getCode(), error.getArguments(), error.getRejectedValue());
		});
		errors.getGlobalErrors().stream().forEach(error -> {
			System.out.printf("%s, %s, %s%n", error.getObjectName(), error.getCode(), error.getArguments());
		});
		
		List<RecordContainer> data = dao.findEmployeeDepartments(1l);
		data.stream().forEach((RecordContainer c) -> {
			System.out.printf("%s %s%n", c.getEmployee().getName(), c.getDepartment().getName());
		});
	}

	public static void main(String[] args) throws Exception {
		SpringApplication application = new SpringApplication(Main.class);
		//application.setApplicationContextClass(AnnotationConfigApplicationContext.class);
		application.setApplicationContextClass(AnnotationConfigWebApplicationContext.class);
		SpringApplication.run(Main.class, args);
	}
}
