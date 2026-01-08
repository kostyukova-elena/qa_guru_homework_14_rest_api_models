package tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.STATUS;

public class BaseTest {
    protected static final String API_KEY = "reqres_91dd69b7db8846b7a789c60d185eb7db";

    @BeforeAll
    public static void setup() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("x-api-key", API_KEY)
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    public static RequestSpecification userRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .log().headers()
            .baseUri("https://reqres.in")
            .basePath("/api");

    public static ResponseSpecification userResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .build();

    public static ResponseSpecification nonExistentUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(STATUS)
            .build();

    public static ResponseSpecification deleteUsersIdResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(STATUS)
            .build();
}