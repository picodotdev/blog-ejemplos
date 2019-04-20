package io.github.picodotdev.plugintapestry.pages;

import io.github.picodotdev.plugintapestry.misc.AppOptionGroupModel;
import io.github.picodotdev.plugintapestry.misc.AppOptionModel;
import io.github.picodotdev.plugintapestry.misc.Globals;
import io.github.picodotdev.plugintapestry.services.annotation.Csrf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.AbstractSelectModel;

import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.util.*;

/**
 * @tapestrydoc
 */
public class Index {

	private static final Logger logger = LogManager.getLogger(Index.class);

	@Property
	@Persist(value = PersistenceConstants.SESSION)
	private Long cuenta;

	@Property
	@Persist(value = PersistenceConstants.FLASH)
	private List colores;

	@Property
	@Persist(value = PersistenceConstants.FLASH)
	private String pais;

	@Property
	@Persist(value = PersistenceConstants.FLASH)
	private List paises;

	@Property
	@Persist(value = PersistenceConstants.FLASH)
	private List coloresSelect;

	@Component
	private Zone zone;

	@Component
	private Zone submitOneZone;

	@Component
	private Zone csrfZone;

	@Inject
	private AjaxResponseRenderer renderer;
	
	@Inject
	private Messages messages;

	@Environmental
	private JavaScriptSupport javascriptSupport;

	// Ciclo de vida
	void onActivate() {
		logger.info("Activating page {}", Index.class.getSimpleName());
	}

	/**
	 * Método del ciclo de vida de la página que es llamado por Tapestry al inicio de la
	 * renderización de la página.
	 */
	void setupRender() {
		logger.info("Rendering page {}", Index.class.getSimpleName());
		logger.info("Host {}", Globals.HOST.get()); // ThreadLocal example

		if (cuenta  == null) {
			cuenta = 0l;
		}
		if (colores == null) {
			colores = new ArrayList();			
		}
		if (coloresSelect == null) {
			coloresSelect = new ArrayList();
		}
		if (paises == null) {
			paises = new ArrayList();
		}
	}

	void afterRender() {
		javascriptSupport.require("app/index").invoke("init");
	}

	// Eventos
	/**
	 * Esta acción permite ver el informe de error generado por Tapestry, además de la traza de la
	 * excepción, el informe incluye mucha más información (extracto de la plantilla, información de
	 * la petición, del entorno, ...)
	 */
	void onActionFromInformeError() throws Exception {
		throw new Exception("Sí, ese enlace produce una excepción");
	}

	/**
	 * Evento que suma uno a la cuenta.
	 */
	void onActionFromSumar1Cuenta() {
		incrementCuenta();
	}

	/**
	 * Evento que también suma uno a la cuenta pero con un nombre de evento propio y sin estar
	 * asociado a un determinado componente.
	 */
	void onSumar1Cuenta() {
		incrementCuenta();
	}

	/**
	 * Evento que suma uno a la cuenta (via Ajax).
	 */
	void onActionFromSumar1CuentaAjax() throws Exception {
		// if (1 == 1) throw new Exception("Sí, ese enlace produce una excepción");

		incrementCuenta();
		// Actualizar una zona
		// return zone.getBody()
		// Actualizar varias zonas
		renderer.addRender("zone", zone).addRender("submitOneZone", submitOneZone).addRender("csrfZone", csrfZone);
	}
	
    void onPrepareForSubmitFromColoresForm() {
    	colores = new ArrayList();
		coloresSelect = new ArrayList();
		paises = new ArrayList();
    }

	void onSumar1CuentaSubmitOne() throws Exception {
		Thread.sleep(3000);
		incrementCuenta();
	}

	void onSumar1CuentaAjaxSubmitOne() throws Exception {
		Thread.sleep(3000);
		incrementCuenta();
		renderer.addRender("zone", zone).addRender("submitOneZone", submitOneZone).addRender("csrfZone", csrfZone);
	}

	void onSuccessFromSubmitOneForm1() throws Exception {
		onSubmitOne();
	}

	void onSuccessFromSubmitOneForm2() throws Exception {
		onSubmitOne();
	}

	void onSuccessFromSubmitOneForm3() throws Exception {
		onSubmitOne();
	}

