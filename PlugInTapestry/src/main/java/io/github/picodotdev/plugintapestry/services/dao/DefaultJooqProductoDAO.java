package io.github.picodotdev.plugintapestry.services.dao;

import io.github.picodotdev.plugintapestry.entities.jooq.Tables;
import io.github.picodotdev.plugintapestry.entities.jooq.tables.pojos.Producto;
import io.github.picodotdev.plugintapestry.entities.jooq.tables.records.ProductoRecord;
import io.github.picodotdev.plugintapestry.misc.Direction;
import io.github.picodotdev.plugintapestry.misc.Globals;
import io.github.picodotdev.plugintapestry.misc.Pagination;
import io.github.picodotdev.plugintapestry.misc.Sort;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SortField;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static io.github.picodotdev.plugintapestry.entities.jooq.tables.Producto.PRODUCTO;

public class DefaultJooqProductoDAO implements GenericDAO<Producto>, JooqProductoDAO {

    private DSLContext context;

    public DefaultJooqProductoDAO(DSLContext context) {
        this.context = context;
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        // ThreadLocal example
        System.out.printf("Host (from service): %s%n", Globals.HOST.get());

        return context.selectFrom(PRODUCTO).where(PRODUCTO.ID.eq(id)).fetchOneInto(Producto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return context.selectFrom(PRODUCTO).fetchInto(Producto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll(Pagination pagination) {
        //return context.selectFrom(Tables.PRODUCTO).orderBy(PRODUCTO.CANTIDAD, PRODUCTO.ID).seek(3l, 5l).limit(10).fetchInto(Producto.class);
        return context.selectFrom(Tables.PRODUCTO).orderBy(getSortFields(pagination)).limit(pagination.getOffset(), pagination.getNum()).fetchInto(Producto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public long countAll() {
        return context.selectCount().from(Tables.PRODUCTO).fetchOne(0, Long.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void persist(Producto object) {
        getRecord(object).store();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(Producto object) {
        getRecord(object).delete();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeAll() {
        context.deleteFrom(PRODUCTO).execute();
    }
    
    private ProductoRecord getRecord(Producto object) {
        ProductoRecord record = null;
        if (object.getId() == null) {
            record = context.newRecord(PRODUCTO);
        } else {
            record = context.selectFrom(PRODUCTO).where(PRODUCTO.ID.eq(object.getId())).fetchOne();
        }
        record.from(object);
        return record;
    }

    private List<SortField<?>> getSortFields(Pagination pagination) {
        return pagination.getSort().stream().map((Sort s) -> {
            Field<?> field = PRODUCTO.field(s.getProperty().toUpperCase());
            SortField<?> sortField = ((s.getDirection() == Direction.ASCENDING)) ? field.asc() : field.desc();
            return sortField;
        }).collect(Collectors.toList());
    }
}