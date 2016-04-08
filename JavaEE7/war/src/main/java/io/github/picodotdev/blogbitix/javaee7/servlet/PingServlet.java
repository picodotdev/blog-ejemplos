package io.github.picodotdev.blogbitix.javaee7.servlet;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.picodotdev.blogbitix.javaee.ejb.SupermarketLocal;
import io.github.picodotdev.blogbitix.javaee.jpa.Product;

@WebServlet("/ping")
public class PingServlet extends HttpServlet {
    
    private static final long serialVersionUID = 4985581640560427927L;

    @EJB
    private SupermarketLocal supermarket;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getOutputStream().print(MessageFormat.format("pong (%d)", supermarket.findProducts().size()));
    }
}