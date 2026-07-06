import annotations.CardTest;
import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import data.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import page.MainPage;

public class CardFormTest {

    @BeforeEach
    void setUpEach() {
        //запускаем aqa-shop.jar
        Selenide.open("http://localhost:8080");
    }

    //1. Успешная покупка тура по карте: CARDNUMBER-APPROVED (заполнение формы валидными данными, отправка, появление сообщения об успешной оплате).
    @CardTest
    void shouldSuccessWithValidData(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

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
    void shouldGetErrorIfCardNumberIsEmpty(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        //---пустое значение номера карты
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorEmptyNumber();
    }

    //3. Отказ в покупке по карте: CARDNUMBER-DECLINED (заполнение формы невалидными данными (номер карты для отказа по ТЗ), появление сообщения об отказе).
    @CardTest
    void shouldGetErrorIfCardNumberIsDeclined(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getDeclinedCardNumber()); //номер карты для отказа по ТЗ
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        mainPage.hasErrorNotification();
    }

    //4. Отказ в покупке по карте: заполнение формы невалидными данными (невалидное рандомное значение номера карты), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfCardNumberIsInvalid(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.generateInvalidCardNumber(Faker.instance())); //невалидное значение номера карты
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        mainPage.hasErrorNotification();
    }

    //5. Отказ в покупке по карте: заполнение формы невалидными данными (короткий номер карты), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfCardNumberIsShort(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.generateShortCardNumber(Faker.instance())); //короткий номер карты
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorInvalidFormatNumber();
    }

    //6. Проверка поля CardNumber на возможность ввода спецсимволов, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfCardNumberHasSpecialSymbols(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.generateSpecialSymbols()); //спецсимволы в номере
        cardFormPage.hasEmptyNumberField();
    }

    //7. Проверка поля CardNumber на возможность ввода букв, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfCardNumberHasLetters(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.generateLetters()); //буквы в номере
        cardFormPage.hasEmptyNumberField();
    }

    //8. Отказ в покупке по карте: заполнение формы невалидными данными (пустое значение месяца), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfMonthIsEmpty(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        //---пустое значение месяца
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorEmptyMonth();
    }

    //9. Отказ в покупке по карте: заполнение формы невалидными данными (короткое значение месяца), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfMonthIsShort(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateShortMonth()); //короткое значение месяца
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorInvalidFormatMonth();
    }

    //10. Отказ в покупке по карте: заполнение формы невалидными данными (нулевой месяц), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfMonthIsZero(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.getZeroMonth()); //нулевой месяц
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorIncorrectMonth();
    }

    //11. Отказ в покупке по карте: заполнение формы невалидными данными (тринадцатый месяц), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfMonthIsThirteenth(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.getThirteenthMonth()); //тринадцатый месяц
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorIncorrectMonth();
    }

    //12. Отказ в покупке по карте: заполнение формы невалидными данными (прошлый месяц текущего года), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfMonthIsPast(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generatePastMonth()); //прошлый месяц
        cardFormPage.setYear(DataGenerator.getCurrentYear()); //текущий год
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorIncorrectMonth();
    }

    //13. Проверка поля Month на возможность ввода спецсимволов, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfMonthHasSpecialSymbols(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setMonth(DataGenerator.generateSpecialSymbols()); //спецсимволы в месяце
        cardFormPage.hasEmptyMonthField();
    }

    //14. Проверка поля Month на возможность ввода букв, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfMonthHasLetters(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setMonth(DataGenerator.generateLetters()); //буквы в месяце
        cardFormPage.hasEmptyMonthField();
    }

    //15. Отказ в покупке по карте: заполнение формы невалидными данными (пустое значение года), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfYeaIsEmpty(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateMonth());
        //---пустое значение года
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorEmptyYear();
    }

    //16. Отказ в покупке по карте: заполнение формы невалидными данными (короткое значение года), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfYearIsShort(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateShortYear()); //короткое значение года
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorInvalidFormatYear();
    }

    //17. Отказ в покупке по карте: заполнение формы невалидными данными (прошлый год), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfYeaIsPast(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generatePastYear()); //прошлый год
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorPastYear();
    }

    //18. Отказ в покупке по карте: заполнение формы невалидными данными (указан год более 5 лет), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfYearIsMoreFive(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateMoreFiveYear()); //указан год более 5 лет
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorIncorrectYear();
    }

    //19. Проверка поля Year на возможность ввода спецсимволов, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfYearHasSpecialSymbols(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setYear(DataGenerator.generateSpecialSymbols()); //спецсимволы в поле года
        cardFormPage.hasEmptyYearField();
    }

    //20. Проверка поля Year на возможность ввода букв, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfYearHasLetters(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setYear(DataGenerator.generateLetters()); //буквы в поле года
        cardFormPage.hasEmptyYearField();
    }

    //21. Отказ в покупке по карте: заполнение формы невалидными данными (пустое значение имени), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfNameIsEmpty(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateYear());
        //---пустое значение имени
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorEmptyName();
    }

    //22. Отказ в покупке по карте: заполнение формы невалидными данными (имя на русском языке), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfNameIsRu(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("ru")); //имя на русском языке
        cardFormPage.setCvc(DataGenerator.generateCvc());

        cardFormPage.submit();
        cardFormPage.hasErrorInvalidFormatName();
    }

    //23. Проверка поля Name на возможность ввода спецсимволов, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfNameHasSpecialSymbols(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setHolderName(DataGenerator.generateSpecialSymbols()); //спецсимволы в имени
        cardFormPage.hasEmptyNameField();
    }

    //24. Проверка поля Name на возможность ввода цифр, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfNameHasNumeric(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setHolderName(DataGenerator.generateNumericName(Faker.instance())); //цифры в имени
        cardFormPage.hasEmptyNameField();
    }

    //25. Отказ в покупке по карте: заполнение формы невалидными данными (пустое значение CVC), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfCvcIsEmpty(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        //---пустое значение CVC

        cardFormPage.submit();
        cardFormPage.hasErrorEmptyCvc();
    }

    //26. Отказ в покупке по карте: заполнение формы невалидными данными (двузначное число в CVC), появление сообщения об отказе.
    @CardTest
    void shouldGetErrorIfCvcIsShort(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCardNumber(DataGenerator.getApprovedCardNumber());
        cardFormPage.setMonth(DataGenerator.generateMonth());
        cardFormPage.setYear(DataGenerator.generateYear());
        cardFormPage.setHolderName(DataGenerator.generateName("en"));
        cardFormPage.setCvc(DataGenerator.generateTooFewCvc()); //двузначное число в CVC

        cardFormPage.submit();
        cardFormPage.hasErrorInvalidFormatCvc();
    }

    //27. Проверка поля Cvc на возможность ввода спецсимволов, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfCvcHasSpecialSymbols(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCvc(DataGenerator.generateSpecialSymbols()); //спецсимволы в CVC
        cardFormPage.hasEmptyCvcField();
    }

    //28. Проверка поля Cvc на возможность ввода букв, поле остается чистым.
    @CardTest
    void shouldBeEmptyIfCvcHasLetters(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        cardFormPage.setCvc(DataGenerator.generateLetters()); //буквы в CVC
        cardFormPage.hasEmptyCvcField();
    }

}