	private void onSubmitOne() throws Exception {
		Thread.sleep(3000);
		incrementCuenta();
		renderer.addRender("zone", zone).addRender("submitOneZone", submitOneZone).addRender("csrfZone", csrfZone);
	}

	@Csrf
	void onSuccessFromCsrfForm() {
		incrementCuenta();
		renderer.addRender("zone", zone).addRender("submitOneZone", submitOneZone).addRender("csrfZone", csrfZone);
	}

	@Csrf
	void onSumar1CuentaCsrf() {
		incrementCuenta();
		renderer.addRender("zone", zone).addRender("submitOneZone", submitOneZone).addRender("csrfZone", csrfZone);
	}

	/**
	 * Evento que reinicializa la cuenta.
	 */
	@RequiresPermissions("cuenta:reset")
	void onActionFromReiniciarCuenta() {
		cuenta = 0l;
	}

	/**
	 * Evento que cierra la sesión del usuario actual.
	 */
	void onActionFromCerrarSesion() {
		SecurityUtils.getSecurityManager().logout(getSubject());
	}

	/**
	 * Devuelve el objeto que representa al usuario autenticado.
	 */
	public WebDelegatingSubject getSubject() {
		return (WebDelegatingSubject) SecurityUtils.getSubject();
	}
	
	public String getMensajeFormasPlurales(String key, long num) {
	    // Obtener el mensaje con las diferentes formas plurales
	    if (!messages.contains(key)) {
	        return messages.get(key);
	    }
	    
	    String message = messages.get(key);

	    // Seleccionar la forma plural adecuada del mensaje
	    ChoiceFormat format = new ChoiceFormat(message);
	    String pluralized = format.format(num);

	    // Realizar la interpolación de variables
	    return MessageFormat.format(pluralized, num);
	}

	public SelectModel getPaisesSelectModel() {
		return new AbstractSelectModel() {
			@Override
			public List<OptionGroupModel> getOptionGroups() {
				Map<String,String> europe = new HashMap<String, String>();
				Map<String,String> america = new HashMap<String, String>();
				Map<String,String> asia = new HashMap<String, String>();
				europe.put("data-tokens", "europa");
				america.put("data-tokens", "america");
				asia.put("data-tokens", "asia");

				OptionModel espana = new AppOptionModel("España", false, "espana", europe);
				OptionModel francia = new AppOptionModel("Francia", false, "francia", europe);
				OptionModel alemania = new AppOptionModel("Alemania", false, "alemania", europe);

				OptionModel eeuu = new AppOptionModel("EEUU", false, "eeuu", america);
				OptionModel mexico = new AppOptionModel("Mexico", false, "mexico", america);
				OptionModel argentina = new AppOptionModel("Argentina", false, "argentina", america);

				OptionModel china = new AppOptionModel("China", false, "china", asia);
				OptionModel japon = new AppOptionModel("Japón", false, "japon", asia);
				OptionModel india = new AppOptionModel("India", true, "india", asia);

				OptionGroupModel europaGroup = new AppOptionGroupModel("Europa", false, Collections.EMPTY_MAP, Arrays.asList(espana, francia, alemania));
				OptionGroupModel americaGroup = new AppOptionGroupModel("América", false, Collections.EMPTY_MAP, Arrays.asList(eeuu, mexico, argentina));
				OptionGroupModel asiaGroup = new AppOptionGroupModel("Asia", false, Collections.EMPTY_MAP, Arrays.asList(china, japon, india));
				return Arrays.asList(europaGroup, americaGroup, asiaGroup);
			}

			@Override
			public List<OptionModel> getOptions() {
				return null;
			}
		};
	}

	public SelectModel getColoresSelectModel() {
		return new AbstractSelectModel() {
			@Override
			public List<OptionGroupModel> getOptionGroups() {
				return null;
			}

			@Override
			public List<OptionModel> getOptions() {
				OptionModel rojo = new AppOptionModel("Rojo", false, "rojo", Collections.EMPTY_MAP);
				OptionModel azul = new AppOptionModel("Azul", false, "azul", Collections.EMPTY_MAP);
				OptionModel verde = new AppOptionModel("Verde", false, "verde", Collections.EMPTY_MAP);
				return Arrays.asList(rojo, azul, verde);
			}
		};
	}

	private void incrementCuenta() {
		if (cuenta == null) {
			cuenta = 0l;
		}
		cuenta += 1;
	}
}