package test;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

class CardDeliveryTest {

    private WebDriver driver;


    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }



    @Test
    @DisplayName("Should successful plan meeting")
    void shouldSuccessfulPlanMeeting() {
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        int daysToAddForFirstMeeting = 4;
        String firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        int daysToAddForSecondMeeting = 7;
        String secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[date-test-id=city] input").setValue(validUser.getCity());
        $("[date-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[date-test-id=date] input").setValue(firstMeetingDate);
        $("[date-test-id=name] input").setValue(validUser.getName());
        $("[date-test-id=phone] input").setValue(validUser.getPhone());
        $("[date-test-id=agreement]").click();
        $(byText("Залпанировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'].notification__content")
                .shouldHave(exactText("Встреча успешно запланировна на" + firstMeetingDate))
                .shouldBe(visible);
        $("[date-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[date-test-id=date] input").setValue(secondMeetingDate);
        $(byText("Залпанировать")).click();
        $("[data-test-id='replan-notification'].notification__content")
                .shouldHave(text("У вас уже запланирована встречча на другую дату. Перепланировать?"))
                .shouldBe(visible);
        $("[data-test-id='replan-notification'] button").click();
        $("[data-test-id='success-notification'] .notification__content").shouldHave(exactText("Встеча успешно запланирована на " + secondMeetingDate)).shouldBe(visible);

    }
}