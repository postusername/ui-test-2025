package ru.github.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Утилитарный класс для чтения конфигурационных файлов
 */
public class ConfigReader {
    
    private static final Properties CONFIG_PROPERTIES = new Properties();
    private static final Properties TEST_DATA_PROPERTIES = new Properties();
    
    static {
        loadProperties();
    }
    
    /**
     * Получает значение из файла config.properties
     * @param key ключ свойства
     * @return значение свойства
     */
    public static String getProperty(String key) {
        return CONFIG_PROPERTIES.getProperty(key);
    }
    
    /**
     * Получает тестовые данные из файла test-data.properties
     * @param key ключ свойства
     * @return значение свойства
     */
    public static String getTestData(String key) {
        return TEST_DATA_PROPERTIES.getProperty(key);
    }
    
    /**
     * Получает свойство с возможностью указать значение по умолчанию
     * @param key ключ свойства
     * @param defaultValue значение по умолчанию
     * @return значение свойства или значение по умолчанию
     */
    public static String getProperty(String key, String defaultValue) {
        return CONFIG_PROPERTIES.getProperty(key, defaultValue);
    }
    
    private static void loadProperties() {
        loadConfigProperties();
        loadTestDataProperties();
    }
    
    private static void loadConfigProperties() {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input != null) {
                CONFIG_PROPERTIES.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить config.properties", e);
        }
    }
    
    private static void loadTestDataProperties() {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("test-data.properties")) {
            if (input != null) {
                TEST_DATA_PROPERTIES.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить test-data.properties", e);
        }
    }
}
