package ru.github.pages;

import ru.github.components.HeaderComponent;
import ru.github.components.elements.ImageElement;
import ru.github.utils.ConfigReader;

import static com.codeborne.selenide.Selenide.open;

/**
 * Главная страница GitHub после авторизации
 */
public class MainPage extends BasePage {

    private static final String REPOSITORY_PATH_TEMPLATE = "/%s/%s";

    private final ImageElement userAvatar = ImageElement.byClass("avatar");
    private final HeaderComponent headerComponent = new HeaderComponent();
    
    /**
     * Конструктор главной страницы
     */
    public MainPage() {
        super();
        waitForPageLoad();
    }
    
    /**
     * Переходит к репозиторию по владельцу и имени
     * @param owner владелец репозитория
     * @param repositoryName имя репозитория
     * @return страница репозитория
     */
    public RepositoryPage navigateToRepository(String owner, String repositoryName) {
        String repositoryPath = String.format(REPOSITORY_PATH_TEMPLATE, owner, repositoryName);
        log.info("Переход к репозиторию: {}", repositoryPath);
        open(repositoryPath);
        return new RepositoryPage();
    }
    
    /**
     * Переходит к тестовому репозиторию из конфигурации
     * @return страница репозитория
     */
    public RepositoryPage navigateToTestRepository() {
        String owner = ConfigReader.getTestData("repository.owner");
        String repositoryName = ConfigReader.getTestData("repository.name");
        return navigateToRepository(owner, repositoryName);
    }
    
    /**
     * Получает компонент заголовка
     * @return компонент заголовка
     */
    public HeaderComponent getHeaderComponent() {
        return headerComponent;
    }
    
    @Override
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки главной страницы");
        userAvatar.waitForVisible();
    }
}
