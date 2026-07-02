package page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

//находим элементы главной страницы, проверяем их видимость и содержание надписей
public class MainPage {

    private final SelenideElement title = $("#root > div > h2");

    private final SelenideElement buyButton = $("#root > div > button:nth-child(3)");
    private final SelenideElement creditBuyButton = $("#root > div > button.button.button_view_extra.button_size_m.button_theme_alfa-on-white");

    private final SelenideElement successNotification = $(".notification_status_ok");
    private final SelenideElement errorNotification = $(".notification_status_error");

    public MainPage() {
        title.should(text("Путешествие дня"), visible);
        buyButton.should(text("Купить"), visible);
        creditBuyButton.should(text("Купить в кредит"), visible);

        successNotification.shouldNotBe(visible);
        errorNotification.shouldNotBe(visible);
    }

    //прописываем методы взаимодействия с полями главной страницы
    public void hasTitle(String title) {
        this.title.should(text(title), visible);
    }

    public CardFormPage selectBuyTab() {
        buyButton.click();
        var cartFormPage = new CardFormPage();
        cartFormPage.hasTitle("Оплата по карте");

        return cartFormPage;
    }

    public CardFormPage selectCreditBuyTab() {
        creditBuyButton.click();
        var cartFormPage = new CardFormPage();
        cartFormPage.hasTitle("Кредит по данным карты");

        return cartFormPage;
    }

    public void hasSuccessNotification() {
        successNotification
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Успешно"), text("Операция одобрена Банком."));
    }

    public void hasErrorNotification() {
        errorNotification
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Ошибка"), text("Ошибка! Банк отказал в проведении операции."));
    }
}