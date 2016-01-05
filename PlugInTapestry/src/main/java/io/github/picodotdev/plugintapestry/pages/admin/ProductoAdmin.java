package io.github.picodotdev.plugintapestry.pages.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.TranslatorSource;
import org.hibernate.Session;
import org.jooq.DSLContext;

import io.github.picodotdev.plugintapestry.entities.jooq.tables.pojos.Producto;
import io.github.picodotdev.plugintapestry.misc.JooqGridDataSource;
import io.github.picodotdev.plugintapestry.misc.Pagination;
import io.github.picodotdev.plugintapestry.services.dao.JooqProductoDAO;

/**
 * @tapestrydoc
 */
public class ProductoAdmin {

	private enum Modo {
		ALTA, EDICION, LISTA
	}

	@Inject
	private JooqProductoDAO dao;

    @Inject
    private Session session;
	
	@Inject
	private DSLContext context;

	@Inject
	@Symbol(SymbolConstants.TAPESTRY_VERSION)
	@Property
	private String tapestryVersion;

	@Inject
	private TranslatorSource translatorSource;

	@Inject
	private BeanModelSource beanModelSource;

	@Inject
	@Property
	private Block listaBlock, edicionBlock, botonesEdicionBlock;

	@Inject
	private ComponentResources resources;

	@Component
	private Form form;

	private Modo modo;

	@Property
	private Producto producto;

	void onActivate(Long id, Modo modo) {
		setModo((modo == null) ? Modo.LISTA : modo, (id == null) ? null : dao.findById(id));
	}

	Object[] onPassivate() {
		return new Object[] { (producto == null) ? null : producto.getId(), (modo == null) ? null : modo.toString().toLowerCase() };
	}

	void setupRender() {
		if (modo == null) {
			setModo(Modo.LISTA, null);
		}
	}

	void onPrepareForSubmitFromForm(Long id) {
		if (id != null) {
			// Si se envía un id se trata de una edición, buscarlo
			producto = dao.findById(id);
		}
		if (producto == null) {
			producto = new Producto();
		}
	}

	Object onCanceledFromForm() {
		setModo(Modo.LISTA, null);
		return ProductoAdmin.class;
	}

	void onSuccessFromForm() {
		dao.persist(producto);

		setModo(Modo.LISTA, null);
	}

	void onNuevo() {
		setModo(Modo.ALTA, null);
	}

	void onEliminarTodos() {
		dao.removeAll();
		setModo(Modo.LISTA, null);
	}

	void onEliminar(Long id) {
		producto = dao.findById(id);
		dao.remove(producto);

		setModo(Modo.LISTA, null);
	}

	public boolean hasProductos() {
		return getSource().getAvailableRows() > 0;
	}

	public GridDataSource getSource() {
		return new JooqGridDataSource(context, Producto.class) {
			@Override
			public int getAvailableRows() {
				return (int) dao.countAll();
			}
			
			@Override
			public List find(Pagination pagination) {
				return dao.findAll(pagination);
			}
		};
	}

	public BeanModel<Producto> getModel() {
		BeanModel<Producto> model = beanModelSource.createDisplayModel(Producto.class, resources.getMessages());
		model.exclude("id");
		model.add("action", null).label("").sortable(false);
		return model;
	}

	public Block getBlock() {
		switch (modo) {
			case ALTA:
			case EDICION:
				return edicionBlock;
			default:
			case LISTA:
				return listaBlock;
		}
	}
	
	// La anotacion @Cached permite cachar el resultado de un método de forma
	// que solo se evalúe
	// una vez independientemente del número de veces que se llame en la
	// plantilla de visualización.
	@Cached
	public Map<String, String> getLabels() {
		Map<String, String> m = new HashMap<String, String>();
		switch (modo) {
			case ALTA:
				m.put("titulo", "Alta producto");
				m.put("guardar", "Crear producto");
				break;
			case EDICION:
				m.put("titulo", "Modificación producto");
				m.put("guardar", "Modificar producto");
				break;
			default:
		}
		return m;
	}

	private void setModo(Modo modo, Producto producto) {
		switch (modo) {
			case ALTA:
				this.producto = new Producto();
				break;
			case EDICION:
				if (producto == null) {
					modo = Modo.ALTA;
					this.producto = new Producto();
				} else {
					this.producto = producto;
				}
				break;
			default:
			case LISTA:
				this.producto = null;
				break;

		}
		this.modo = modo;
	}
}