package io.github.picodotdev.plugintapestry.misc;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;

import java.util.List;
import java.util.Map;

public class AppOptionGroupModel implements OptionGroupModel {

    private String label;
    private boolean disabled;
    private Map<String, String> attributes;
    private List<OptionModel> options;

    public AppOptionGroupModel(String label, boolean disabled, Map<String, String> attributes, List<OptionModel> options) {
        this.label = label;
        this.disabled = disabled;
        this.attributes = attributes;
        this.options = options;
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
    public List<OptionModel> getOptions() {
        return options;
    }
}
