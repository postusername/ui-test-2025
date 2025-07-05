package ru.github.pages;

import com.codeborne.selenide.SelenideElement;
import ru.github.base.BasePage;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * Страница создания новой issue
 */
public class NewIssuePage extends BasePage {
    
    private static final String SUBMIT_BUTTON_TEXT = "Create";
    private static final String TITLE_EMPTY_ERROR = "Title can not be empty";
    
    private final SelenideElement titleField = $("input[aria-label='Add a title']");
    private final SelenideElement descriptionField = $("textarea[placeholder='Type your description here…']");
    private final SelenideElement submitButton = $("button[data-testid='create-issue-button']");
    private final SelenideElement titleError = $("#title-validation");
    
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
        log.info("Заполнение заголовка issue: {}", title);
        titleField.shouldBe(visible).setValue(title);
        return this;
    }
    
    /**
     * Заполняет поле описания
     * @param description описание issue
     * @return текущая страница
     */
    public NewIssuePage fillDescription(String description) {
        log.info("Заполнение описания issue");
        descriptionField.shouldBe(visible).setValue(description);
        return this;
    }
    
    /**
     * Очищает поле заголовка
     * @return текущая страница
     */
    public NewIssuePage clearTitle() {
        log.info("Очистка поля заголовка");
        titleField.shouldBe(visible).clear();
        return this;
    }
    
    /**
     * Нажимает кнопку создания issue
     * @return страница деталей созданной issue
     */
    public IssueDetailsPage clickSubmit() {
        log.info("Нажатие кнопки 'Create'");
        submitButton.shouldBe(visible).click();
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
        submitButton.shouldBe(visible).click();
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
        return titleError.shouldBe(visible).getText();
    }
    
    /**
     * Проверяет, что отображается ошибка о пустом заголовке
     * @return true, если ошибка отображается
     */
    public boolean isEmptyTitleErrorDisplayed() {
        return titleError.isDisplayed() && titleError.getText().contains(TITLE_EMPTY_ERROR);
    }
    
    /**
     * Проверяет наличие ошибки валидации (альтернативное название для совместимости)
     * @return true, если есть ошибка валидации
     */
    public boolean hasValidationError() {
        return titleError.exists() && titleError.isDisplayed();
    }
    
    @Override
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки страницы создания новой issue");
        titleField.shouldBe(visible);
        descriptionField.shouldBe(visible);
        submitButton.shouldBe(visible);
    }
}
