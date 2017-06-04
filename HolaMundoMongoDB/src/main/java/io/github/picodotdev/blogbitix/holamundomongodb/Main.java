package io.github.picodotdev.blogbitix.holamundomongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private MongoClient mongoClient;

    @Bean
    public MongoClient buildMongoClient() {
        return new MongoClient("localhost");
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        MongoDatabase test = mongoClient.getDatabase("test");
        MongoCollection users = test.getCollection("users");

        // Insertar un documento
        users.insertOne(new Document().append("username", "smith").append("age", 30));

        // Listar bases de datos y colecciones
        mongoClient.listDatabases().forEach((Consumer<Document>) (Document d) -> { System.out.println(d.toJson()); });
        test.listCollections().forEach((Consumer<Document>) (Document d) -> { System.out.println(d.toJson()); });

        // Búsqueda
        users.find().forEach((Consumer<Document>) (Document d) -> { System.out.println(d.toJson()); });
        users.find(Filters.eq("username", "smith")).forEach((Consumer<Document>) (Document d) -> { System.out.println(d.toJson()); });

        // Actualización
        users.updateMany(Filters.eq("username", "smith"), Updates.set("age", 32));

        users.find(Filters.eq("username", "smith")).forEach((Consumer<Document>) (Document d) -> { System.out.println(d.toJson()); });

        // Agregación
        System.out.println(users.count());

        // Eliminación
        users.deleteMany(Filters.eq("username", "smith"));
    }
}
