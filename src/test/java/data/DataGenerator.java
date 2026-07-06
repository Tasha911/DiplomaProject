package data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    private DataGenerator() {
    }

    //создаем переменную CARDNUMBER-APPROVED - валидный номер карты для одобрения
    public static String getApprovedCardNumber() {
        return "4444 4444 4444 4441";
    }

    //создаем переменную CARDNUMBER-DECLINED - номер карты для отказа
    public static String getDeclinedCardNumber() {
        return "4444 4444 4444 4442";
    }

    //генерируем переменную invalidCARDNUMBER
    public static String generateInvalidCardNumber(Faker faker) {
        return faker.number().digits(16);
    }

    //генерируем переменную invalidCARDNUMBER-short
    public static String generateShortCardNumber(Faker faker) {
        return faker.number().digits(10);
    }

    //генерируем переменную MONTH c использованием класс Random, получаем месяц срока действия карты
    public static String generateMonth() {
        var randomMonth = new Random().nextInt(1, 12);
        return String.format("%02d", randomMonth);
    }

    //генерируем переменную MONTH-shor c использованием класс Random
    public static String generateShortMonth() {
        var randomMonth = new Random().nextInt(1, 9);
        return String.valueOf(randomMonth);
    }

    //создаем переменную invalidMONTH-00 (нулевой месяц)
    public static String getZeroMonth() {
        return "00";
    }

    //создаем переменную invalidMONTH-13 (тринадцатый месяц)
    public static String getThirteenthMonth() {
        return "13";
    }

    //генерируем переменную invalidMONTH-past (прошлый месяц *текущего года)
    public static String generatePastMonth() {
        return LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    //создаем переменную YEAR-current (текущий год)
    public static String getCurrentYear() {
        return String.format("%02d", LocalDate.now().getYear() % 100);
    }

    //генерируем переменную YEAR c использованием Random и LocalDate классов, получаем год срока действия карты
    public static String generateYear() {
        var shift = new Random().nextInt(1, 5);
        var year = (LocalDate.now().getYear() + shift) % 100;
        return String.format("%02d", year);
    }

    //генерируем переменную YEAR-shor (короткое значение года) c использованием класс Random
    public static String generateShortYear() {
        return String.valueOf(new Random().nextInt(1, 9));
    }

    //генерируем переменную invalidYEAR-past (прошлый года)
    public static String generatePastYear() {
        var shift = new Random().nextInt(1, 5);
        var year = (LocalDate.now().getYear() - shift) % 100;
        return String.format("%02d", year);
    }

    //генерируем переменную YEAR-moreFive (более пяти лет) c использованием Random и LocalDate классов
    public static String generateMoreFiveYear() {
        var shift = new Random().nextInt(6, 20);
        var year = (LocalDate.now().getYear() + shift) % 100;
        return String.format("%02d", year);
    }

    //генерируем переменную NAME
    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return (faker.name().lastName() + " " + faker.name().firstName())
                .replace("ё", "е")
                .replace("Ё", "Е");
    }

    //генерируем переменную invalidNAME-numeric (цифры, длина 10)
    public static String generateNumericName(Faker faker) {
        return faker.number().digits(10);
    }

    //генерируем переменную CVC/CVV (число от 100 до 999) и преобразуем его в строку
    public static String generateCvc() {
        return String.valueOf(new Random().nextInt(100, 999));
    }

    //генерируем переменную invalidCVC/CVV-tooFew (число от 0 до 99) и преобразуем его в строку
    public static String generateTooFewCvc() {
        return String.valueOf(new Random().nextInt(99));
    }

    //генерируем переменную SPECIAL_SYMBOLS (два спецсимвола)
    public static String generateSpecialSymbols() {
        Faker faker = new Faker();
        return faker.regexify("[!@#$%^&*()_+={}\\[\\]:;\"'<>,.?/~`|\\\\-]{2}");
    }

    //генерируем переменную LETTERS (две латинские буквы)
    public static String generateLetters() {
        Faker faker = new Faker();
        return faker.letterify("??");
    }

}
