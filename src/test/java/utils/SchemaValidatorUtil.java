package utils;

import io.restassured.response.Response;

import java.io.InputStream;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

/**
 * Utility to validate JSON response against a JSON Schema located under
 * src/test/resources/schemas/.
 */
public class SchemaValidatorUtil {

    public static void validate(Response response, String schemaFileName) {
        InputStream is = SchemaValidatorUtil.class.getClassLoader().getResourceAsStream("schemas/" + schemaFileName);
        if (is == null) {
            throw new IllegalArgumentException("Schema not found: " + schemaFileName);
        }

        response.then().assertThat().body(matchesJsonSchema(is));
    }
}

