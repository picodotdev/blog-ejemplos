package io.github.picodotdev.plugintapestry.components;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.internal.util.SelectModelRenderer;

import java.util.Collection;

@SupportsInformalParameters
public class MultiSelect extends AbstractField {

    /**
     * A ValueEncoder used to convert server-side objects (provided from the
     * "source" parameter) into unique client-side strings (typically IDs) and
     * back. Note: this component does NOT support ValueEncoders configured to
     * be provided automatically by Tapestry.
     */
    @Parameter
    private ValueEncoder<Object> encoder;

    /**
     * Model used to define the values and labels used when rendering.
     */
    @Parameter(required = true, allowNull = false)
    private SelectModel model;

    /**
     * The list of selected values from the {@link SelectModel}. This will be updated when the form
     * is submitted. If the value for the parameter is null, a new list will be created, otherwise the existing list
     * will be cleared. If unbound, defaults to a property of the container matching this component's id.
     * <p>
     * Prior to Tapestry 5.4, this allowed null, and a list would be created when the form was submitted. Starting
     * with 5.4, the selected list may not be null, and it need not be a list (it may be, for example, a set).
     */
    @Parameter(required = true, autoconnect = true, allowNull = false)
    private Collection<Object> selected;

    /**
     * The object that will perform input validation. The validate binding prefix is generally used to provide
     * this object in a declarative fashion.
     *
     * @since 5.2.0
     */
    @Parameter(defaultPrefix = BindingConstants.VALIDATE)
    private FieldValidator<Object> validate;

    public final Renderable mainRenderer = new Renderable() {
        public void render(MarkupWriter writer) {
            SelectModelRenderer visitor = new SelectModelRenderer(writer, encoder, false) {
                @Override
                protected boolean isOptionSelected(OptionModel optionModel, String clientValue) {
                    return selected.contains(optionModel.getValue());
                }
            };

            model.visit(visitor);
        }
    };

    @Override
    protected void processSubmission(String controlName) {
        String[] values = request.getParameters(controlName);
        values = (values == null) ? new String[0]: values;

        // Use a couple of local variables to cut down on access via bindings

        Collection<Object> selected = this.selected;

        selected.clear();

        ValueEncoder encoder = this.encoder;

        for (String value : values) {
            Object objectValue = toValue(value);

            selected.add(objectValue);
        }

        putPropertyNameIntoBeanValidationContext("selected");

        try {
            fieldValidationSupport.validate(selected, resources, validate);

            this.selected = selected;
        } catch (final ValidationException e) {
            validationTracker.recordError(this, e.getMessage());
        }

        removePropertyNameFromBeanValidationContext();
    }

    void beginRender(MarkupWriter writer) {
        writer.element("select", "name", getControlName(), "id", getClientId(), "multiple", "multiple", "disabled", getDisabledValue(), "class", cssClass);

        putPropertyNameIntoBeanValidationContext("selected");

        validate.render(writer);

        removePropertyNameFromBeanValidationContext();

        resources.renderInformalParameters(writer);

        decorateInsideField();

        mainRenderer.render(writer);
    }

    void afterRender(MarkupWriter writer) {
        writer.end();
    }

    ValueEncoder defaultEncoder() {
        return defaultProvider.defaultValueEncoder("selected", resources);
    }

    /**
     * Computes a default value for the "validate" parameter using
     * {@link org.apache.tapestry5.services.FieldValidatorDefaultSource}.
     */
    Binding defaultValidate() {
        return this.defaultProvider.defaultValidatorBinding("selected", this.resources);
    }

    String toClient(Object value) {
        return encoder.toClient(value);
    }

    Object toValue(String clientValue) { return ((Collection) encoder.toValue(clientValue)).toArray()[0]; }

    @Override
    public boolean isRequired() {
        return validate.isRequired();
    }

    public String getDisabledValue() {
        return disabled ? "disabled" : null;
    }
}
