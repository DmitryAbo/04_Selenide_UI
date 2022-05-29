package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CallbackTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @BeforeEach
    public void startBrowser() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldSuccess() {
        $x("//span/input[@placeholder = 'Город']").setValue("Брянск");
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").setValue(generateDate(3));
        $x("//span[@data-test-id= 'name']//child::input[@name= 'name']").setValue("Большой-Маленький Лебовски");
        $x("//span[@data-test-id= 'phone']//child::input[@name= 'phone']").setValue("+79155377834");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Забронировать\"]/ancestor::button").click();
        $x("//div[text() = 'Встреча успешно забронирована на ']/ancestor::div[@data-test-id=\"notification\"]").should(visible, Duration.ofSeconds(15));
        $x("//div[text() = \"" + generateDate(3) + "\"]/ancestor::div[@data-test-id=\"notification\"]").should(visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldFailInvalidDate() {
        $x("//span/input[@placeholder = 'Город']").setValue("Брянск");
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").setValue(generateDate(2));
        $x("//span[@data-test-id= 'name']//child::input[@name= 'name']").setValue("Большой-Маленький Лебовски");
        $x("//span[@data-test-id= 'phone']//child::input[@name= 'phone']").setValue("+79155377834");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Забронировать\"]/ancestor::button").click();
        $x("//span[@data-test-id=\"date\"]//span[@class = \"input__sub\"]").shouldHave(text("Заказ на выбранную дату невозможен"));

    }

    @Test
    public void shouldFailNoValidCity() {
        $x("//span/input[@placeholder = 'Город']").setValue("Париж");
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").setValue(generateDate(3));
        $x("//span[@data-test-id= 'name']//child::input[@name= 'name']").setValue("Большой-Маленький Лебовски");
        $x("//span[@data-test-id= 'phone']//child::input[@name= 'phone']").setValue("+79155377834");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Забронировать\"]/ancestor::button").click();
        $x("//span[@data-test-id=\"city\"]//span[@class = \"input__sub\"]").shouldHave(text("Доставка в выбранный город недоступна"));

    }

    @Test
    public void shouldFailNoValidNameAlphabet() {
        $x("//span/input[@placeholder = 'Город']").setValue("Брянск");
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").setValue(generateDate(3));
        $x("//span[@data-test-id= 'name']//child::input[@name= 'name']").setValue("Big-Маленький Лебовски");
        $x("//span[@data-test-id= 'phone']//child::input[@name= 'phone']").setValue("+79155377834");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Забронировать\"]/ancestor::button").click();
        $x("//span[@data-test-id=\"name\"]//span[@class = \"input__sub\"]").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldFailNoValidNameSymbol() {
        $x("//span/input[@placeholder = 'Город']").setValue("Брянск");
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").setValue(generateDate(3));
        $x("//span[@data-test-id= 'name']//child::input[@name= 'name']").setValue("Большой-Маленький Лебовски!");
        $x("//span[@data-test-id= 'phone']//child::input[@name= 'phone']").setValue("+79155377834");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Забронировать\"]/ancestor::button").click();
        $x("//span[@data-test-id=\"name\"]//span[@class = \"input__sub\"]").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void shouldFailNoValidPhoneManyNumbers() {
        $x("//span/input[@placeholder = 'Город']").setValue("Брянск");
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").setValue(generateDate(3));
        $x("//span[@data-test-id= 'name']//child::input[@name= 'name']").setValue("Большой-Маленький Лебовски");
        $x("//span[@data-test-id= 'phone']//child::input[@name= 'phone']").setValue("+79155377834000");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Забронировать\"]/ancestor::button").click();
        $x("//span[@data-test-id=\"phone\"]//span[@class = \"input__sub\"]").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    public void shouldFailNoValidPhonePlusNotFirst() {
        $x("//span/input[@placeholder = 'Город']").setValue("Брянск");
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").setValue(generateDate(3));
        $x("//span[@data-test-id= 'name']//child::input[@name= 'name']").setValue("Большой-Маленький Лебовски");
        $x("//span[@data-test-id= 'phone']//child::input[@name= 'phone']").setValue("791553+77834");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Забронировать\"]/ancestor::button").click();
        $x("//span[@data-test-id=\"phone\"]//span[@class = \"input__sub\"]").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldFailNoValidPhoneHaveLetters() {
        $x("//span/input[@placeholder = 'Город']").setValue("Брянск");
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").setValue(generateDate(3));
        $x("//span[@data-test-id= 'name']//child::input[@name= 'name']").setValue("Большой-Маленький Лебовски");
        $x("//span[@data-test-id= 'phone']//child::input[@name= 'phone']").setValue("+семь9155377834");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Забронировать\"]/ancestor::button").click();
        $x("//span[@data-test-id=\"phone\"]//span[@class = \"input__sub\"]").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    public void shouldFailNoValidNoAgreement() {
        $x("//span/input[@placeholder = 'Город']").setValue("Брянск");
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").setValue(generateDate(3));
        $x("//span[@data-test-id= 'name']//child::input[@name= 'name']").setValue("Большой-Маленький Лебовски");
        $x("//span[@data-test-id= 'phone']//child::input[@name= 'phone']").setValue("+79155377834");
        $x("//span[text()=\"Забронировать\"]/ancestor::button").click();
        Assertions.assertTrue($(By.cssSelector("[data-test-id=\"agreement\"]")).getCssValue("color").equals("rgba(255, 92, 92, 1)"));
    }

}
