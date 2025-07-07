package ru.github.pages;

import ru.github.components.elements.ButtonElement;
import ru.github.components.elements.InputElement;
import ru.github.components.elements.TextElement;
import ru.github.components.elements.TextareaElement;


/**
 * Страница создания новой issue
 */
public class NewIssuePage extends BasePage {
    private static final String TITLE_EMPTY_ERROR = "Title can not be empty";
    
    private final InputElement titleField = InputElement.byAriaLabel("Add a title");
    private final TextareaElement descriptionField = TextareaElement.byPlaceholder("Type your description here…");
    private final ButtonElement submitButton = ButtonElement.byTestId("create-issue-button");
    private final TextElement titleError = TextElement.byId("title-validation");
    
    /**
     * Конструктор страницы создания новой issue
     */
    public NewIssuePage() {
        super();
        waitForPageLoad();
    }
    
    /**
     * Заполняет поле заголовка
     * @param title заголовок issue
     * @return текущая страница
     */
    public NewIssuePage fillTitle(String title) {
        fillField(titleField, title, "заголовок issue");
        return this;
    }
    
    /**
     * Заполняет поле описания
     * @param description описание issue
     * @return текущая страница
     */
    public NewIssuePage fillDescription(String description) {
        fillTextarea(descriptionField, description, "описание issue");
        return this;
    }
    
    /**
     * Нажимает кнопку создания issue
     * @return страница деталей созданной issue
     */
    public IssueDetailsPage clickCreate() {
        log.info("Нажатие кнопки 'Create'");
        submitButton.click();
        return new IssueDetailsPage();
    }

    /**
     * Пытается создать issue с пустым заголовком
     * @return текущая страница с ошибкой
     */
    public NewIssuePage clickCreateWithoutTitle() {
        log.info("Попытка создания issue с пустым заголовком");
        submitButton.click();
        return this;
    }
    
    /**
     * Проверяет, что отображается ошибка о пустом заголовке
     * @return true, если ошибка отображается
     */
    public boolean isEmptyTitleErrorDisplayed() {
        return hasErrorWithText(titleError, TITLE_EMPTY_ERROR);
    }
    
    @Override
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки страницы создания новой issue");
        waitForElementsVisible(titleField, descriptionField, submitButton);
    }
}
