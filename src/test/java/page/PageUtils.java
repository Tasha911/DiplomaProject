package page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

public class PageUtils {

    //метод очистки поля и постановки курсора в начало строки
    public static SelenideElement clearInput(SelenideElement element) {
        return element
                .press(Keys.SHIFT, Keys.HOME)
                .press(Keys.BACK_SPACE);
    }

}
