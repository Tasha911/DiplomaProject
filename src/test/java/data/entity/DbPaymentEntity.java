package data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class DbPaymentEntity {
    public String id;
    public int amount;
    public Timestamp created;
    public String status;
    public String transaction_id;
}
