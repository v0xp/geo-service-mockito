import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSenderImpl;
import org.mockito.Mockito;

import java.util.*;
import java.util.stream.Stream;

public class MessageSenderTests {

    static MessageSenderImpl test;

    @BeforeAll

    public static void initTest() {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.startsWith("172.")))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(geoService.byIp(Mockito.startsWith("96.")))
                .thenReturn(new Location("New York", Country.USA, null, 0));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Wellcome");

        test = new MessageSenderImpl(geoService, localizationService);
    }

    @AfterAll
    public static void finish() {
        System.out.print("Все тесты завершены");
    }

    @ParameterizedTest
    @MethodSource("source")
    public void sendTest(String ip, String expected) {

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String result = test.send(headers);

        Assertions.assertEquals(expected, result);

    }

    private static Stream<Arguments> source() {
        return Stream.of(Arguments.of("172.0.32.11", "Добро пожаловать"), Arguments.of("96.0.32.11", "Wellcome"),
                Arguments.of("172.5.50.66", "Добро пожаловать"), Arguments.of("96.4.02.65", "Wellcome"));
    }

}