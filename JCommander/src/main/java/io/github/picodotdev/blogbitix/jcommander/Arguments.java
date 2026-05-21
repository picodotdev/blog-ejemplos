package io.github.picodotdev.blogbitix.jcommander;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Arguments {

  @Parameter
  private List<String> parameters = new ArrayList<>();

  @Parameter(names = { "--required", "-r" }, required = true, description = "Required parameter")
  private boolean required;

  @Parameter(names = { "--optional", "-o" }, description = "Optional parameter")
  private boolean optional = false;

  @Parameter(names = { "--values", "-v" }, variableArity = true, description = "Values parameter")
  private List<String> values = new ArrayList<>();

  @Parameter(names = { "--help", "-h" }, description = "Help parameter")
  private boolean help = false;

  public List<String> getParameters() {
    return parameters;
  }

  public boolean isRequired() {
    return required;
  }

  public boolean isOptional() {
    return optional;
  }

  public List<String> getValues() {
    return values;
  }

  public boolean isHelp() {
    return help;
  }
}

