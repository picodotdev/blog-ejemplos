package io.github.picodotdev.plugintapestry.services.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.github.picodotdev.plugintapestry.entities.hibernate.Producto;
import io.github.picodotdev.plugintapestry.misc.Pagination;
import io.github.picodotdev.plugintapestry.misc.Sort;

@SuppressWarnings({ "unchecked" })
public class DefaultHibernateProductoDAO implements HibernateProductoDAO {

	protected SessionFactory sessionFactory;

	public DefaultHibernateProductoDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

    @Override
	@Transactional(readOnly = true)
	public Producto findById(Long id) {
		return (Producto) sessionFactory.getCurrentSession().get(Producto.class, id);
	}

    @Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return findAll(null);
	}

    @Override
	@Transactional(readOnly = true)
	public List<Producto> findAll(Pagination pagination) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Producto.class);

		if (pagination != null) {
			List<Order> orders = getOrders(pagination);
			for (Order order : orders) {
				criteria.addOrder(order);
			}
		}

		if (pagination != null) {
			criteria.setFirstResult(pagination.getOffset());
			criteria.setFetchSize(pagination.getNum());
		}

		return criteria.list();
	}

    @Override
	@Transactional(readOnly = true)
	public long countAll() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Producto.class);

		criteria.setProjection(Projections.rowCount());

		return (long) criteria.uniqueResult();
	}

    @Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void persist(Producto object) {
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}

    @Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(Producto object) {
		sessionFactory.getCurrentSession().delete(object);
	}

    @Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeAll() {
		String hql = String.format("delete from %s", Producto.class.getName());
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
	}
	
    private List<Order> getOrders(Pagination pagination) {
        List<Order> orders = new ArrayList<Order>();
        for (Sort s : pagination.getSort()) {
            Order o = s.getOrder();
            if (o != null) {
                orders.add(o);
            }
        }
        return orders;
    }
}