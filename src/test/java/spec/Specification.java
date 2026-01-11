package spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.STATUS;
import static tests.BaseTest.API_KEY;

public class Specification {

    public static RequestSpecification userRequestSpec = with()
            .filter(withCustomTemplates())
            .header("x-api-key", API_KEY)
            .contentType(ContentType.JSON)
            .filter(new RequestLoggingFilter())
            .filter(new ResponseLoggingFilter())
            .log().uri()
            .log().body()
            .log().headers();

    public static ResponseSpecification createResponseSpec(int expectedStatusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(expectedStatusCode)
                .log(STATUS)
                .build();
    }
}
