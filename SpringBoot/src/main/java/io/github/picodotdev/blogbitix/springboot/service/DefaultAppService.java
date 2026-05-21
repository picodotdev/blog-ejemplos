package io.github.picodotdev.blogbitix.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import io.github.picodotdev.blogbitix.springboot.jooq.Tables;
import io.github.picodotdev.blogbitix.springboot.jooq.tables.records.DepartmentRecord;
import io.github.picodotdev.blogbitix.springboot.jooq.tables.records.EmployeeRecord;
import io.github.picodotdev.blogbitix.springboot.misc.RecordContainer;
import io.github.picodotdev.blogbitix.springboot.validator.EmployeeValidator;

public class DefaultAppService implements AppService {

		private DSLContext context;
		private javax.validation.Validator validator;
		private List<Validator> validators;

		public DefaultAppService(DSLContext context, javax.validation.Validator validator, EmployeeValidator employeeValidator) {
			this.context = context;
			this.validator = validator;
			this.validators = new ArrayList<Validator>();
			this.validators.add(employeeValidator);
		}

		@Override
		public long countEmployees() {
			return context.selectCount().from(Tables.EMPLOYEE).fetchOneInto(Long.class);
		}

		@Override
		public long countDepartments() {
			return context.selectCount().from(Tables.DEPARTMENT).fetchOneInto(Long.class);
		}
		
		@Override
		public DepartmentRecord findDepartment(long id) {
		    return context.select().from(Tables.DEPARTMENT).where(Tables.DEPARTMENT.ID.eq(id)).fetchOneInto(DepartmentRecord.class);
		}

		@Override
		public Errors validate(Object object) {
			List<Validator> supportedValidators = validators.stream().filter((Validator v) -> {
				return v.supports(object.getClass());
			}).collect(Collectors.toList());

			DataBinder binder = new DataBinder(object);
			binder.addValidators(new SpringValidatorAdapter(validator));
			binder.addValidators(supportedValidators.toArray(new Validator[0]));
			binder.validate();
			return binder.getBindingResult();
		}

		@Override
		public List<RecordContainer> findDepartmentEmployees(Long id) {
			return context.select().from(Tables.DEPARTMENT).join(Tables.EMPLOYEE_DEPARTMENT).on(Tables.DEPARTMENT.ID.eq(Tables.EMPLOYEE_DEPARTMENT.DEPARTMENT_ID))
				.join(Tables.EMPLOYEE).on(Tables.EMPLOYEE.ID.eq(Tables.EMPLOYEE_DEPARTMENT.EMPLOYEE_ID)).where(Tables.DEPARTMENT.ID.eq(id))
				.fetch((Record record) -> {
					RecordContainer container = new RecordContainer();
					container.setEmployee(record.into(EmployeeRecord.class));
					container.setDepartment(record.into(DepartmentRecord.class));
					return container;
			});
		}
		
        @Override
        public List<RecordContainer> findDepartmentsEmployees() {
            return context.select().from(Tables.DEPARTMENT).join(Tables.EMPLOYEE_DEPARTMENT).on(Tables.DEPARTMENT.ID.eq(Tables.EMPLOYEE_DEPARTMENT.DEPARTMENT_ID))
                .join(Tables.EMPLOYEE).on(Tables.EMPLOYEE.ID.eq(Tables.EMPLOYEE_DEPARTMENT.EMPLOYEE_ID))
                .fetch((Record record) -> {
                    RecordContainer container = new RecordContainer();
                    container.setEmployee(record.into(EmployeeRecord.class));
                    container.setDepartment(record.into(DepartmentRecord.class));
                    return container;
            });
        }
}