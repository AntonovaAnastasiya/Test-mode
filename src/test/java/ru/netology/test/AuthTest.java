package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationInfo;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen=true;
    }

    @Test
    void shouldSuccessfullySendForm() {
        RegistrationInfo validUser = DataGenerator.Registration.getUser("active");
        $("[data-test-id=login] input").setValue(validUser.getLogin());
        $("[data-test-id=password] input").setValue(validUser.getPassword());
        $("button[data-test-id=action-login]").click();
        $(withText("Личный кабинет")).shouldBe(appear);

    }

    @Test
    void shouldGetErrorWithBlockedUser(){
        RegistrationInfo blockedUser = DataGenerator.Registration.getUser("blocked");
        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $("button[data-test-id=action-login]").click();
        $(withText("Пользователь заблокирован")).shouldBe(appear);
    }

    @Test
    void noValidPassword(){
        RegistrationInfo wrongPassword = DataGenerator.Registration.shouldGetInvalidPassword();
        $("[data-test-id='login'] input").setValue(wrongPassword.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword.getPassword());
        $(withText("Продолжить")).click();
        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    void noValidLogin(){
        RegistrationInfo wrongLogin = DataGenerator.Registration.shouldGetInvalidLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin.getLogin());
        $("[data-test-id='password'] input").setValue(wrongLogin.getPassword());
        $(withText("Продолжить")).click();
        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
}
