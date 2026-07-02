package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

//находим элементы поля с картинкой, проверяем их видимость и содержание надписей
public class CardPreviewPage {
    private final SelenideElement root = $(".Order_cardPreview__47B2k");

    private final SelenideElement picture = root.$("div > div:nth-child(1) > img");
    private final SelenideElement titleField = root.$("div > div:nth-child(2) > h3");
    private final SelenideElement descriptionField = root.$("div > div:nth-child(2) > ul > li:nth-child(1)");
    private final SelenideElement milesField = root.$("div > div:nth-child(2) > ul > li:nth-child(2)");
    private final SelenideElement percentsField = root.$("div > div:nth-child(2) > ul > li:nth-child(3)");
    private final SelenideElement priceField = root.$("div > div:nth-child(2) > ul > li:nth-child(4)");

    public void shouldBeVisible() {
        root.shouldBe(visible);
    }

    public void hasPicture() {
        picture.shouldBe(visible);
    }

    public void hasTitle(String text) {
        titleField.should(text(text), visible);
    }

    public void hasDescription(String text) {
        descriptionField.should(text(text), visible);
    }

    public void hasMiles(String text) {
        milesField.should(text(text), visible);
    }

    public void hasPercents(String text) {
        percentsField.should(text(text), visible);
    }

    public void hasPrice(String text) {
        priceField.should(text(text), visible);
    }

    public void validatedDefaults() {
        shouldBeVisible();
        hasPicture();
        hasTitle("Марракэш");
        hasDescription("Сказочный Восток");
        hasMiles("33 360 миль на карту");
        hasPercents("До 7% на остаток по счёту");
        hasPrice("Всего 45 000 руб.!");
    }
}