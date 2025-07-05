package ru.github.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.github.pages.LoginPage;
import ru.github.pages.MainPage;
import ru.github.utils.ConfigReader;

/**
 * Сервис для авторизации пользователей
 */
public class AuthService {
    
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private MainPage mainPage;
    
    /**
     * Авторизует пользователя в системе
     * @param username логин пользователя
     * @param password пароль пользователя
     * @return главная страница после авторизации
     */
    public MainPage auth(String username, String password) {
        log.info("Начало авторизации пользователя: {}", username);
        
        LoginPage loginPage = new LoginPage();
        mainPage = loginPage.fillUsername(username)
                           .fillPassword(password)
                           .clickSignIn();
        return mainPage;
    }
    
    /**
     * Авторизует основного тестового пользователя
     * @return главная страница после авторизации
     */
    public MainPage auth() {
        return authPrimaryUser();
    }
    
    /**
     * Авторизует основного тестового пользователя
     * @return главная страница после авторизации
     */
    public MainPage authPrimaryUser() {
        String username = ConfigReader.getTestData("user.primary.login");
        String password = ConfigReader.getTestData("user.primary.password");
        
        return auth(username, password);
    }
    
    /**
     * Авторизует дополнительного тестового пользователя
     * @return главная страница после авторизации
     */
    public MainPage authSecondaryUser() {
        String username = ConfigReader.getTestData("user.secondary.login");
        String password = ConfigReader.getTestData("user.secondary.password");
        
        return auth(username, password);
    }
    
    /**
     * Возвращает текущую главную страницу
     * @return главная страница
     */
    public MainPage getMainPage() {
        if (mainPage == null) {
            mainPage = new MainPage();
        }
        return mainPage;
    }
}
