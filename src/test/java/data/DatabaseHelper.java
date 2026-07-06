package data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import data.entity.DbCreditRequestEntity;
import data.entity.DbOrderEntity;
import data.entity.DbPaymentEntity;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import javax.sql.DataSource;

public class DatabaseHelper {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/app?allowMultiQueries=true";
//    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/app";
    private static final String DATABASE_USER = "app";
    private static final String DATABASE_PASSWORD = "pass";

    private static final QueryRunner queryRunner = new QueryRunner(initDataSource());

    @SneakyThrows
    public static QueryRunner getQueryRunner() {
        return queryRunner;
    }

    //очищает таблицы
    @SneakyThrows
    public static void cleanDatabase() {
        var query = """
                DELETE FROM order_entity;
                DELETE FROM payment_entity;
                DELETE FROM credit_request_entity;
                """;
        queryRunner.update(query);
    }

    //возвращает последнюю запись из таблицы заказов
    @SneakyThrows
    public static DbOrderEntity getLastOrderEntity() {
        var selectSQL = "SELECT * FROM order_entity ORDER BY created DESC LIMIT 1;";
        return queryRunner.query(selectSQL, new BeanHandler<>(DbOrderEntity.class));
    }

    //возвращает последнюю запись из таблицы обычных платежей
    @SneakyThrows
    public static DbPaymentEntity getLastPaymentEntity() {
        var selectSQL = "SELECT * FROM payment_entity ORDER BY created DESC LIMIT 1;";
        return queryRunner.query(selectSQL, new BeanHandler<>(DbPaymentEntity.class));
    }

    //возвращает последнюю запись из таблицы кредитных запросов
    @SneakyThrows
    public static DbCreditRequestEntity getLastCreditRequestEntity() {
        var selectSQL = "SELECT * FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        return queryRunner.query(selectSQL, new BeanHandler<>(DbCreditRequestEntity.class));
    }

    @SneakyThrows
    private static DataSource initDataSource() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(DATABASE_URL);
        config.setUsername(DATABASE_USER);
        config.setPassword(DATABASE_PASSWORD);

        return new HikariDataSource(config);
    }

}
