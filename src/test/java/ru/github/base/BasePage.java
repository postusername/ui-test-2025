package ru.github.base;

import com.codeborne.selenide.WebDriverRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.title;
import static com.codeborne.selenide.WebDriverRunner.url;

/**
 * Базовый класс для всех страниц
 */
public abstract class BasePage {
    
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    
    /**
     * Конструктор базовой страницы
     */
    public BasePage() {
        log.info("Открыта страница: {}", this.getClass().getSimpleName());
    }
    
    /**
     * Получает текущий URL страницы
     * @return текущий URL
     */
    public String getCurrentUrl() {
        return url();
    }
    
    /**
     * Получает заголовок страницы
     * @return заголовок страницы
     */
    public String getPageTitle() {
        return title();
    }
    
    /**
     * Проверяет, что URL содержит указанную строку
     * @param expectedUrlPart ожидаемая часть URL
     * @return true, если URL содержит указанную строку
     */
    public boolean isUrlContains(String expectedUrlPart) {
        return getCurrentUrl().contains(expectedUrlPart);
    }
    
    /**
     * Ожидает загрузки страницы
     * Переопределяется в наследниках при необходимости
     */
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки страницы: {}", this.getClass().getSimpleName());
    }
}
