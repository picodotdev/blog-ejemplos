package io.github.picodotdev.plugintapestry.components;

import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentAction;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Any;
import org.apache.tapestry5.corelib.components.Checkbox;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Events({ EventConstants.VALIDATE })
public class ListSelect {

	@Parameter(defaultPrefix = BindingConstants.PROP)
	@Property
	private List model;
	
	@Parameter(defaultPrefix = BindingConstants.PROP)
	@Property
	private List values;

	@Property
	private Object value;
	
	@Property
	private Boolean all;

	@Environmental
	private FormSupport formSupport;
	
	@Environmental
	private JavaScriptSupport javascriptSupport;

	@Environmental
	private ValidationTracker tracker;

	@Component
	private Checkbox allCheckbox;
	
	@Component
	private Any checkboxs;
	
	private static final ProcessSubmission PROCESS_SUBMISSION = new ProcessSubmission();

	void beginRender() {
		all = values.containsAll(model);
	}
	
	void afterRender() {
		JSONObject spec = new JSONObject();
		spec.put("all", allCheckbox.getClientId());
		spec.put("checkboxs", checkboxs.getClientId());
		javascriptSupport.require("app/listSelect").invoke("init").with(spec);
		
		// If we are inside a form, ask FormSupport to store PROCESS_SUBMISSION in its list of
		// actions to do on submit.
		// If I contain other components, their actions will already be in the list, before
		// PROCESS_SUBMISSION. That is
		// because this method, afterRender(), is late in the sequence. This guarantees
		// PROCESS_SUBMISSION will be
		// executed on submit AFTER the components I contain are processed (which includes their
		// validation).

		if (formSupport != null) {
			formSupport.store(this, PROCESS_SUBMISSION);
		}
	}	

	public boolean isSelected() {
		return values.contains(value);
	}

	public void setSelected(boolean selected) {
		if (selected) {
			values.add(value);
		} else {
			values.remove(value);
		}
	}
	
	private void processSubmission() {
		// Validate. We ensured in afterRender() that the components I contain have already been validated.        
        // Error if the number of persons chosen is less than specified by the min parameter.
        if (values.isEmpty()) {
            tracker.recordError("Debes elegir al menos un elemento");
            return;
        }
    }

	private static class ProcessSubmission implements ComponentAction<ListSelect> {

		private static final long serialVersionUID = 4736960369589830393L;

		public void execute(ListSelect component) {
			component.processSubmission();
		}

		@Override
		public String toString() {
			return this.getClass().getSimpleName() + ".ListSelect";
		}
	};
}