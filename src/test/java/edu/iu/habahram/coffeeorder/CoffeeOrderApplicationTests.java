package edu.iu.habahram.coffeeorder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import edu.iu.habahram.coffeeorder.model.*;
import edu.iu.habahram.coffeeorder.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CoffeeOrderApplicationTests {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void orderEspressoNoCondiments() throws Exception {
        var order = new OrderData("Espresso", Collections.emptyList());
        var receipt = orderRepository.add(order);
        assertEquals("Espresso", receipt.description());
        assertEquals(1.34f, receipt.cost());
    }

    @Test
    void orderDarkRoastWithMilkAndMocha() throws Exception {
        var order = new OrderData("Dark Roast", Arrays.asList("Milk", "Mocha"));
        var receipt = orderRepository.add(order);
        assertEquals("Dark roast, Milk, Mocha", receipt.description());
        assertEquals(2.69f, receipt.cost());
    }

    @Test
    void orderDecafWithSoy() throws Exception {
        var order = new OrderData("Decaf", Collections.singletonList("Soy"));
        var receipt = orderRepository.add(order);
        assertEquals("Decaf Coffee, Soy", receipt.description());
        assertEquals(1.55f, receipt.cost());
    }

    @Test
    void orderHouseBlendWithWhip() throws Exception {
        var order = new OrderData("HouseBlend", Collections.singletonList("Whip"));
        var receipt = orderRepository.add(order);
        assertEquals("House Blend Coffee, Whip", receipt.description());
        assertEquals(1.9f, receipt.cost());
    }

    @Test
    void invalidBeverageType() {
        var order = new OrderData("InvalidBeverage", Collections.emptyList());
        Exception exception = assertThrows(Exception.class, () -> orderRepository.add(order));
        assertTrue(exception.getMessage().contains("Beverage type 'InvalidBeverage' is not valid!"));
    }

    @Test
    void invalidCondimentType() {
        var order = new OrderData("Espresso", Collections.singletonList("InvalidCondiment"));
        Exception exception = assertThrows(Exception.class, () -> orderRepository.add(order));
        assertTrue(exception.getMessage().contains("Condiment type 'InvalidCondiment' is not valid"));
    }

    @Test
    void orderEspressoWithAllCondiments() throws Exception {
        var order = new OrderData("Espresso", Arrays.asList("Milk", "Mocha", "Whip", "Soy"));
        var receipt = orderRepository.add(order);
        assertEquals("Espresso, Milk, Mocha, Whip, Soy", receipt.description());
        assertEquals(2.56f, receipt.cost());
    }

    @Test
    void orderMultipleCondimentsSameType() throws Exception {
        var order = new OrderData("Espresso", Arrays.asList("Milk", "Milk"));
        var receipt = orderRepository.add(order);
        assertEquals("Espresso, Milk, Milk", receipt.description());
        assertEquals(2.14f, receipt.cost());
    }

    @Test
    void orderDecafNoCondiments() throws Exception {
        var order = new OrderData("Decaf", Collections.emptyList());
        var receipt = orderRepository.add(order);
        assertEquals("Decaf Coffee", receipt.description());
        assertEquals(1.28f, receipt.cost());
    }

    @Test
    void orderHouseBlendWithMochaAndWhip() throws Exception {
        var order = new OrderData("HouseBlend", Arrays.asList("Mocha", "Whip"));
        var receipt = orderRepository.add(order);
        assertEquals("House Blend Coffee, Mocha, Whip", receipt.description());
        assertEquals(2.2f, receipt.cost());
    }

    @Test
    void orderDarkRoastWithSoyAndMilk() throws Exception {
        var order = new OrderData("Dark Roast", Arrays.asList("Soy", "Milk"));
        var receipt = orderRepository.add(order);
        assertEquals("Dark roast, Soy, Milk", receipt.description());
        assertEquals(2.66f, receipt.cost());
    }

    @Test
    void orderEspressoWithWhip() throws Exception {
        var order = new OrderData("Espresso", Collections.singletonList("Whip"));
        var receipt = orderRepository.add(order);
        assertEquals("Espresso, Whip", receipt.description());
        assertEquals(1.59f, receipt.cost());
    }

    @Test
    void orderHouseBlendNoCondiments() throws Exception {
        var order = new OrderData("HouseBlend", Collections.emptyList());
        var receipt = orderRepository.add(order);
        assertEquals("House Blend Coffee", receipt.description());
        assertEquals(1.65f, receipt.cost());
    }

    @Test
    void orderDarkRoastNoCondiments() throws Exception {
        var order = new OrderData("Dark Roast", Collections.emptyList());
        var receipt = orderRepository.add(order);
        assertEquals("Dark roast", receipt.description());
        assertEquals(1.99f, receipt.cost());
    }

    @Test
    void orderEspressoWithMilkAndSoy() throws Exception {
        var order = new OrderData("Espresso", Arrays.asList("Milk", "Soy"));
        var receipt = orderRepository.add(order);
        assertEquals("Espresso, Milk, Soy", receipt.description());
        assertEquals(2.01f, receipt.cost());
    }
}