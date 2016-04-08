package io.github.picodotdev.blogbitix.javaee7.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;

    @Inject
    private HttpServletRequest request;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        try {
            request.login(username, password);

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
            response.sendRedirect("index.xhtml");
            return null;
        } catch (Exception e) {
            return "login.xhtml?e=1";
        }
    }
}
