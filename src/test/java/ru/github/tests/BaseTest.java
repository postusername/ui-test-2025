package ru.github.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.github.services.AuthService;
import ru.github.utils.ConfigReader;
import ru.github.pages.RepositoryPage;
import ru.github.pages.IssuesListPage;
import ru.github.pages.NewIssuePage;
import ru.github.pages.IssueDetailsPage;

/**
 * Базовый класс для всех тестов
 */
public abstract class BaseTest {
    
    protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    
    protected AuthService authService;
    protected static final String REPOSITORY_PATH = ConfigReader.getTestData("repository.owner") + "/" + ConfigReader.getTestData("repository.name");
    
    private static final String BASE_URL = ConfigReader.getProperty("base.url");
    private static final String BROWSER_HEADLESS = ConfigReader.getProperty("browser.headless");
    private static final String BROWSER_SIZE = ConfigReader.getProperty("browser.size");
    private static final String BROWSER_TIMEOUT = ConfigReader.getProperty("browser.timeout");
    
     /**
     * Инициализация теста - выполняется перед каждым тестом
     * Настраивает Selenide и открывает приложение
     */
    @BeforeEach
    public void setUp() {
        log.info("Начало выполнения теста: {}", this.getClass().getSimpleName());
        authService = new AuthService();
        configureSelenide();
        openApplication();
    }

    /**
     * Завершение теста - выполняется после каждого теста
     * Закрывает браузер и очищает ресурсы
     */
    @AfterEach
    public void tearDown() {
        log.info("Завершение выполнения теста: {}", this.getClass().getSimpleName());
        Selenide.closeWebDriver();
    }
    
    /**
     * Настройка конфигурации Selenide
     */
    private void configureSelenide() {
        Configuration.browser = "chrome";
        Configuration.baseUrl = BASE_URL;
        Configuration.browserSize = BROWSER_SIZE != null ? BROWSER_SIZE : "1920x1080";
        Configuration.timeout = BROWSER_TIMEOUT != null ? Long.parseLong(BROWSER_TIMEOUT) : 10000L;
        Configuration.headless = BROWSER_HEADLESS != null ? Boolean.parseBoolean(BROWSER_HEADLESS) : false;
        
        Configuration.holdBrowserOpen = false;
        Configuration.savePageSource = false;
        Configuration.screenshots = true;
        Configuration.reportsFolder = "target/screenshots";
        
        log.info("Конфигурация Selenide настроена: browser=firefox, baseUrl={}, headless={}, size={}", 
                BASE_URL, Configuration.headless, Configuration.browserSize);
    }
    
    /**
     * Открытие приложения по базовому URL
     */
    private void openApplication() {
        log.info("Открытие приложения по URL: {}", BASE_URL);
        Selenide.open(BASE_URL);
    }

    /**
     * Аутентификация и навигация к тестовому репозиторию
     * @return страница репозитория
     */
    protected RepositoryPage navigateToTestRepository() {
        return authService.auth().navigateToTestRepository();
    }

    /**
     * Создание новой issue с заголовком и описанием
     * @param title заголовок issue
     * @param description описание issue
     * @return страница деталей созданной issue
     */
    protected IssueDetailsPage createIssue(String title, String description) {
        RepositoryPage repositoryPage = navigateToTestRepository();
        IssuesListPage issuesPage = repositoryPage.clickIssuesTab();
        NewIssuePage newIssuePage = issuesPage.clickNewIssue();
        return newIssuePage
                .fillTitle(title)
                .fillDescription(description)
                .clickCreate();
    }

    /**
     * Получение страницы создания новой issue
     * @return страница создания новой issue
     */
    protected NewIssuePage getNewIssuePage() {
        RepositoryPage repositoryPage = navigateToTestRepository();
        IssuesListPage issuesPage = repositoryPage.clickIssuesTab();
        return issuesPage.clickNewIssue();
    }
}
