package data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardInfo {
    private String cardNumber;
    private String month;
    private String year;
    private String cardHolder;
    private String cvc;
}
