package page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class CardFormPage {
    private static final String REQUIRED_FIELD = "Поле обязательно для заполнения";
    private static final String INCORRECT_FORMAT = "Неверный формат";
    private static final String INCORRECT_CARD_DATE = "Неверно указан срок действия карты";

    //находим элементы поля "форма карты"
    private final SelenideElement title = $("#root > div > h3");
    private final SelenideElement form = $("form");

    private final SelenideElement numberField = form.$("fieldset > div:nth-child(1) > span > span > span.input__box > input");
    private final SelenideElement monthField = form.$("fieldset > div:nth-child(2) > span > span:nth-child(1) > span > span > span.input__box > input");
    private final SelenideElement yearField = form.$("fieldset > div:nth-child(2) > span > span:nth-child(2) > span > span > span.input__box > input");
    private final SelenideElement nameField = form.$("fieldset > div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private final SelenideElement cvcField = form.$("fieldset > div:nth-child(3) > span > span:nth-child(2) > span > span > span.input__box > input");
    private final SelenideElement submitButton = form.$("fieldset > div:nth-child(4) > button");

    private final SelenideElement numberFieldError = form.$("fieldset > div:nth-child(1) > span > span > span.input__sub");
    private final SelenideElement monthFieldError = form.$("fieldset > div:nth-child(2) > span > span:nth-child(1) > span > span > span.input__sub");
    private final SelenideElement yearFieldError = form.$("fieldset > div:nth-child(2) > span > span:nth-child(2) > span > span > span.input__sub");
    private final SelenideElement nameFieldError = form.$("fieldset > div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__sub");
    private final SelenideElement cvcFieldError = form.$("fieldset > div:nth-child(3) > span > span:nth-child(2) > span > span > span.input__sub");


    //проверяем видимость и содержание надписей полей карты
    public CardFormPage() {
        title.shouldBe(visible);
        form.shouldBe(visible);

        numberField.shouldBe(visible);
        monthField.shouldBe(visible);
        yearField.shouldBe(visible);
        nameField.shouldBe(visible);
        cvcField.shouldBe(visible);

        submitButton.should(text("Продолжить"), visible);
    }

    //прописываем методы заполнения полей карты
    public void hasTitle(String title) {
        this.title.should(text(title), visible);
    }

    public void setCardNumber(String cardNumber) {
        PageUtils
                .clearInput(numberField)
                .setValue(cardNumber);
    }

    public void setMonth(String month) {
        PageUtils
                .clearInput(monthField)
                .setValue(month);
    }

    public void setYear(String year) {
        PageUtils
                .clearInput(yearField)
                .setValue(year);
    }

    public void setHolderName(String holderName) {
        PageUtils
                .clearInput(nameField)
                .setValue(holderName);
    }

    public void setCvc(String cvc) {
        PageUtils
                .clearInput(cvcField)
                .setValue(cvc);
    }

    public void submit() {
        submitButton.click();
    }

    //сообщения о неверно-заполненных полях
    public void hasErrorEmptyNumber() {
        numberFieldError
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text(REQUIRED_FIELD));
    }

    public void hasErrorInvalidFormatNumber() {
        numberFieldError
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text(INCORRECT_FORMAT));
    }

    public void hasErrorEmptyMonth() {
        monthFieldError
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text(REQUIRED_FIELD));
    }

    public void hasErrorInvalidFormatMonth() {
        monthFieldError
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text(INCORRECT_FORMAT));
    }

    public void hasErrorIncorrectMonth() {
        monthFieldError
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text(INCORRECT_CARD_DATE));
    }

    public void hasErrorEmptyYear() {
        yearFieldError
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text(REQUIRED_FIELD));
    }

    public void hasErrorInvalidFormatYear() {
        yearFieldError
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text(INCORRECT_FORMAT));
    }

    public void hasErrorIncorrectYear() {
        yearFieldError
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text(INCORRECT_CARD_DATE));
    }

    public void hasErrorPastYear() {
        yearFieldError
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text("Истёк срок действия карты"));
    }

    public void hasErrorEmptyName() {
        nameFieldError
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text(REQUIRED_FIELD));
    }

    public void hasErrorInvalidFormatName() {
        nameFieldError
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text(INCORRECT_FORMAT));
    }

    public void hasErrorEmptyCvc() {
        cvcFieldError
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text(REQUIRED_FIELD));
    }

    public void hasErrorInvalidFormatCvc() {
        cvcFieldError
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text(INCORRECT_FORMAT));
    }

    //сохраняет пустые значения полей
    public void hasEmptyNumberField() {
        numberField.should(empty);
        numberFieldError.shouldNotBe(visible);
    }

    public void hasEmptyMonthField() {
        monthField.should(empty);
        monthFieldError.shouldNotBe(visible);
    }

    public void hasEmptyYearField() {
        yearField.should(empty);
        yearFieldError.shouldNotBe(visible);
    }

    public void hasEmptyNameField() {
        nameField.should(empty);
        nameFieldError.shouldNotBe(visible);
    }

    public void hasEmptyCvcField() {
        cvcField.should(empty);
        cvcFieldError.shouldNotBe(visible);
    }

    public void clearFormFields() {
        PageUtils.clearInput(numberField);
        PageUtils.clearInput(monthField);
        PageUtils.clearInput(yearField);
        PageUtils.clearInput(nameField);
        PageUtils.clearInput(cvcField);
    }
}

