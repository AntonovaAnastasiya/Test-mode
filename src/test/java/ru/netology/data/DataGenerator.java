package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import static io.restassured.RestAssured.given;


public class DataGenerator {

    private static Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @BeforeAll
    public static void sendRequest(RegistrationInfo user) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user)
        .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static class Registration {
        private Registration() {

        }
        public static String RandomLogin() {
            return faker.name().username();
        }

        public static String RandomPassword() {
            return faker.internet().password();
        }

        public static RegistrationInfo getUser(String status) {
            RegistrationInfo user = new RegistrationInfo(RandomLogin(), RandomPassword(), status);
            sendRequest(user);
            return user;
        }

        public static RegistrationInfo shouldGetInvalidLogin() {
            String password = RandomPassword();
            RegistrationInfo user = new RegistrationInfo(RandomLogin(), password, "active");
            sendRequest(user);
            return new RegistrationInfo(RandomLogin(), password, "active");
        }

        public static RegistrationInfo shouldGetInvalidPassword() {
            String login = RandomLogin();
            RegistrationInfo user = new RegistrationInfo(login, RandomPassword(), "active");
            sendRequest(user);
            return new RegistrationInfo(login, RandomPassword(), "active");
        }
    }
}
