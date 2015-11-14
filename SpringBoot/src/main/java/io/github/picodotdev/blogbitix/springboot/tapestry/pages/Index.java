package io.github.picodotdev.blogbitix.springboot.tapestry.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import io.github.picodotdev.blogbitix.springboot.misc.RecordContainer;
import io.github.picodotdev.blogbitix.springboot.service.AppService;

public class Index {

    @Inject
    private AppService service;

    @Property
    private Long numEmployees;

    @Property
    private Long numDepartments;

    @Property
    private List<RecordContainer> containers;
    
    @Property
    private RecordContainer container;

    void setupRender() {
        numEmployees = service.countEmployees();
        numDepartments = service.countDepartments();
        containers = service.findDepartmentsEmployees();
    }
}