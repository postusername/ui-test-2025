package ru.github.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.github.base.BaseComponent;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * Компонент боковой панели управления issue (Assignees, Labels)
 */
public class IssueActionsComponent extends BaseComponent {
    
    // Обновленные локаторы под реальную структуру GitHub
    private final SelenideElement assigneesSection = $x("//div[@data-testid='sidebar-section']//h3[text()='Assignees']");
    private final SelenideElement labelsSection = $x("//div[@data-testid='sidebar-section']//h3[text()='Labels']");
    
    // Правильные кнопки редактирования
    private final SelenideElement editAssigneesButton = $x("//div[@data-testid='sidebar-section']//h3[text()='Assignees']/..//button[contains(@aria-describedby, 'loading-announcement')]");
    private final SelenideElement editLabelsButton = $x("//div[@data-testid='sidebar-section']//h3[text()='Labels']/..//button[contains(@aria-describedby, 'loading-announcement')]");
    
    // Элементы для отображения данных
    private final SelenideElement issueLabelsContainer = $("[data-testid='issue-labels']");
    private final SelenideElement assigneesContainer = $x("//div[@data-testid='sidebar-section']//h3[text()='Assignees']/..//ul");
    
    // Поля для поиска (появляются после клика)
    private final SelenideElement labelSearchField = $("input[placeholder*='filter' i], input[placeholder*='search' i]");
    private final SelenideElement assigneeSearchField = $("input[placeholder*='search' i], input[placeholder*='type' i]");
    
    // Элементы результатов поиска
    private final SelenideElement searchResults = $(".ActionList, .prc-ActionList-ActionList-X4RiC");
    private final SelenideElement noResultsMessage = $x("//div[contains(text(), 'No results') or contains(text(), 'not found')]");
    
    /**
     * Конструктор компонента действий с issue
     */
    public IssueActionsComponent() {
        log.info("Инициализация компонента действий с issue");
    }
    
    /**
     * Проверяет, что секция назначений отображается
     * @return true, если секция назначений доступна
     */
    public boolean isAssigneesVisible() {
        return assigneesSection.exists();
    }
    
    /**
     * Проверяет, что секция меток отображается
     * @return true, если секция меток доступна
     */
    public boolean isLabelsVisible() {
        return labelsSection.exists();
    }
    
    /**
     * Кликает по кнопке редактирования назначений
     */
    public void clickAssignees() {
        log.info("Клик по кнопке редактирования назначений");
        try {
            if (editAssigneesButton.exists()) {
                editAssigneesButton.shouldBe(visible).click();
            } else {
                log.warn("Кнопка редактирования назначений не найдена");
            }
        } catch (Exception e) {
            log.error("Ошибка при клике по кнопке назначений: {}", e.getMessage());
        }
    }
    
    /**
     * Кликает по кнопке редактирования меток
     */
    public void clickLabels() {
        log.info("Клик по кнопке редактирования меток");
        try {
            if (editLabelsButton.exists()) {
                editLabelsButton.shouldBe(visible).click();
            } else {
                log.warn("Кнопка редактирования меток не найдена");
            }
        } catch (Exception e) {
            log.error("Ошибка при клике по кнопке меток: {}", e.getMessage());
        }
    }
    
    /**
     * Выбирает исполнителя по имени пользователя
     * @param username имя пользователя
     */
    public void selectAssignee(String username) {
        log.info("Выбор исполнителя: {}", username);
        clickAssignees();
        
        // Обновленный локатор для React версии
        SelenideElement assigneeCheckbox = $x("//input[@type='checkbox' and contains(@data-value, '" + username + "')]");
        if (!assigneeCheckbox.exists()) {
            // Альтернативный способ поиска
            assigneeCheckbox = $x("//label[contains(text(), '" + username + "')]//input[@type='checkbox']");
        }
        if (assigneeCheckbox.exists()) {
            assigneeCheckbox.shouldBe(visible).click();
        } else {
            log.warn("Не удалось найти исполнителя: {}", username);
        }
    }
    
    /**
     * Выбирает метку по названию
     * @param labelName название метки
     */
    public void selectLabel(String labelName) {
        log.info("Выбор метки: {}", labelName);
        clickLabels();
        
        // Обновленный локатор для React версии
        SelenideElement labelCheckbox = $x("//input[@type='checkbox' and contains(@data-value, '" + labelName + "')]");
        if (!labelCheckbox.exists()) {
            // Альтернативный способ поиска
            labelCheckbox = $x("//label[contains(text(), '" + labelName + "')]//input[@type='checkbox']");
        }
        labelCheckbox.shouldBe(visible).click();
    }
    
    /**
     * Ищет метку по названию
     * @param labelName название метки для поиска
     */
    public void searchLabel(String labelName) {
        log.info("Поиск метки: {}", labelName);
        clickLabels();
        
        // Ждем появления поля поиска
        try {
            if (labelSearchField.exists()) {
                labelSearchField.shouldBe(visible).setValue(labelName);
            } else {
                log.warn("Поле поиска меток не найдено");
            }
        } catch (Exception e) {
            log.error("Ошибка при поиске метки: {}", e.getMessage());
        }
    }
    
    /**
     * Проверяет, что указанная метка найдена
     * @param labelName название метки
     * @return true, если метка найдена
     */
    public boolean isLabelFound(String labelName) {
        try {
            return $x("//div[contains(text(), '" + labelName + "')]").exists();
        } catch (Exception e) {
            log.error("Ошибка при проверке метки: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Проверяет, что предложение создать новую метку отображается
     * @param labelName название метки
     * @return true, если предложение создать метку отображается
     */
    public boolean isCreateLabelSuggestionVisible(String labelName) {
        try {
            // Ищем различные варианты предложений создать метку
            return $x("//div[contains(text(), 'Create') and contains(text(), '" + labelName + "')]").exists() ||
                   $x("//button[contains(text(), 'Create') and contains(text(), '" + labelName + "')]").exists() ||
                   $x("//span[contains(text(), 'Create') and contains(text(), '" + labelName + "')]").exists();
        } catch (Exception e) {
            log.error("Ошибка при проверке предложения создать метку: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Проверяет отсутствие результатов поиска
     * @return true, если результатов нет
     */
    public boolean hasNoSearchResults() {
        try {
            return noResultsMessage.exists() || !searchResults.exists();
        } catch (Exception e) {
            log.error("Ошибка при проверке результатов поиска: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Получает список назначенных исполнителей
     * @return список имен исполнителей
     */
    public java.util.List<String> getAssignees() {
        ElementsCollection assignees = assigneesSection.$$("[data-testid='assignee-item']");
        if (assignees.isEmpty()) {
            // Альтернативный способ для React версии
            assignees = assigneesSection.$$(".assignee-item");
        }
        return assignees.texts();
    }
    
    /**
     * Получает список назначенных меток
     * @return список названий меток
     */
    public java.util.List<String> getLabels() {
        ElementsCollection labels = labelsSection.$$("[data-testid='label-item']");
        if (labels.isEmpty()) {
            // Альтернативный способ для React версии
            labels = labelsSection.$$(".label-item");
        }
        return labels.texts();
    }
    
    /**
     * Проверяет, что указанный пользователь назначен исполнителем
     * @param username имя пользователя
     * @return true, если пользователь назначен
     */
    public boolean isAssigneeSelected(String username) {
        return getAssignees().contains(username);
    }
    
    /**
     * Проверяет, что указанная метка назначена
     * @param labelName название метки
     * @return true, если метка назначена
     */
    public boolean isLabelSelected(String labelName) {
        return getLabels().contains(labelName);
    }
}
