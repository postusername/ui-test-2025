package ru.github.components;

import ru.github.components.elements.TextElement;
import ru.github.components.elements.TextareaElement;
import ru.github.components.elements.ButtonElement;

/**
 * Компонент формы комментариев
 */
public class CommentFormComponent extends BaseComponent {
    
    private final TextareaElement commentField = TextareaElement.byPlaceholder("Use Markdown to format your comment");
    private final ButtonElement commentButton = ButtonElement.byXpath("//button[contains(@class, 'prc-Button-ButtonBase-c50BI') and contains(., 'Comment')]");
    
    /**
     * Конструктор компонента формы комментариев
     */
    public CommentFormComponent() {
        super();
    }
    
    /**
     * Добавляет комментарий к issue
     * @param commentText текст комментария
     * @return текущий компонент
     */
    public CommentFormComponent addComment(String commentText) {
        log.info("Добавление комментария: {}", commentText);
        commentField.setValue(commentText);
        commentButton.click();
        
        return this;
    }
    
    /**
     * Проверяет, что поле комментария видимо
     * @return true, если поле видимо
     */
    public boolean isCommentFieldVisible() {
        return commentField.isVisible();
    }
    
    /**
     * Проверяет, что кнопка отправки комментария видима
     * @return true, если кнопка видима
     */
    public boolean isCommentButtonVisible() {
        return commentButton.isVisible();
    }
    
    /**
     * Очищает поле комментария
     * @return текущий компонент
     */
    public CommentFormComponent clearComment() {
        log.info("Очистка поля комментария");
        commentField.clear();
        return this;
    }
    
    /**
     * Устанавливает текст в поле комментария
     * @param text текст комментария
     * @return текущий компонент
     */
    public CommentFormComponent setCommentText(String text) {
        log.info("Установка текста комментария: {}", text);
        commentField.setValue(text);
        return this;
    }
    
    /**
     * Добавляет текст к существующему комментарию
     * @param text текст для добавления
     * @return текущий компонент
     */
    public CommentFormComponent appendCommentText(String text) {
        log.info("Добавление текста к комментарию: {}", text);
        commentField.appendText(text);
        return this;
    }
    
    /**
     * Получает текст из поля комментария
     * @return текст комментария
     */
    public String getCommentText() {
        return commentField.getValue();
    }
    
    /**
     * Проверяет, что поле комментария пустое
     * @return true, если поле пустое
     */
    public boolean isCommentFieldEmpty() {
        return commentField.isEmpty();
    }
    
    /**
     * Кликает по полю комментария
     * @return текущий компонент
     */
    public CommentFormComponent clickCommentField() {
        log.info("Клик по полю комментария");
        commentField.click();
        return this;
    }

    public boolean isCommentWithTextExists(String commentText) {
        try {
            TextElement markdownBody = TextElement.byCssSelector("[data-testid*='comment-viewer-outer-box'] [data-testid='markdown-body']");
            markdownBody.waitForVisible();
            log.info("Текст комментария: {}", markdownBody.getText());
            return markdownBody.exists() && commentText.equals(markdownBody.getText());
        } catch (Exception e) {
            log.warn("Ошибка при поиске комментария с текстом '{}': {}", commentText, e.getMessage());
            return false;
        }
    }
}
