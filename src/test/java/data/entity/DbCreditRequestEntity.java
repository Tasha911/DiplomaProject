package data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class DbCreditRequestEntity {
    public String id;
    public String bank_id;
    public Timestamp created;
    public String status;
}
