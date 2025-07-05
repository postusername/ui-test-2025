package ru.github.components;

import com.codeborne.selenide.SelenideElement;
import ru.github.base.BaseComponent;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * Компонент для работы с комментариями
 */
public class CommentComponent extends BaseComponent {
    
    private final SelenideElement commentElement;
    private final SelenideElement authorLink;
    private final SelenideElement commentText;
    private final SelenideElement editButton;
    private final SelenideElement deleteButton;

    /**
     * Конструктор компонента комментария по тексту
     * @param commentText текст комментария
     */
    public CommentComponent(String commentText) {
        this.commentElement = $x("//div[contains(@class, 'comment-body') and contains(text(), '" + commentText + "')]");
        this.authorLink = commentElement.$(".author");
        this.commentText = commentElement.$(".comment-body");
        this.editButton = commentElement.$x(".//button[contains(text(), 'Edit')]");
        this.deleteButton = commentElement.$x(".//button[contains(text(), 'Delete')]");
        
        log.info("Инициализация компонента комментария по тексту: {}", commentText);
    }
    
    /**
     * Получает текст комментария
     * @return текст комментария
     */
    public String getCommentText() {
        return commentText.shouldBe(visible).getText();
    }
    
    /**
     * Получает имя автора комментария
     * @return имя автора
     */
    public String getAuthor() {
        return authorLink.shouldBe(visible).getText();
    }
    
    /**
     * Проверяет, что комментарий принадлежит указанному автору
     * @param expectedAuthor ожидаемый автор
     * @return true, если автор совпадает
     */
    public boolean isAuthoredBy(String expectedAuthor) {
        return getAuthor().equals(expectedAuthor);
    }
    
    /**
     * Проверяет, что комментарий содержит указанный текст
     * @param expectedText ожидаемый текст
     * @return true, если текст содержится в комментарии
     */
    public boolean containsText(String expectedText) {
        return getCommentText().contains(expectedText);
    }
    
    /**
     * Редактирует комментарий
     */
    public void clickEdit() {
        log.info("Нажатие кнопки редактирования комментария");
        editButton.shouldBe(visible).click();
    }
    
    /**
     * Удаляет комментарий
     */
    public void clickDelete() {
        log.info("Нажатие кнопки удаления комментария");
        deleteButton.shouldBe(visible).click();
    }
    
    /**
     * Проверяет, что комментарий доступен для редактирования
     * @return true, если кнопка редактирования доступна
     */
    public boolean isEditable() {
        return editButton.isDisplayed();
    }
    
    /**
     * Проверяет, что комментарий доступен для удаления
     * @return true, если кнопка удаления доступна
     */
    public boolean isDeletable() {
        return deleteButton.isDisplayed();
    }
    
    /**
     * Проверяет, что комментарий отображается
     * @return true, если комментарий видим
     */
    public boolean isVisible() {
        return isVisible(commentElement);
    }
}
