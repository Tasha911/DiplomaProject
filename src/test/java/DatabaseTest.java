import annotations.CardTest;
import com.codeborne.selenide.Selenide;
import data.DataHelper;
import data.DatabaseHelper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import page.MainPage;

public class DatabaseTest {

    @BeforeEach
    void setUpEach() {
        Selenide.open("http://localhost:8080");
        DatabaseHelper.cleanDatabase();
    }

    @AfterEach
    void tearDown() {
        DatabaseHelper.cleanDatabase();
    }

    @SneakyThrows
    @CardTest
    void shouldSaveCorrectStatusInDbApproved(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        var validCard = DataHelper.getApprovedCardInformation();
        cardFormPage.makeOrder(validCard);
        Thread.sleep(15_000);

        validateDatabaseRecord(testType, "APPROVED");
    }

    @SneakyThrows
    @CardTest
    void shouldSaveCorrectStatusInDbDeclined(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        var validCard = DataHelper.getDeclinedCardInformation();
        cardFormPage.makeOrder(validCard);
        Thread.sleep(15_000);

        validateDatabaseRecord(testType, "DECLINED");
    }

    @SneakyThrows
    @CardTest
    void shouldNotSaveInDb(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        var validCard = DataHelper.getInvalidCardInformation();
        cardFormPage.makeOrder(validCard);
        Thread.sleep(15_000);

        Assertions.assertNull(DatabaseHelper.getLastOrderEntity());
        Assertions.assertNull(DatabaseHelper.getLastPaymentEntity());
        Assertions.assertNull(DatabaseHelper.getLastCreditRequestEntity());
    }

    void validateDatabaseRecord(CardTest.Type testType, String status) {
        var lastOrder = DatabaseHelper.getLastOrderEntity();
        Assertions.assertNotNull(lastOrder.getId());

        switch (testType) {
            case PAY -> {
                //если была обычная оплата картой, то:
                var lastPayment = DatabaseHelper.getLastPaymentEntity();
                Assertions.assertEquals(status, lastPayment.getStatus());
                Assertions.assertEquals(4_500_000, lastPayment.getAmount());
                //id платежа должно совпадать с payment_id в таблице заказов
                Assertions.assertEquals(lastPayment.getTransaction_id(), lastOrder.getPayment_id());
                //поле credit_id у обычного платежа обязано оставаться пустым (null)
                Assertions.assertNull(lastOrder.getCredit_id());

                Assertions.assertNull(DatabaseHelper.getLastCreditRequestEntity());
            }
            case CREDIT -> {
                //если была покупка в кредит, то
                var lastCredit = DatabaseHelper.getLastCreditRequestEntity();
                Assertions.assertEquals(status, lastCredit.getStatus());
                //id кредита должно совпадать с credit_id в таблице заказов
                Assertions.assertEquals(lastCredit.getBank_id(), lastOrder.getPayment_id());
                //поле credit_id у кредитного заказа обязано оставаться пустым (null)
                Assertions.assertNull(lastOrder.getCredit_id());

                Assertions.assertNull(DatabaseHelper.getLastPaymentEntity());
            }
        }
    }


    //проверка отсутствия сохранения данных карт: валидация того, что номера карт и CVC-коды не попадают ни в одну из таблиц СУБД
    @SneakyThrows
    @CardTest
    void shouldNotSaveSensitiveCardDataInDb(CardTest.Type testType) {
        var mainPage = new MainPage();
        var cardFormPage = mainPage.selectTab(testType); //автоматически выбирать нужную вкладку

        //заполняем карту валидными значениями с уникальными CardNumber и Cvc
        var validCard = DataHelper.getApprovedCardInformation();
        String targetCardNumber = validCard.getCardNumber().replaceAll("\\s+", ""); // убираем пробелы, если БД их вырезает
        String targetCvc = validCard.getCvc();

        cardFormPage.makeOrder(validCard);

        Thread.sleep(15_000);

        //извлекаем последние сущности из всех таблиц
        var lastOrder = DatabaseHelper.getLastOrderEntity();
        var lastPayment = DatabaseHelper.getLastPaymentEntity();
        var lastCredit = DatabaseHelper.getLastCreditRequestEntity();

        //валидация таблицы credit_request_entity (проверяем все текстовые поля на отсутствие данных карты)
        if (lastCredit != null) {
            Assertions.assertNotEquals(targetCardNumber, lastCredit.getId());
            Assertions.assertNotEquals(targetCardNumber, lastCredit.getBank_id());
            Assertions.assertNotEquals(targetCardNumber, lastCredit.getStatus());

            Assertions.assertNotEquals(targetCvc, lastCredit.getId());
            Assertions.assertNotEquals(targetCvc, lastCredit.getBank_id());
            Assertions.assertNotEquals(targetCvc, lastCredit.getStatus());
        }

        //валидация таблицы order_entity
        if (lastOrder != null) {
            Assertions.assertNotEquals(targetCardNumber, lastOrder.getId());
            Assertions.assertNotEquals(targetCardNumber, lastOrder.getCredit_id());
            Assertions.assertNotEquals(targetCardNumber, lastOrder.getPayment_id());

            Assertions.assertNotEquals(targetCvc, lastOrder.getId());
            Assertions.assertNotEquals(targetCvc, lastOrder.getCredit_id());
            Assertions.assertNotEquals(targetCvc, lastOrder.getPayment_id());
        }

        //валидация таблицы payment_entity
        if (lastPayment != null) {
            Assertions.assertNotEquals(targetCardNumber, lastPayment.getId());
            Assertions.assertNotEquals(targetCardNumber, lastPayment.getStatus());
            Assertions.assertNotEquals(targetCardNumber, lastPayment.getTransaction_id());

            Assertions.assertNotEquals(targetCvc, lastPayment.getId());
            Assertions.assertNotEquals(targetCvc, lastPayment.getStatus());
            Assertions.assertNotEquals(targetCvc, lastPayment.getTransaction_id());
        }
    }

}
