plugins {
    id("java")
    id("io.qameta.allure") version "4.0.1" //подключает плагин Allure к сборщику Gradle
}

group = "ru.netology"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val allureVersion = "2.34.0"

allure {
    version = allureVersion
    adapter {
        autoconfigure = true
        frameworks {
            junit5 {
                adapterVersion = allureVersion
            }
        }
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0")) //BOM (Bill of Materials) управляет версиями JUnit 5, гарантируя, что все его компоненты будут строго версии 5.10.0 и не будут конфликтовать между собой.
    testImplementation("org.junit.jupiter:junit-jupiter") //основная библиотека JUnit 5 (Jupiter) дает аннотации для управления тестами (@Test, @BeforeEach, @ParameterizedTest) и проверки результатов (Assertions).
    testRuntimeOnly("org.junit.platform:junit-platform-launcher") //движок, который позволяет среде разработки (IDEA) или сборщику (Gradle) находить и запускать тесты JUnit 5.

    testImplementation("com.codeborne:selenide:7.16.0") //фреймворк Selenide берет на себя управление браузером, автоматически скачивает нужные драйверы (ChromeDriver, GeckoDriver), умеет «умно» ждать элементы и предоставляет лаконичный синтаксис (вроде open(), $ и shouldHave()).
    testImplementation("com.github.javafaker:javafaker:1.0.2") //библиотека для генерации случайных реалистичных данных, с её помощью можно одной строчкой создавать случайные имена владельцев карт, адреса, телефоны и другие данные для заполнения форм.
    testImplementation("org.slf4j:slf4j-simple:2.0.3") //простой плагин для вывода логов нужен, чтобы в консоли красиво и понятно отображались системные сообщения от Selenide и JUnit во время прогона тестов (информация о кликах, открытиях страниц и ошибках).

    testCompileOnly("org.projectlombok:lombok:1.18.36") //библиотека Lombok избавляет от написания шаблонного кода (геттеров, сеттеров, конструкторов, методов toString()). Можно просто поставить аннотацию (например, @Data), и всё генерируется автоматически во время компиляции.
    testAnnotationProcessor("org.projectlombok:lombok:1.18.36")

    testImplementation("com.zaxxer:HikariCP:7.0.2")
    testImplementation("mysql:mysql-connector-java:8.0.33") //JDBC-драйвер для базы данных MySQL - это «переводчик», который позволяет Java-коду общаться с базой данных MySQL. Без него тесты физически не смогут подключиться к контейнеру MySQL, чтобы проверить, сохранилась ли запись о покупке тура.
    testImplementation ("org.postgresql:postgresql:42.7.1") //JDBC-драйвер для базы данных PostgreSQL - это «переводчик», который позволяет Java-коду общаться с базой данных PostgreSQL. Без него тесты физически не смогут подключиться к контейнеру PostgreSQL, чтобы проверить, сохранилась ли запись о покупке тура.
    testImplementation("commons-dbutils:commons-dbutils:1.8.1") //библиотека-утилита от Apache для упрощения работы с SQL, заменяет тяжеловесные ORM (вроде Hibernate), берет на себя рутину: сама открывает и закрывает соединения с БД, выполняет SQL-запросы (SELECT * FROM payment_entity...) и автоматически превращает строчки из базы данных в удобные Java-объекты.

    testImplementation("io.qameta.allure:allure-selenide:$allureVersion") //плагин-интеграция между фреймворком Allure и браузерным движком Selenide - этот модуль «следит» за действиями Selenide в браузере и если какой-то тест упадет (например, не появилась кнопка или пришла ошибка), этот плагин автоматически сделает скриншот страницы, запишет исходный код HTML и прикрепит их к отчету Allure.
}

tasks.test {
    useJUnitPlatform()

    systemProperty("selenide.headless", System.getProperty("selenide.headless"))
    systemProperty(             //отключает менеджер паролей, для того, что бы он не мешал при тестировании
        "chromeoptions.prefs",
        System.getProperty("chromeoptions.prefs", "profile.password_manager_leak_detection=false")
    )
}