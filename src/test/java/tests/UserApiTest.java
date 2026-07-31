package tests;

import baseAPI.BaseTest;
import clients.UserClient;
import models.UserRequest;
import models.UserResponse;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Simple sample test demonstrating create and get user using the client layer.
 */
public class UserApiTest extends BaseTest {

    private final UserClient userClient = new UserClient();

    @Test
    public void testCreateAndGetUser() {
        UserRequest req = new UserRequest("John Doe", "john@example.com");
        UserResponse created = userClient.createUserAsModel(req);

        assertNotNull(created, "Created user should not be null");
        assertEquals(created.getName(), "John Doe");

        UserResponse fetched = userClient.getUserAsModel(created.getId());
        assertNotNull(fetched, "Fetched user should not be null");
        assertEquals(fetched.getEmail(), "john@example.com");
    }
}

