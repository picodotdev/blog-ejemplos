package io.github.picodotdev.blogbitix.springboot.misc;

import io.github.picodotdev.blogbitix.springboot.jooq.tables.records.DepartmentRecord;
import io.github.picodotdev.blogbitix.springboot.jooq.tables.records.EmployeeRecord;

public class RecordContainer {

	private EmployeeRecord employee;
	private DepartmentRecord department;

	public RecordContainer() {		
	}
	
	public EmployeeRecord getEmployee() {
		return employee;
	}
	
	public void setEmployee(EmployeeRecord employee) {
		this.employee = employee;
	}
	
    public String getEmployeeName() {
        return employee.getName();
    }

	public DepartmentRecord getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentRecord department) {
		this.department = department;
	}
	
    public String getDepartmentName() {
        return department.getName();
    }
}