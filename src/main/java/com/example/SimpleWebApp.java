package com.example;

// ═══════════════════════════════════════════════════════════════════════════
// ИМПОРТЫ: подключаем необходимые классы из Spring Framework
// ═══════════════════════════════════════════════════════════════════════════

// Основные классы Spring Boot для запуска приложения
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Аннотации для создания REST-контроллера
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Класс для работы с датой и временем
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Для получения информации о системе
import java.net.InetAddress;

/**
 * ═══════════════════════════════════════════════════════════════════════════
 * ГЛАВНЫЙ КЛАСС ПРИЛОЖЕНИЯ
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * Это демонстрационное веб-приложение Spring Boot для изучения Docker.
 * Приложение предоставляет простой REST API с несколькими эндпоинтами.
 *
 * @author Дмитрий К.
 * @version 1.0.0
 * @since декабрь 2024
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * АННОТАЦИИ КЛАССА:
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * @SpringBootApplication — комбинированная аннотация, включает:
 *   • @Configuration     — класс содержит конфигурацию Spring
 *   • @EnableAutoConfiguration — автоматическая настройка Spring Boot
 *   • @ComponentScan     — сканирование компонентов в текущем пакете
 *
 * @RestController — комбинированная аннотация, включает:
 *   • @Controller        — это контроллер Spring MVC
 *   • @ResponseBody      — методы возвращают данные напрямую (не имя view)
 */
@SpringBootApplication
@RestController
public class SimpleWebApp {

    // ═══════════════════════════════════════════════════════════════════════
    // КОНСТАНТЫ
    // ═══════════════════════════════════════════════════════════════════════

    /** Версия приложения для отображения на странице */
    private static final String APP_VERSION = "1.0.0";

    /** Имя разработчика */
    private static final String DEVELOPER_NAME = "Дмитрий К.";

    /** Формат даты и времени для отображения */
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    // ═══════════════════════════════════════════════════════════════════════
    // ТОЧКА ВХОДА В ПРИЛОЖЕНИЕ
    // ═══════════════════════════════════════════════════════════════════════

    /**
     * Метод main — точка входа в Java-приложение.
     *
     * SpringApplication.run() выполняет:
     * 1. Создаёт контекст приложения Spring (ApplicationContext)
     * 2. Сканирует и регистрирует все компоненты (@Component, @Service и т.д.)
     * 3. Запускает встроенный веб-сервер Tomcat
     * 4. Применяет автоконфигурацию
     *
     * @param args аргументы командной строки (можно передать настройки)
     */
    public static void main(String[] args) {
        // Запускаем Spring Boot приложение
        SpringApplication.run(SimpleWebApp.class, args);

        // Это сообщение выведется в консоль после запуска
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║  🚀 Приложение успешно запущено!                   ║");
        System.out.println("║  📍 Адрес: http://localhost:8080                   ║");
        System.out.println("║  💚 Health: http://localhost:8080/actuator/health  ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
    }

    // ═══════════════════════════════════════════════════════════════════════
    // REST-ЭНДПОИНТЫ (HTTP-обработчики)
    // ═══════════════════════════════════════════════════════════════════════

    /**
     * ГЛАВНАЯ СТРАНИЦА
     *
     * @GetMapping("/") — обрабатывает HTTP GET запросы на корневой URL.
     * Когда пользователь открывает http://localhost:8080/ в браузере,
     * вызывается именно этот метод.
     *
     * @return HTML-разметка главной страницы
     */
    @GetMapping("/")
    public String home() {
        // Получаем текущее время сервера
        String currentTime = LocalDateTime.now().format(DATE_FORMATTER);

        // Получаем имя хоста (в Docker это будет ID контейнера)
        String hostname = getHostname();

        // Возвращаем HTML-страницу
        // Используем многострочную строку (text blocks, Java 15+)
        return """
            <!DOCTYPE html>
            <html lang="ru">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Docker Demo Application</title>
                <style>
                    /* Стили для красивого отображения страницы */
                    body {
                        font-family: 'Segoe UI', Arial, sans-serif;
                        background: linear-gradient(135deg, #1a1a2e 0%%, #16213e 100%%);
                        color: #eee;
                        min-height: 100vh;
                        margin: 0;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                    }
                    .container {
                        background: rgba(255,255,255,0.1);
                        backdrop-filter: blur(10px);
                        border-radius: 20px;
                        padding: 40px;
                        box-shadow: 0 8px 32px rgba(0,0,0,0.3);
                        max-width: 600px;
                        text-align: center;
                    }
                    h1 {
                        color: #4ecca3;
                        margin-bottom: 10px;
                    }
                    .emoji { font-size: 48px; }
                    .info {
                        background: rgba(78, 204, 163, 0.2);
                        border-radius: 10px;
                        padding: 20px;
                        margin: 20px 0;
                    }
                    .info p { margin: 10px 0; }
                    .label { color: #4ecca3; font-weight: bold; }
                    .success {
                        color: #4ecca3;
                        font-size: 1.2em;
                        font-weight: bold;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="emoji">🐳</div>
                    <h1>Docker Demo Application</h1>
                    <p class="success">✅ Приложение успешно запущено в Docker контейнере!</p>
                    
                    <div class="info">
                        <p><span class="label">⏰ Время сервера:</span> %s</p>
                        <p><span class="label">🖥️ Hostname:</span> %s</p>
                        <p><span class="label">📦 Версия:</span> %s</p>
                        <p><span class="label">👨‍💻 Разработчик:</span> %s</p>
                    </div>
                    
                    <p style="color: #888; font-size: 0.9em;">
                        Spring Boot + Docker | JDK 17 | Alpine Linux
                    </p>
                </div>
            </body>
            </html>
            """.formatted(currentTime, hostname, APP_VERSION, DEVELOPER_NAME);
    }

    /**
     * ЭНДПОИНТ ПРОВЕРКИ ЗДОРОВЬЯ (простой)
     *
     * Дополнительный эндпоинт для быстрой проверки.
     * Основной health check через Spring Actuator: /actuator/health
     *
     * @return текстовое сообщение о статусе
     */
    @GetMapping("/health")
    public String health() {
        return "OK - Application is running. Time: " +
                LocalDateTime.now().format(DATE_FORMATTER);
    }

    /**
     * ИНФОРМАЦИЯ О ПРИЛОЖЕНИИ
     *
     * Возвращает JSON с информацией о приложении.
     *
     * @return строка в формате JSON
     */
    @GetMapping("/info")
    public String info() {
        return """
            {
                "application": "Docker Demo Application",
                "version": "%s",
                "developer": "%s",
                "java_version": "%s",
                "hostname": "%s",
                "timestamp": "%s"
            }
            """.formatted(
                APP_VERSION,
                DEVELOPER_NAME,
                System.getProperty("java.version"),
                getHostname(),
                LocalDateTime.now().format(DATE_FORMATTER)
        );
    }

    // ═══════════════════════════════════════════════════════════════════════
    // ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ
    // ═══════════════════════════════════════════════════════════════════════

    /**
     * Получает имя хоста (hostname) текущей машины.
     * В Docker-контейнере это будет короткий ID контейнера.
     *
     * @return имя хоста или "unknown" при ошибке
     */
    private String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "unknown";
        }
    }
}