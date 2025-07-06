package ru.github.pages;

import ru.github.components.elements.ButtonElement;
import ru.github.components.elements.InputElement;
import ru.github.components.elements.TextElement;
import ru.github.components.elements.TextareaElement;

import static com.codeborne.selenide.Selenide.*;

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
     * Очищает поле заголовка
     * @return текущая страница
     */
    public NewIssuePage clearTitle() {
        clearField(titleField, "заголовок issue");
        return this;
    }
    
    /**
     * Нажимает кнопку создания issue
     * @return страница деталей созданной issue
     */
    public IssueDetailsPage clickSubmit() {
        log.info("Нажатие кнопки 'Create'");
        submitButton.click();
        return new IssueDetailsPage();
    }
    
    /**
     * Нажимает кнопку создания issue (альтернативное название для совместимости)
     * @return страница деталей созданной issue
     */
    public IssueDetailsPage clickCreate() {
        return clickSubmit();
    }
    
    /**
     * Пытается создать issue с пустым заголовком
     * @return текущая страница с ошибкой
     */
    public NewIssuePage clickSubmitWithEmptyTitle() {
        log.info("Попытка создания issue с пустым заголовком");
        submitButton.click();
        return this;
    }
    
    /**
     * Пытается создать issue без заголовка (альтернативное название для совместимости)
     * @return текущая страница с ошибкой
     */
    public NewIssuePage clickCreateWithoutTitle() {
        return clickSubmitWithEmptyTitle();
    }
    
    /**
     * Получает текст ошибки
     * @return текст ошибки
     */
    public String getErrorMessage() {
        return getErrorText(titleError);
    }
    
    /**
     * Проверяет, что отображается ошибка о пустом заголовке
     * @return true, если ошибка отображается
     */
    public boolean isEmptyTitleErrorDisplayed() {
        return hasErrorWithText(titleError, TITLE_EMPTY_ERROR);
    }
    
    /**
     * Проверяет наличие ошибки валидации (альтернативное название для совместимости)
     * @return true, если есть ошибка валидации
     */
    public boolean isErrorDisplayed() {
        return hasError(titleError);
    }
    
    @Override
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки страницы создания новой issue");
        waitForElementsVisible(titleField, descriptionField, submitButton);
    }
}
