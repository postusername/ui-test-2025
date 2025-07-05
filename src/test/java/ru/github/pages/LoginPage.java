package ru.github.pages;

import com.codeborne.selenide.SelenideElement;
import ru.github.base.BasePage;
import ru.github.utils.ConfigReader;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * Страница авторизации GitHub
 */
public class LoginPage extends BasePage {
    
    private static final String SIGN_IN_BUTTON_TEXT = "Sign in";
    private static final String LOGIN_URL = ConfigReader.getProperty("github.login.url");
    
    private final SelenideElement usernameField = $("#login_field");
    private final SelenideElement passwordField = $("#password");
    private final SelenideElement signInButton = $("input[type='submit'][value='Sign in']");
    
    /**
     * Конструктор страницы авторизации
     */
    public LoginPage() {
        super();
        navigateToLoginPage();
        waitForPageLoad();
    }
    
    /**
     * Заполняет поле имени пользователя
     * @param username имя пользователя
     * @return текущая страница авторизации
     */
    public LoginPage fillUsername(String username) {
        log.info("Заполнение поля имени пользователя: {}", username);
        usernameField.shouldBe(visible).setValue(username);
        return this;
    }
    
    /**
     * Заполняет поле пароля
     * @param password пароль
     * @return текущая страница авторизации
     */
    public LoginPage fillPassword(String password) {
        log.info("Заполнение поля пароля");
        passwordField.shouldBe(visible).setValue(password);
        return this;
    }
    
    /**
     * Нажимает кнопку входа
     * @return главная страница GitHub
     */
    public MainPage clickSignIn() {
        log.info("Нажатие кнопки 'Sign in'");
        signInButton.shouldBe(visible).click();
        return new MainPage();
    }
    
    @Override
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки страницы авторизации");
        usernameField.shouldBe(visible);
        passwordField.shouldBe(visible);
        signInButton.shouldBe(visible);
    }
    
    private void navigateToLoginPage() {
        if (!getCurrentUrl().contains("/login")) {
            log.info("Переход на страницу авторизации");
            open(LOGIN_URL);
        }
    }
}
