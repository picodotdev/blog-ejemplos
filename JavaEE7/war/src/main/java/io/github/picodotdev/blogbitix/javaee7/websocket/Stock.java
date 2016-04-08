package io.github.picodotdev.blogbitix.javaee7.websocket;

import io.github.picodotdev.blogbitix.javaee.ejb.SupermarketLocal;
import io.github.picodotdev.blogbitix.javaee.jpa.Product;
import io.github.picodotdev.blogbitix.javaee.jpa.Purchase;

import javax.ejb.EJB;
import javax.enterprise.event.Observes;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ServerEndpoint("/stock")
public class Stock {

    private static List<Session> sessions = new ArrayList<>();;

    @EJB
    private SupermarketLocal supermarket;

    @OnOpen
    public void open(Session s) {
        sessions.add(s);
    }

    @OnClose
    public void close(Session s, CloseReason c) {
        sessions.remove(s);
    }

    @OnError
    public void error(Session s, Throwable t) {
    }

    @OnMessage
    public void receiveMessage(String message) {
        System.out.println(message);
    }

    void onPurchase(@Observes Purchase purchase) {
        List<Product> products = purchase.getItems().stream().map(i -> { return i.getProduct(); }).collect(Collectors.toList());
        String json = toJson(products);
        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendText(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String toJson(List<Product> products) {
        JsonArrayBuilder array = Json.createArrayBuilder();
        for (Product product : products) {
            JsonObject object = Json.createObjectBuilder()
                    .add("id", product.getId())
                    .add("stock", product.getStock())
                    .build();
            array.add(object);
        }
        StringWriter writer = new StringWriter();
        Json.createWriter(writer).writeArray(array.build());
        return writer.toString();
    }
}
