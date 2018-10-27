package io.github.picodotdev.jist;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Arguments {

  @Parameter
  private List<String> parameters = new ArrayList<>();

  @Parameter(names = { "--user", "-u" }, description = "GitHub username")
  private String user;

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }
}