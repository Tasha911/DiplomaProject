package annotations;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ParameterizedTest //включает поведение параметризации
@EnumSource(CardTest.Type.class)
public @interface CardTest {

    enum Type {
        PAY,
        CREDIT
    }

}
