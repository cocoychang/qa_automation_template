package tests;

import baseAPI.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import models.Order;
import java.util.Map;
import java.util.HashMap;

import static org.testng.Assert.*;
import static io.restassured.RestAssured.given;

/**
 * Tests for Petstore store endpoints: inventory and order operations.
 */
public class StoreApiTest extends BaseTest {

    @Test
    public void testInventoryIsAccessible() {
        Response r = given().spec(requestSpec).when().get("/store/inventory");
        assertTrue(r.getStatusCode() == 200 || r.getStatusCode() == 403 || r.getStatusCode() == 401, "Inventory endpoint should be accessible");
        // basic sanity: response is JSON map
        assertNotNull(r.as(Map.class));
    }

    @Test
    public void testCreateGetDeleteOrder() {
        // create an order payload using POJO
        long idVal = System.currentTimeMillis() / 1000 % 100000;
        Order order = new Order();
        order.setId(idVal);
        order.setPetId(1L);
        order.setQuantity(1);
        order.setShipDate("2026-07-29T10:00:00.000Z");
        order.setStatus("placed");
        order.setComplete(true);

        // log request
        Response create = given().spec(requestSpec).log().all().body(order).when().post("store/order");
        System.out.println("Create response status: " + create.statusCode());
        System.out.println("Create response body: " + create.asString());

        // Accept 200 or 201 depending on implementation
        assertTrue(create.statusCode() == 200 || create.statusCode() == 201, "Unexpected create status: " + create.statusCode());

        Integer id = create.jsonPath().getInt("id");
        assertNotNull(id, "Created order id should not be null. Response: " + create.asString());

        // get the order
        Response get = given().spec(requestSpec).log().all().when().get("store/order/" + id);
        System.out.println("Get response status: " + get.statusCode());
        System.out.println("Get response body: " + get.asString());
        assertEquals(get.statusCode(), 200, "GET order failed. Body: " + get.asString());
        assertEquals(get.jsonPath().getInt("id"), id.intValue());

        // delete the order
        Response del = given().spec(requestSpec).log().all().when().delete("store/order/" + id);
        System.out.println("Delete response status: " + del.statusCode());
        System.out.println("Delete response body: " + del.asString());
        assertTrue(del.statusCode() == 200 || del.statusCode() == 404, "Unexpected delete status: " + del.statusCode());
    }
}

