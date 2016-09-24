package io.github.picodotdev.blogbitix.springbootjaxrsoauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ErrorController {

    @RequestMapping("/401")
    public void render401(HttpServletResponse response) throws Exception {
        response.getWriter().write("401\n");
    }

    @RequestMapping("/403")
    public void render403(HttpServletResponse response) throws Exception {
        response.getWriter().write("403\n");
    }

    @RequestMapping("/404")
    public void render404(HttpServletResponse response) throws Exception {
        response.getWriter().write("404\n");
    }
}