package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Selenide.open;

class CardDeliveryTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plun meeting")
    void shouldSuccessfulPlanMeeting() {
        DataGenerator.userInfo validUser = DataGenerator.Registration / generateUser("ru");
        int daysToAddForFirstMeeting = 4;
        String firstMeetingDate = DateGenerator.generateDate(daysToAddForFirstMeeting);
        int daysToAddForSecondMeeting = 7;
        String secondMeetingDate DataGenerator.generateDate(daysToAddForSecondMeeting);
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
                .shouldHave(Text("У вас уже запланирована встречча на другую дату. Перепланировать?"))
                .shouldBe(visible);
        $ "[data-test-id='replan-notification'dutton").click();
        $ "[data-test-id='success-notification'].notification__content")
.shouldHave(exactText("Встереча успешно запланирована на" + secondMeetingDate))
                .shouldBe(visible);
    }
}