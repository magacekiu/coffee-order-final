package edu.iu.habahram.coffeeorder.repository;

import edu.iu.habahram.coffeeorder.model.*;
import org.springframework.stereotype.Repository;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class OrderRepository {
    private AtomicInteger receiptIdGenerator = new AtomicInteger(0);

    public Receipt add(OrderData order) throws Exception {
        Beverage beverage = null;
        switch (order.beverage().toLowerCase()) {
            case "dark roast":
                beverage = new DarkRoast();
                break;
            case "houseblend":
                beverage = new HouseBlend();
                break;
            case "espresso":
                beverage = new Espresso();
                break;
            case "decaf":
                beverage = new Decaf();
                break;
        }
        if (beverage == null) {
            throw new Exception("Beverage type '%s' is not valid!".formatted(order.beverage()));
        }
        for(String condiment : order.condiments()) {
            switch (condiment.toLowerCase()) {
                case "milk":
                   beverage = new Milk(beverage);
                   break;
                case "mocha":
                    beverage = new Mocha(beverage);
                    break;
                case "whip":
                    beverage = new Whip(beverage);
                    break;
                case "soy":
                    beverage = new Soy(beverage);
                    break;
                default:
                    throw new Exception("Condiment type '%s' is not valid".formatted(condiment));
            }
        }
        int receiptId = receiptIdGenerator.incrementAndGet();
        Receipt receipt = new Receipt(receiptId, beverage.getDescription(), beverage.cost());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("db.txt", true))) {
            writer.write(receiptId + ", " + receipt.cost() + ", " + receipt.description());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receipt;
    }

    public List<OrderData> findAll() throws IOException {
        List<OrderData> orders = new ArrayList<>();
        Path path = Paths.get("db.txt"); 
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                String[] parts = line.split(",");
                String beverage = parts[0];
                List<String> condiments = Arrays.asList(parts).subList(1, parts.length);
                orders.add(new OrderData(beverage, condiments));
            }
        } 
        return orders;
    }
}