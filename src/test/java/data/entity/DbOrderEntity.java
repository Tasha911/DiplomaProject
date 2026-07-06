package data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class DbOrderEntity {
    public String id;
    public Timestamp created;
    public String credit_id;
    public String payment_id;
}
