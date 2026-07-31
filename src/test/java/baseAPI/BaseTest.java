package baseAPI;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.AuthUtil;
import utils.ConfigReader;

/**
 * Base test for API tests. Sets RestAssured baseURI and a shared RequestSpecification.
 */
public class BaseTest {

    protected static RequestSpecification requestSpec;

    @BeforeClass
    public void setUp() {
        // load config
        ConfigReader.init();

        String baseUri = ConfigReader.get("base.uri");
        if (baseUri != null && !baseUri.isEmpty()) {
            RestAssured.baseURI = baseUri;
        }

        requestSpec = RestAssured.given().contentType("application/json");

        // attach auth token if available
        String token = AuthUtil.getAuthToken();
        if (token != null && !token.isEmpty()) {
            requestSpec.auth().oauth2(token);
        }
    }

    @AfterClass
    public void tearDown() {
        // placeholder for any cleanup
    }
}

