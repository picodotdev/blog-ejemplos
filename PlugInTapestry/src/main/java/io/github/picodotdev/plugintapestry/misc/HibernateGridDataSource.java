package io.github.picodotdev.plugintapestry.misc;

import java.util.List;

import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;
import org.hibernate.Session;

@SuppressWarnings({ "rawtypes" })
public abstract class HibernateGridDataSource implements GridDataSource {

	private Session session;
	private Class clazz;

	private int start;
	private List results;

	public HibernateGridDataSource() {		
	}
	
	public HibernateGridDataSource(Session session, Class clazz) {
		this.session = session;
		this.clazz = clazz;
	}
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
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