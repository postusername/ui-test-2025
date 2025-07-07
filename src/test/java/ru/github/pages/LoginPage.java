package ru.github.pages;

import ru.github.utils.ConfigReader;
import ru.github.components.elements.InputElement;
import ru.github.components.elements.ButtonElement;

import static com.codeborne.selenide.Selenide.open;

/**
 * Страница авторизации GitHub
 */
public class LoginPage extends BasePage {
    
    private static final String LOGIN_URL = ConfigReader.getProperty("github.login.url");
    
    private final InputElement usernameField = InputElement.byId("login_field");
    private final InputElement passwordField = InputElement.byId("password");
    private final ButtonElement signInButton = ButtonElement.byCssSelector("input[type='submit'][value='Sign in']");
    
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
        fillField(usernameField, username, "имя пользователя");
        return this;
    }
    
    /**
     * Заполняет поле пароля
     * @param password пароль
     * @return текущая страница авторизации
     */
    public LoginPage fillPassword(String password) {
        fillField(passwordField, password, "пароль");
        return this;
    }
    
    /**
     * Нажимает кнопку входа
     * @return главная страница GitHub
     */
    public MainPage clickSignIn() {
        log.info("Нажатие кнопки 'Sign in'");
        signInButton.click();
        return new MainPage();
    }
    
    @Override
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки страницы авторизации");
        waitForElementsVisible(usernameField, passwordField, signInButton);
    }
    
    private void navigateToLoginPage() {
        if (!getCurrentUrl().contains("/login")) {
            log.info("Переход на страницу авторизации");
            open(LOGIN_URL);
        }
    }
}
