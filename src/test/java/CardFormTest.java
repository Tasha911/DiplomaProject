import annotations.CardTest;
import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import page.CardFormPage;
import page.MainPage;

public class CardFormTest {

    @BeforeEach
    void setUpEach() {
        //запускаем aqa-shop.jar
        Selenide.open("http://localhost:8080");
    }

    //метод для выбора кнопки на основе параметра (для параметризированных тестов)
    private CardFormPage selectTab(MainPage mainPage, String tabType) {
        if (tabType.equals("pay")) {
            return mainPage.selectBuyTab();
        }
        return mainPage.selectCreditBuyTab();
    }

    //1. Успешная покупка тура по карте: заполнение формы валидными данными, отправка, появление сообщения об успешной оплате
    @CardTest
    void shouldSuccessWithValidData(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        mainPage.hasSuccessNotification();
    }

    //2. Отказ в покупке по карте: заполнение формы невалидными данными (пустое значение номера карты), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfCardNumberIsEmpty(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        //---пустое значение номера карты
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorEmptyNumber();
    }

    //3. Отказ в покупке по карте: заполнение формы невалидными данными (невалидное значение номера карты), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfCardNumberIsInvalid(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.generateInvalidCardNumber(Faker.instance())); //невалидное значение номера карты
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        mainPage.hasErrorNotification();
    }

    //4. Отказ в покупке по карте: заполнение формы невалидными данными (короткий номер карты), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfCardNumberIsShort(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.generateShortCardNumber(Faker.instance())); //короткий номер карты
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorInvalidFormatNumber();
    }

    //5. Проверка поля CardNumber на возможность ввода спецсимволов, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfCardNumberHasSpecialSymbols(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.generateSpecialSymbols()); //спецсимволы в номере
        cardFormPage.hasEmptyNumberField();
    }

    //6. Проверка поля CardNumber на возможность ввода букв, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfCardNumberHasLetters(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.generateLetters()); //буквы в номере
        cardFormPage.hasEmptyNumberField();
    }

    //7. Отказ в покупке по карте: заполнение формы невалидными данными (пустое значение месяца), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfMonthIsEmpty(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        //---пустое значение месяца
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorEmptyMonth();
    }

    //8. Отказ в покупке по карте: заполнение формы невалидными данными (короткое значение месяца), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfMonthIsShort(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateShortMonth()); //короткое значение месяца
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorInvalidFormatMonth();
    }

    //9. Отказ в покупке по карте: заполнение формы невалидными данными (нулевой месяц), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfMonthIsZero(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.getZeroMonth()); //нулевой месяц
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorIncorrectMonth();
    }

    //10. Отказ в покупке по карте: заполнение формы невалидными данными (тринадцатый месяц), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfMonthIsThirteenth(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.getThirteenthMonth()); //тринадцатый месяц
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorIncorrectMonth();
    }

    //11. Отказ в покупке по карте: заполнение формы невалидными данными (прошлый месяц текущего года), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfMonthIsPast(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generatePastMonth()); //прошлый месяц
        cardFormPage.setYear(DataGenerator.getCurrentYear()); //текущий год
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorIncorrectMonth();
    }

    //12. Проверка поля Month на возможность ввода спецсимволов, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfMonthHasSpecialSymbols(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setMonth(DataGenerator.generateSpecialSymbols()); //спецсимволы в месяце
        cardFormPage.hasEmptyMonthField();
    }

    //13. Проверка поля Month на возможность ввода букв, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfMonthHasLetters(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setMonth(DataGenerator.generateLetters()); //буквы в месяце
        cardFormPage.hasEmptyMonthField();
    }

    //14. Отказ в покупке по карте: заполнение формы невалидными данными (пустое значение года), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfYeaIsEmpty(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateMonth());
        //---пустое значение года
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorEmptyYear();
    }

    //15. Отказ в покупке по карте: заполнение формы невалидными данными (короткое значение года), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfYearIsShort(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateShortYear()); //короткое значение года
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorInvalidFormatYear();
    }

    //16. Отказ в покупке по карте: заполнение формы невалидными данными (прошлый год), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfYeaIsPast(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generatePastYear()); //прошлый год
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorPastYear();
    }

    //17. Отказ в покупке по карте: заполнение формы невалидными данными (указан год более 5 лет), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfYearIsMoreFive(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateMoreFiveYear()); //указан год более 5 лет
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorIncorrectYear();
    }

    //18. Проверка поля Year на возможность ввода спецсимволов, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfYearHasSpecialSymbols(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setYear(DataGenerator.generateSpecialSymbols()); //спецсимволы в поле года
        cardFormPage.hasEmptyYearField();
    }

    //19. Проверка поля Year на возможность ввода букв, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfYearHasLetters(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setYear(DataGenerator.generateLetters()); //буквы в поле года
        cardFormPage.hasEmptyYearField();
    }

    //20. Отказ в покупке по карте: заполнение формы невалидными данными (пустое значение имени), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfNameIsEmpty(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateYear());
        //---пустое значение имени
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorEmptyName();
    }

    //21. Отказ в покупке по карте: заполнение формы невалидными данными (имя на русском языке), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfNameIsRu(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("ru")); //имя на русском языке
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorInvalidFormatName();
    }

    //22. Проверка поля Name на возможность ввода спецсимволов, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfNameHasSpecialSymbols(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setHolderName(DataGenerator.generateSpecialSymbols()); //спецсимволы в имени
        cardFormPage.hasEmptyNameField();
    }

    //23. Проверка поля Name на возможность ввода цифр, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfNameHasNumeric(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setHolderName(DataGenerator.generateNumericName(Faker.instance())); //цифры в имени
        cardFormPage.hasEmptyNameField();
    }

    //24. Отказ в покупке по карте: заполнение формы невалидными данными (пустое значение CVC), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfCvcIsEmpty(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        //---пустое значение CVC

        cardFormPage.submit();
        cardFormPage.hasErrorEmptyCvc();
    }

    //25. Отказ в покупке по карте: заполнение формы невалидными данными (двузначное число в CVC), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfCvcIsShort(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateTooFewCvc()); //двузначное число в CVC

        cardFormPage.submit();
        cardFormPage.hasErrorInvalidFormatCvc();
    }

    //26. Проверка поля Cvc на возможность ввода спецсимволов, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfCvcHasSpecialSymbols(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCvc(DataGenerator.generateSpecialSymbols()); //спецсимволы в CVC
        cardFormPage.hasEmptyCvcField();
    }

    //27. Проверка поля Cvc на возможность ввода букв, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfCvcHasLetters(String tabType) {
        var mainPage = new MainPage();
        var cardFormPage = selectTab(mainPage, tabType); //автоматически выбирать нужную вкладку

        cardFormPage.setCvc(DataGenerator.generateLetters()); //буквы в CVC
        cardFormPage.hasEmptyCvcField();
    }
}