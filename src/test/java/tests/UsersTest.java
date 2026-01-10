package tests;

import models.UserListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static spec.Specification.createResponseSpec;
import static spec.Specification.userRequestSpec;

public class UsersTest extends BaseTest {

    @Test
    @DisplayName("Проверка списка пользователей по умолчанию")
    public void defaultUserListTest() {
        UserListResponse response = step("Make request", () ->
                given(userRequestSpec)
                        .when()
                        .get("/users")
                        .then()
                        .spec(createResponseSpec(200))
                        .extract().as(UserListResponse.class));

        step("Check response", () -> {
            assertEquals(1, response.getPage());
            assertEquals(2, response.getTotal_pages());
            assertEquals(6, response.getData().size());
        });
    }

    @Test
    @DisplayName("Получение второй страницы списка пользователей")
    public void gettingTheSecondPageOfTheUserListTest() {

        UserListResponse response = step("Make request", () ->
                given(userRequestSpec)
                        .queryParam("page", "2")
                        .when()
                        .get("/users")
                        .then()
                        .spec(createResponseSpec(200))
                        .extract().as(UserListResponse.class));

        step("Check response", () -> {
            assertEquals(2, response.getPage());
            assertEquals(2, response.getTotal_pages());
            assertEquals(6, response.getData().size());
        });
    }

    @Test
    @DisplayName("Запрос 12 пользователей на одной странице")
    public void twentyUserRequestsOnOnePageTest() {
        UserListResponse response = step("Make request", () ->
                given(userRequestSpec)
                        .queryParam("per_page", "12")
                        .when()
                        .get("/users")
                        .then()
                        .spec(createResponseSpec(200))
                        .extract().as(UserListResponse.class));

        step("Check response", () -> {
            assertEquals(1, response.getPage());
            assertEquals(1, response.getTotal_pages());
            assertEquals(12, response.getData().size());
        });
    }

    @Test
    @DisplayName("Запрос несуществующей страницы")
    public void nonExistentPageTest() {
        UserListResponse response = step("Make request", () ->
                given(userRequestSpec)
                        .queryParam("page", "10")
                        .when()
                        .get("/users")
                        .then()
                        .spec(createResponseSpec(200))
                        .extract().as(UserListResponse.class));

        step("Check response", () -> {
            assertEquals(10, response.getPage());
            assertEquals(2, response.getTotal_pages());
            assertEquals(0, response.getData().size());
        });
    }

    @Test
    @DisplayName("Запрос несуществующего пользователя")
    public void nonExistentUserTest() {
        step("Make request", () ->
                given(userRequestSpec)
                        .when()
                        .get("/users/999")
                        .then()
                        .spec(createResponseSpec(404)));
    }

    @Test
    @DisplayName("Проверка удаления пользователя по id")
    public void deleteUsersIdTest() {
        step("Make request", () ->
                given(userRequestSpec)
                        .when()
                        .delete("/users/6")
                        .then()
                        .spec(createResponseSpec(204)));
    }
}
