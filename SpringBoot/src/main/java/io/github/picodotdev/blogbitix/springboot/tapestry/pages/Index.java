package io.github.picodotdev.blogbitix.springboot.tapestry.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import io.github.picodotdev.blogbitix.springboot.misc.RecordContainer;
import io.github.picodotdev.blogbitix.springboot.service.AppService;

public class Index {

    @Inject
    private AppService appService;

    @Property
    private Long numEmployees;

    @Property
    private Long numDepartments;

    @Property
    private List<RecordContainer> containers;

    void setupRender() {
        numEmployees = appService.countEmployees();
        numDepartments = appService.countDepartments();
        containers = appService.findEmployeeDepartments(1l);
    }
}