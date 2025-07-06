package ru.github.pages;

import ru.github.components.elements.ButtonElement;
import ru.github.components.elements.InputElement;
import ru.github.components.elements.TextareaElement;

import static com.codeborne.selenide.Selenide.*;

/**
 * Страница редактирования issue
 */
public class EditIssuePage extends BasePage {
    
    private final InputElement titleField = InputElement.byXpath("//input[@name='issue[title]'] | //input[contains(@placeholder, 'Title')] | //input[contains(@aria-label, 'Title')]");
    private final TextareaElement descriptionField = TextareaElement.byXpath("//textarea[@name='issue[body]'] | //textarea[contains(@placeholder, 'comment')] | //textarea[contains(@aria-label, 'Comment')]");
    private final ButtonElement saveButton = ButtonElement.byXpath("//button[contains(text(), 'Update issue')] | //button[contains(text(), 'Save')] | //button[@type='submit'][contains(@class, 'btn-primary')]");
    private final ButtonElement cancelButton = ButtonElement.byXpath("//button[contains(text(), 'Cancel')] | //a[contains(text(), 'Cancel')]");
    private final ButtonElement editButton = ButtonElement.byXpath("//button[@data-testid='edit-issue-title-button'] | //button[contains(text(), 'Edit')] | //a[contains(text(), 'Edit')]");

    /**
     * Конструктор страницы редактирования issue
     */
    public EditIssuePage() {
        super();
        waitForPageLoad();
    }
    
    /**
     * Очищает поле заголовка
     * @return текущая страница
     */
    public EditIssuePage clearTitle() {
        clearField(titleField, "заголовок issue");
        return this;
    }
    
    /**
     * Заполняет поле заголовка
     * @param title новый заголовок
     * @return текущая страница
     */
    public EditIssuePage fillTitle(String title) {
        fillField(titleField, title, "заголовок issue");
        return this;
    }
    
    /**
     * Заполняет поле описания
     * @param description новое описание
     * @return текущая страница
     */
    public EditIssuePage fillDescription(String description) {
        fillTextarea(descriptionField, description, "описание issue");
        return this;
    }
    
    /**
     * Получает текущий заголовок
     * @return текущий заголовок
     */
    public String getCurrentTitle() {
        return titleField.getValue();
    }
    
    /**
     * Получает текущее описание
     * @return текущее описание
     */
    public String getCurrentDescription() {
        return descriptionField.getValue();
    }
    
    /**
     * Сохраняет изменения
     * @return страница деталей issue
     */
    public IssueDetailsPage clickSave() {
        log.info("Нажатие кнопки 'Save'");
        saveButton.click();
        return new IssueDetailsPage();
    }
    
    /**
     * Отменяет изменения
     * @return страница деталей issue
     */
    public IssueDetailsPage clickCancel() {
        log.info("Нажатие кнопки 'Cancel'");
        cancelButton.click();
        return new IssueDetailsPage();
    }
    
    @Override
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки страницы редактирования issue");
        waitForElementsVisible(titleField, descriptionField, saveButton);
    }
}
