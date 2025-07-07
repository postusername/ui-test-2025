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
    private final TextElement markdownBody = TextElement.byCssSelector("[data-testid*='comment-viewer-outer-box'] [data-testid='markdown-body']");
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

    public boolean isCommentWithTextExists(String commentText) {
        try {
            markdownBody.waitForVisible();
            log.info("Текст комментария: {}", markdownBody.getText());
            return markdownBody.exists() && commentText.equals(markdownBody.getText());
        } catch (Exception e) {
            log.warn("Ошибка при поиске комментария с текстом '{}': {}", commentText, e.getMessage());
            return false;
        }
    }
}
