package io.github.picodotdev.plugintapestry.misc;

import org.apache.tapestry5.OptionModel;

import java.util.Map;

public class AppOptionModel implements OptionModel {

    private String label;
    private boolean disabled;
    private Object value;
    private Map<String, String> attributes;

    public AppOptionModel(String label, boolean disabled, Object value, Map<String, String> attributes) {
        this.label = label;
        this.disabled = disabled;
        this.value = value;
        this.attributes = attributes;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
