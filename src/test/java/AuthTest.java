import com.codeborne.selenide.Condition;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = DataGenerator.Registration.getUser("active");
        $( "[data-test-id=login] input").setValue(registeredUser.getLogin());
        $( "[data-test-id=password] input").setValue(registeredUser.getPassword());
        $(byClassName("button")).click();
        $(byText("Личный кабинет")).shouldBe(Condition.appear, Duration.ofSeconds(15));
    }
        // + добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        //  пользователя registeredUser

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = DataGenerator.Registration.getUser("active");
        $( "[data-test-id=login] input").setValue(DataGenerator.getRandomLogin());
        $( "[data-test-id=password] input").setValue(DataGenerator.getRandomPassword());
        $(byClassName("button")).click();
        $(byText("Ошибка")).shouldBe(Condition.appear, Duration.ofSeconds(15));

        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $( "[data-test-id=login] input").setValue(blockedUser.getLogin());
        $( "[data-test-id=password] input").setValue(blockedUser.getPassword());
        $(byClassName("button")).click();
        $(byText("Личный кабинет")).shouldBe(Condition.appear, Duration.ofSeconds(15));

        // + добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongLogin = DataGenerator.getRandomLogin();
        $( "[data-test-id=login] input").setValue(wrongLogin);
        $( "[data-test-id=password] input").setValue(registeredUser.getPassword());
        $(byClassName("button")).click();
        $(byText("Ошибка")).shouldBe(Condition.appear, Duration.ofSeconds(15));
        // + добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongPassword = DataGenerator.getRandomPassword();
        $( "[data-test-id=login] input").setValue(registeredUser.getLogin());
        $( "[data-test-id=password] input").setValue(wrongPassword);
        $(byClassName("button")).click();
        $(byText("Ошибка")).shouldBe(Condition.appear, Duration.ofSeconds(15));

        // + добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        //  "Пароль" - переменную wrongPassword
    }
}
