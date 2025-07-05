package ru.github.components;

import com.codeborne.selenide.SelenideElement;
import ru.github.base.BaseComponent;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * Компонент заголовка страницы с навигацией
 */
public class HeaderComponent extends BaseComponent {
    
    private final SelenideElement userMenu = $x("//button[@aria-label='View profile and more'] | //button[contains(@aria-label, 'profile')] | //summary[contains(@aria-label, 'menu')]");
    private final SelenideElement userAvatar = $("img[alt*='@'][data-testid='github-avatar']");
    private final SelenideElement signOutButton = $x("//a[contains(text(), 'Sign out')] | //button[contains(text(), 'Sign out')]");
    private final SelenideElement profileLink = $x("//a[contains(text(), 'Your profile')] | //a[contains(@href, '/profile')]");
    private final SelenideElement searchField = $("input[name='q']");
    private final SelenideElement githubLogo = $(".octicon-mark-github");
    
    private final SelenideElement dropdownCaret = $(".dropdown-caret");
    private final SelenideElement userMenuDropdown = $x("//div[@role='menu'] | //div[contains(@class, 'dropdown-menu')]");
    
    /**
     * Конструктор компонента заголовка
     */
    public HeaderComponent() {
        log.info("Инициализация компонента заголовка");
    }
    
    /**
     * Выполняет поиск через глобальное поле поиска
     * @param searchQuery поисковый запрос
     */
    public void search(String searchQuery) {
        log.info("Поиск через заголовок: {}", searchQuery);
        searchField.shouldBe(visible).setValue(searchQuery).pressEnter();
    }
    
    /**
     * Открывает меню пользователя
     */
    public void openUserMenu() {
        log.info("Открытие меню пользователя");
        
        if (userMenu.exists()) {
            userMenu.shouldBe(visible).click();
        } else if (userAvatar.exists()) {
            userAvatar.shouldBe(visible).click();
        } else if (dropdownCaret.exists()) {
            dropdownCaret.shouldBe(visible).click();
        } else {
            log.warn("Меню пользователя не найдено. Возможно, пользователь не авторизован.");
        }
    }
    
    /**
     * Переходит в профиль пользователя
     */
    public void clickProfile() {
        log.info("Переход в профиль пользователя");
        openUserMenu();
        
        if (profileLink.exists()) {
            profileLink.shouldBe(visible).click();
        } else {
            log.warn("Ссылка на профиль не найдена в меню");
        }
    }
    
    /**
     * Выходит из системы
     */
    public void signOut() {
        log.info("Выход из системы");
        openUserMenu();
        
        if (signOutButton.exists()) {
            signOutButton.shouldBe(visible).click();
        } else {
            log.warn("Кнопка выхода не найдена в меню");
        }
    }
    
    /**
     * Переходит на главную страницу GitHub
     */
    public void clickGitHubLogo() {
        log.info("Переход на главную страницу GitHub");
        githubLogo.shouldBe(visible).click();
    }
    
    /**
     * Проверяет, что пользователь авторизован
     * @return true, если пользователь авторизован
     */
    public boolean isUserLoggedIn() {
        return userAvatar.exists() || userMenu.exists() || dropdownCaret.exists();
    }
    
    /**
     * Проверяет, что меню пользователя открыто
     * @return true, если меню открыто
     */
    public boolean isUserMenuOpen() {
        return userMenuDropdown.exists() && userMenuDropdown.isDisplayed();
    }
}
