package io.github.picodotdev.plugintapestry.misc;

import java.util.List;

import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;
import org.jooq.DSLContext;

@SuppressWarnings({ "rawtypes" })
public abstract class JooqGridDataSource implements GridDataSource {

	private DSLContext context;
	private Class clazz;

	private int start;
	private List results;

	public JooqGridDataSource() {		
	}
	
	public JooqGridDataSource(DSLContext context, Class clazz) {
		this.context = context;
		this.clazz = clazz;
	}
	
	public DSLContext getContext() {
		return context;
	}

	public void setContext(DSLContext context) {
		this.context = context;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	@Override
	public void prepare(int start, int end, List<SortConstraint> sort) {
		Pagination pagination = new Pagination(start, end - start + 1, Sort.fromSortConstraint(sort));

		this.start = start;
		
		results = find(pagination);
	}

	public abstract int getAvailableRows();
	public abstract List find(Pagination pagination);

	@Override
	public Object getRowValue(int i) {
		return results.get(i - this.start);
	}

	@Override
	public Class getRowType() {
		return clazz;
	}
}