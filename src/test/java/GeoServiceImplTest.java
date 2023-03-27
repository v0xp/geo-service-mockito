import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class GeoServiceImplTest {
    GeoServiceImpl test = new GeoServiceImpl();

    @AfterAll
    public static void finish() {
        System.out.print("Все тесты завершены");
    }


    @ParameterizedTest
    @MethodSource("source")
    public void byIpTest(String ip, Location expected) {
        //given

        //when
        Location result = test.byIp(ip);

        //then
        Assertions.assertEquals(expected.getCity(), result.getCity());
        Assertions.assertEquals(expected.getCountry(), result.getCountry());
        Assertions.assertEquals(expected.getStreet(), result.getStreet());
        Assertions.assertEquals(expected.getBuiling(), result.getBuiling());

    }

    private static Stream<Arguments> source() {
        return Stream.of(
                Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.44.183.149", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.44.78.149", new Location("New York", Country.USA, null, 0)));
    }

}
