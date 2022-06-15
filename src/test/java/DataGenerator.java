import com.github.javafaker.Faker;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static final Faker faker = new Faker(new Locale("en"));
    private DataGenerator() {
    }


    private static void sendRequest(RegistrationDto user) {

        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK

        //+отправить запрос на указанный в требованиях path, передав в body запроса объект user
        // + и не забудьте передать подготовленную спецификацию requestSpec.
        //  Пример реализации метода показан в условии к задаче.
    }

    public static String getRandomLogin() {
        String login=faker.name().firstName();
        // + добавить логику для объявления переменной login и задания её значения, для генерации
        //  + случайного логина используйте faker
        return login;
    }

    public static String getRandomPassword() {
        String password = faker.regexify("[a-z1-9]{10}");
        // + добавить логику для объявления переменной password и задания её значения, для генерации
        // + случайного пароля используйте faker
        return password;
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto getUser(String status) {
            // + создать пользователя user используя методы getRandomLogin(), getRandomPassword() и параметр status

            RegistrationDto user = new RegistrationDto(getRandomLogin(), getRandomPassword(), status ) ;

            return user;
        }

        public static RegistrationDto getRegisteredUser(String status) {
            // + объявить переменную registeredUser и присвоить ей значение возвращённое getUser(status).
                      RegistrationDto registeredUser = getUser(status);

            // +Послать запрос на регистрацию пользователя с помощью вызова sendRequest(registeredUser)
            sendRequest(registeredUser);

            return registeredUser;
        }
    }
}

