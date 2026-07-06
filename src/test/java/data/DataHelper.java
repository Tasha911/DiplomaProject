package data;

import data.entity.CardInfo;

public class DataHelper {

    //возвращает тестовые данные валидной карты
    public static CardInfo getApprovedCardInformation() {
        return getCardInformation("4444 4444 4444 4441");
    }

    public static CardInfo getDeclinedCardInformation() {
        return getCardInformation("4444 4444 4444 4442");
    }

    public static CardInfo getInvalidCardInformation() {
        return getCardInformation("4444 4444 4444 4443");
    }

    private static CardInfo getCardInformation(String cardNumber) {
        var card = new CardInfo();
        card.setCardNumber(cardNumber);
        card.setMonth(DataGenerator.generateMonth());
        card.setYear(DataGenerator.generateYear());
        card.setCardHolder(DataGenerator.generateName("en"));
        card.setCvc(DataGenerator.generateCvc());

        return card;
    }

}
