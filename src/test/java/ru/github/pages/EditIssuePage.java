package ru.github.pages;

import com.codeborne.selenide.SelenideElement;
import ru.github.base.BasePage;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * Страница редактирования issue
 */
public class EditIssuePage extends BasePage {
    
    private static final String SAVE_BUTTON_TEXT = "Save";
    private static final String CANCEL_BUTTON_TEXT = "Cancel";
    
    private final SelenideElement titleField = $("#issue_title");
    private final SelenideElement descriptionField = $("#issue_body");
    private final SelenideElement saveButton = $x("//button[contains(@class, 'btn-primary') and contains(text(), 'Save')]");
    private final SelenideElement cancelButton = $x("//button[contains(@class, 'btn') and contains(text(), 'Cancel')]");
    
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
        log.info("Очистка поля заголовка");
        titleField.shouldBe(visible).clear();
        return this;
    }
    
    /**
     * Заполняет поле заголовка
     * @param title новый заголовок
     * @return текущая страница
     */
    public EditIssuePage fillTitle(String title) {
        log.info("Заполнение нового заголовка: {}", title);
        titleField.shouldBe(visible).setValue(title);
        return this;
    }
    
    /**
     * Заполняет поле описания
     * @param description новое описание
     * @return текущая страница
     */
    public EditIssuePage fillDescription(String description) {
        log.info("Заполнение нового описания");
        descriptionField.shouldBe(visible).setValue(description);
        return this;
    }
    
    /**
     * Получает текущий заголовок
     * @return текущий заголовок
     */
    public String getCurrentTitle() {
        return titleField.shouldBe(visible).getValue();
    }
    
    /**
     * Получает текущее описание
     * @return текущее описание
     */
    public String getCurrentDescription() {
        return descriptionField.shouldBe(visible).getValue();
    }
    
    /**
     * Сохраняет изменения
     * @return страница деталей issue
     */
    public IssueDetailsPage clickSave() {
        log.info("Нажатие кнопки 'Save'");
        saveButton.shouldBe(visible).click();
        return new IssueDetailsPage();
    }
    
    /**
     * Отменяет изменения
     * @return страница деталей issue
     */
    public IssueDetailsPage clickCancel() {
        log.info("Нажатие кнопки 'Cancel'");
        cancelButton.shouldBe(visible).click();
        return new IssueDetailsPage();
    }
    
    @Override
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки страницы редактирования issue");
        titleField.shouldBe(visible);
        descriptionField.shouldBe(visible);
        saveButton.shouldBe(visible);
    }
}
