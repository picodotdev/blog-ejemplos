package io.github.picodotdev.plugintapestry.misc;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.grid.SortConstraint;
import org.hibernate.criterion.Order;

public class Sort {

	private String property;
	private Direction direction;

	public Sort(String property, Direction direction) {
		this.property = property;
		this.direction = direction;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Order getOrder() {
		switch (direction) {
			case ASCENDING:
				return Order.asc(property);
			case DESCENDING:
				return Order.desc(property);
			default:
				return null;
		}
	}

	public static List<Sort> fromSortConstraint(List<SortConstraint> sort) {
		List<Sort> cs = new ArrayList<Sort>();

		for (SortConstraint s : sort) {
			String property = s.getPropertyModel().getPropertyName();
			Direction direction = Direction.UNSORTED;

			switch (s.getColumnSort()) {
				case ASCENDING:
					direction = Direction.ASCENDING;
					break;
				case DESCENDING:
					direction = Direction.DESCENDING;
					break;
				default:
			}

			Sort c = new Sort(property, direction);
			cs.add(c);
		}

		return cs;
	}
}