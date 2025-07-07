package ru.github.components;

import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.*;

/**
 * Базовый класс для всех UI компонентов
 */
public abstract class BaseComponent {
    
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Проверяет, что компонент видим
     * @param element элемент для проверки
     * @return true, если элемент видим
     */
    protected boolean isVisible(SelenideElement element) {
        return element.is(visible);
    }
    
    /**
     * Ожидает появления элемента
     * @param element элемент для ожидания
     */
    protected void waitForVisible(SelenideElement element) {
        element.shouldBe(visible);
    }
}
