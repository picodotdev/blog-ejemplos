package io.github.picodotdev.blogbitix.springbootconfigvalidated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "app")
@Validated
public class AppConfiguration {

    @Min(3)
    @Max(10)
    private Integer numeric;
    @Pattern(regexp = "[A-Z]\\d{3,}")
    private String regexp;
    @NotBlank
    private String password;

    public Integer getNumeric() {
        return numeric;
    }

    public void setNumeric(Integer numeric) {
        this.numeric = numeric;
    }

    public String getRegexp() {
        return regexp;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
