package spec;

import io.restassured.builder.ResponseSpecBuilder;
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
            .log().uri()
            .log().body()
            .log().headers()
            ;

    public static ResponseSpecification createResponseSpec(int expectedStatusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(expectedStatusCode)
                .log(STATUS)
                .build();
    }
}
