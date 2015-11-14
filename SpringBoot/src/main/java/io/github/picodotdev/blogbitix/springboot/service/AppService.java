package io.github.picodotdev.blogbitix.springboot.service;

import java.util.List;

import org.springframework.validation.Errors;

import io.github.picodotdev.blogbitix.springboot.jooq.tables.records.DepartmentRecord;
import io.github.picodotdev.blogbitix.springboot.misc.RecordContainer;

public interface AppService {

	long countEmployees();
	long countDepartments();
	DepartmentRecord findDepartment(long id);
	Errors validate(Object object);
	List<RecordContainer> findDepartmentEmployees(Long id);
	List<RecordContainer> findDepartmentsEmployees();
}