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
    
    private final SelenideElement assigneesSection = $x("//div[@data-testid='sidebar-section']//h3[text()='Assignees']");
    private final SelenideElement labelsSection = $x("//div[@data-testid='sidebar-section']//h3[text()='Labels']");
    
    private final SelenideElement editAssigneesButton = $x("//div[@data-testid='sidebar-section']//h3[text()='Assignees']/..//button[contains(@aria-describedby, 'loading-announcement')]");
    private final SelenideElement editLabelsButton = $x("//div[@data-testid='sidebar-section']//h3[text()='Labels']/..//button[contains(@aria-describedby, 'loading-announcement')]");
    
    private final SelenideElement issueLabelsContainer = $("[data-testid='issue-labels']");
    private final SelenideElement assigneesContainer = $x("//div[@data-testid='sidebar-section']//h3[text()='Assignees']/..//ul");
    
    private final SelenideElement labelSearchField = $("input[placeholder*='filter' i], input[placeholder*='search' i]");
    private final SelenideElement assigneeSearchField = $("input[placeholder*='search' i], input[placeholder*='type' i]");
    
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
     * Ищет метку по названию
     * @param labelName название метки для поиска
     */
    public void searchLabel(String labelName) {
        log.info("Поиск метки: {}", labelName);
        clickLabels();
        
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
     * Выбирает исполнителя для issue
     * @param username имя пользователя для назначения
     */
    public void selectAssignee(String username) {
        log.info("Выбор исполнителя: {}", username);
        clickAssignees();
        
        try {
            SelenideElement userOption = $x("//div[contains(text(), '" + username + "')]");
            if (userOption.exists()) {
                userOption.click();
                log.info("Исполнитель {} выбран", username);
            } else {
                log.warn("Не удалось найти исполнителя: {}", username);
            }
        } catch (Exception e) {
            log.error("Ошибка при выборе исполнителя: {}", e.getMessage());
        }
    }
    
    /**
     * Проверяет, что пользователь назначен исполнителем
     * @param username имя пользователя
     * @return true, если пользователь назначен
     */
    public boolean isAssigneeSelected(String username) {
        try {
            return assigneesContainer.exists() && 
                   $x("//div[@data-testid='sidebar-section']//h3[text()='Assignees']/..//a[contains(@href, '" + username + "')]").exists();
        } catch (Exception e) {
            log.error("Ошибка при проверке назначения: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Выбирает метку для issue
     * @param labelName название метки
     */
    public void selectLabel(String labelName) {
        log.info("Выбор метки: {}", labelName);
        clickLabels();
        
        try {
            SelenideElement labelOption = $x("//div[contains(text(), '" + labelName + "')]");
            if (labelOption.exists()) {
                labelOption.click();
                log.info("Метка {} выбрана", labelName);
            } else {
                log.warn("Не удалось найти метку: {}", labelName);
            }
        } catch (Exception e) {
            log.error("Ошибка при выборе метки: {}", e.getMessage());
        }
    }
    
    /**
     * Проверяет, что метка выбрана
     * @param labelName название метки
     * @return true, если метка выбрана
     */
    public boolean isLabelSelected(String labelName) {
        try {
            return issueLabelsContainer.exists() && 
                   $x("//div[@data-testid='issue-labels']//span[contains(text(), '" + labelName + "')]").exists();
        } catch (Exception e) {
            log.error("Ошибка при проверке метки: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Назначает текущего пользователя исполнителем
     */
    public void assignYourself() {
        log.info("Назначение себя исполнителем");
        clickAssignees();
        
        try {
            SelenideElement assignYourselfOption = $x("//div[contains(text(), 'Assign yourself')]");
            if (assignYourselfOption.exists()) {
                assignYourselfOption.click();
                log.info("Пользователь назначен исполнителем");
            } else {
                log.warn("Опция 'Assign yourself' не найдена");
            }
        } catch (Exception e) {
            log.error("Ошибка при назначении себя исполнителем: {}", e.getMessage());
        }
    }
    
    /**
     * Проверяет, что текущий пользователь назначен исполнителем
     * @return true, если текущий пользователь назначен
     */
    public boolean isCurrentUserAssigned() {
        try {
            return assigneesContainer.exists() && 
                   $x("//div[@data-testid='sidebar-section']//h3[text()='Assignees']/..//img[contains(@alt, '@')]").exists();
        } catch (Exception e) {
            log.error("Ошибка при проверке назначения текущего пользователя: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Получает список всех назначенных исполнителей
     * @return список имен исполнителей
     */
    public java.util.List<String> getAssignees() {
        try {
            ElementsCollection assigneeElements = $$x("//div[@data-testid='sidebar-section']//h3[text()='Assignees']/..//a");
            return assigneeElements.texts();
        } catch (Exception e) {
            log.error("Ошибка при получении списка исполнителей: {}", e.getMessage());
            return java.util.Collections.emptyList();
        }
    }
    
    /**
     * Получает список всех меток
     * @return список названий меток
     */
    public java.util.List<String> getLabels() {
        try {
            ElementsCollection labelElements = $$x("//div[@data-testid='issue-labels']//span");
            return labelElements.texts();
        } catch (Exception e) {
            log.error("Ошибка при получении списка меток: {}", e.getMessage());
            return java.util.Collections.emptyList();
        }
    }
}
