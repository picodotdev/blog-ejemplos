package io.github.picodotdev.blogbitix.springboot.service;

import java.util.List;

import org.springframework.validation.Errors;

import io.github.picodotdev.blogbitix.springboot.misc.RecordContainer;

public interface AppService {

	long countEmployees();
	long countDepartments();
	Errors validate(Object object);
	List<RecordContainer> findEmployeeDepartments(Long id);
}