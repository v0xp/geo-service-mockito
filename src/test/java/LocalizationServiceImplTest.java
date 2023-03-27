import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

import static ru.netology.entity.Country.RUSSIA;
import static ru.netology.entity.Country.USA;

public class LocalizationServiceImplTest {
    LocalizationServiceImpl test = new LocalizationServiceImpl();

    @AfterAll
    public static void finish() {
        System.out.print("Все тесты завершены");
    }

    @ParameterizedTest
    @MethodSource("source")
    public void testLocal(Country country, String expected) {
        String result = test.locale(country);

        Assertions.assertEquals(result, expected);
    }

    public static Stream<Arguments> source() {
        return Stream.of(Arguments.of(RUSSIA, "Добро пожаловать"), Arguments.of(USA, "Welcome"));
    }
}