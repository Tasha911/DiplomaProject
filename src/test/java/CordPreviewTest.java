import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.CardPreviewPage;

public class CordPreviewTest {

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