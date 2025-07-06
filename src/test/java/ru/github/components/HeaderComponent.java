package ru.github.components;

import ru.github.components.BaseComponent;
import ru.github.components.elements.ButtonElement;
import ru.github.components.elements.ImageElement;
import ru.github.components.elements.InputElement;
import ru.github.components.elements.TextElement;

import static com.codeborne.selenide.Selenide.*;

/**
 * Компонент заголовка страницы с навигацией
 */
public class HeaderComponent extends BaseComponent {
    
    private final ButtonElement userMenu = ButtonElement.byXpath("//button[@aria-label='View profile and more'] | //button[contains(@aria-label, 'profile')] | //summary[contains(@aria-label, 'menu')]");
    private final ImageElement userAvatar = ImageElement.byCssSelector("img[alt*='@'][data-testid='github-avatar']");
    private final ButtonElement signOutButton = ButtonElement.byXpath("//a[contains(text(), 'Sign out')] | //button[contains(text(), 'Sign out')]");
    private final ButtonElement profileLink = ButtonElement.byXpath("//a[contains(text(), 'Your profile')] | //a[contains(@href, '/profile')]");
    private final InputElement searchField = InputElement.byName("q");
    private final ImageElement githubLogo = ImageElement.byCssSelector(".octicon-mark-github");
    
    private final ButtonElement dropdownCaret = ButtonElement.byCssSelector(".dropdown-caret");
    private final TextElement userMenuDropdown = TextElement.byXpath("//div[@role='menu'] | //div[contains(@class, 'dropdown-menu')]");

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
        searchField.setValue(searchQuery);
        searchField.pressEnter();
    }
    
    /**
     * Открывает меню пользователя
     */
    public void openUserMenu() {
        log.info("Открытие меню пользователя");
        
        if (userMenu.exists()) {
            userMenu.click();
        } else if (userAvatar.exists()) {
            userAvatar.click();
        } else if (dropdownCaret.exists()) {
            dropdownCaret.click();
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
            profileLink.click();
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
            signOutButton.click();
        } else {
            log.warn("Кнопка выхода не найдена в меню");
        }
    }
    
    /**
     * Переходит на главную страницу GitHub
     */
    public void clickGitHubLogo() {
        log.info("Переход на главную страницу GitHub");
        githubLogo.click();
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
