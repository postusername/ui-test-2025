package ru.github.components;

import ru.github.components.elements.ButtonElement;
import ru.github.components.elements.InputElement;
import ru.github.components.elements.TextElement;

/**
 * Компонент боковой панели управления issue (Assignees, Labels)
 */
public class IssueActionsComponent extends BaseComponent {
    
    private final TextElement labelsSection = TextElement.byXpath("//div[@data-testid='sidebar-section']//h3[text()='Labels']");
    private final ButtonElement editLabelsButton = ButtonElement.byXpath("//div[@data-testid='sidebar-section']//h3[text()='Labels']/..//button[contains(@aria-describedby, 'loading-announcement')]");
    private final TextElement issueLabelsContainer = TextElement.byCssSelector("[data-testid='issue-labels']");
    private final InputElement labelSearchField = InputElement.byCssSelector("input[placeholder*='filter' i], input[placeholder*='search' i]");
    private final TextElement searchResults = TextElement.byCssSelector(".ActionList, .prc-ActionList-ActionList-X4RiC");
    private final TextElement noResultsMessage = TextElement.byXpath("//div[contains(text(), 'No results') or contains(text(), 'not found')]");

    private final TextElement assigneesSection = TextElement.byXpath("//div[@data-testid='sidebar-section']//h3[text()='Assignees']");
    private final TextElement assigneesContainer = TextElement.byTestId("issue-assignees");
    private final ButtonElement assignYourselfOption = ButtonElement.byXpath("//button[.//*[text()='Assign yourself']]");

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
     * Кликает по кнопке редактирования меток
     */
    public void clickLabels() {
        log.info("Клик по кнопке редактирования меток");
        try {
            if (editLabelsButton.exists()) {
                editLabelsButton.click();
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
                labelSearchField.setValue(labelName);
            } else {
                log.warn("Поле поиска меток не найдено");
            }
        } catch (Exception e) {
            log.error("Ошибка при поиске метки: {}", e.getMessage());
        }
    }
    
    /**
     * Проверяет, что предложение создать новую метку отображается
     * @param labelName название метки
     * @return true, если предложение создать метку отображается
     */
    public boolean isCreateLabelSuggestionVisible(String labelName) {
        try {
            TextElement createLabelDiv = TextElement.byXpath("//div[contains(text(), 'Create') and contains(text(), '" + labelName + "')]");
            ButtonElement createLabelButton = ButtonElement.byXpath("//button[contains(text(), 'Create') and contains(text(), '" + labelName + "')]");
            TextElement createLabelSpan = TextElement.byXpath("//span[contains(text(), 'Create') and contains(text(), '" + labelName + "')]");
            
            return createLabelDiv.exists() || createLabelButton.exists() || createLabelSpan.exists();
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
     * Выбирает метку для issue
     * @param labelName название метки
     */
    public void selectLabel(String labelName) {
        log.info("Выбор метки: {}", labelName);
        clickLabels();

        try {
            ButtonElement labelOption = ButtonElement.byXpath("//span[@data-component='ActionList.Item--DividerContainer'][contains(., '" + labelName + "')]");
            labelOption.waitForVisible();
            if (labelOption.exists()) {
                labelOption.click();
                log.info("Метка {} выбрана", labelName);
            } else {
                log.warn("Не удалось найти метку: {}", labelName);
            }
        } catch (Exception e) {
            log.error("Ошибка при выборе метки: {}", e.getMessage());
        }

        clickLabels();
        issueLabelsContainer.waitForVisible();
    }
    
    /**
     * Проверяет, что метка выбрана
     * @param labelName название метки
     * @return true, если метка выбрана
     */
    public boolean isLabelSelected(String labelName) {
        try {
            TextElement labelElement = TextElement.byXpath("//div[@data-testid='issue-labels']//span[contains(text(), '" + labelName + "')]");
            return issueLabelsContainer.exists() && labelElement.exists();
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
        try {
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
        assigneesContainer.waitForVisible();
        try {
            return assigneesContainer.exists();
        } catch (Exception e) {
            log.error("Ошибка при проверке назначения текущего пользователя: {}", e.getMessage());
            return false;
        }
    }
}
