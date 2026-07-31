package clients;

import io.restassured.response.Response;
import models.UserRequest;
import models.UserResponse;

import static io.restassured.RestAssured.given;

/**
 * Simple client wrapper providing reusable API calls for User resource.
 */
public class UserClient {

    public Response getUserResponse(int id) {
        return given().when().get("/users/" + id);
    }

    public Response createUserResponse(UserRequest request) {
        return given().body(request).when().post("/users");
    }

    public UserResponse getUserAsModel(int id) {
        Response r = getUserResponse(id);
        return r.as(UserResponse.class);
    }

    public UserResponse createUserAsModel(UserRequest request) {
        Response r = createUserResponse(request);
        return r.as(UserResponse.class);
    }
}

