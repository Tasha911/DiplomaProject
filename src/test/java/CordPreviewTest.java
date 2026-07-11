import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.CardPreviewPage;

public class CordPreviewTest {

    //добавляем листенер в тестовый класс перед выполнением всех тестов
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    //удаляем листенер после выполнением всех тестов
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        //запускаем aqa-shop.jar
        Selenide.open("http://localhost:8080");
    }

    @Test
    void shouldDisplayCorrectPreviewInfo() {
        CardPreviewPage previewPage = new CardPreviewPage();  //инициализируем страницу PreviewPage

        previewPage.validatedDefaults(); //запускаем проверку всех исходных картинок и текстов (Марракэш, Сказочный Восток, мили, проценты и цена)
    }

}
