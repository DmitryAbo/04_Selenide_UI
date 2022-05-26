package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CallbackTest {

    Date validDate = new Date();
    Date invalidDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.YYYY");
    String validDateFormated;
    String invalidDateFormated;


    @BeforeEach
    public void startBrowser() {
        open("http://localhost:9999/");
        validDate.setDate(validDate.getDate() + 3);
        invalidDate.setDate(invalidDate.getDate() + 2);
        validDateFormated = formatter.format(validDate);
        invalidDateFormated = formatter.format(invalidDate);
    }

    @Test
    public void shouldSuccess() {
        $x("//span/input[@placeholder = 'Город']").setValue("Брянск");
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").setValue(validDateFormated);
        $x("//span[@data-test-id= 'name']//child::input[@name= 'name']").setValue("Большой-Маленький Лебовски");
        $x("//span[@data-test-id= 'phone']//child::input[@name= 'phone']").setValue("+79155377834");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Забронировать\"]/ancestor::button").click();
        $x("//div[@data-test-id=\"notification\"]").should(visible, Duration.ofSeconds(15));

    }

    @Test
    public void shouldFailInvalidDate() {
        $x("//span/input[@placeholder = 'Город']").setValue("Брянск");
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").setValue(invalidDateFormated);
        $x("//span[@data-test-id= 'name']//child::input[@name= 'name']").setValue("Большой-Маленький Лебовски");
        $x("//span[@data-test-id= 'phone']//child::input[@name= 'phone']").setValue("+79155377834");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Забронировать\"]/ancestor::button").click();
        sleep(15000L);
        $x("//div[@data-test-id=\"notification\"]").should(hidden);

    }

    @Test
    public void shouldFailNoValidCity() {
        $x("//span/input[@placeholder = 'Город']").setValue("Париж");
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").setValue(validDateFormated);
        $x("//span[@data-test-id= 'name']//child::input[@name= 'name']").setValue("Большой-Маленький Лебовски");
        $x("//span[@data-test-id= 'phone']//child::input[@name= 'phone']").setValue("+79155377834");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Забронировать\"]/ancestor::button").click();
        sleep(15000L);
        $x("//div[@data-test-id=\"notification\"]").should(hidden);

    }

    @Test
    public void shouldFailNoValidNameAlphabet() {
        $x("//span/input[@placeholder = 'Город']").setValue("Брянск");
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").setValue(validDateFormated);
        $x("//span[@data-test-id= 'name']//child::input[@name= 'name']").setValue("Big-Маленький Лебовски");
        $x("//span[@data-test-id= 'phone']//child::input[@name= 'phone']").setValue("+79155377834");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Забронировать\"]/ancestor::button").click();
        sleep(15000L);
        $x("//div[@data-test-id=\"notification\"]").should(hidden);

    }

    @Test
    public void shouldFailNoValidNameSymbol() {
        $x("//span/input[@placeholder = 'Город']").setValue("Брянск");
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").setValue(validDateFormated);
        $x("//span[@data-test-id= 'name']//child::input[@name= 'name']").setValue("Большой-Маленький Лебовски!");
        $x("//span[@data-test-id= 'phone']//child::input[@name= 'phone']").setValue("+79155377834");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Забронировать\"]/ancestor::button").click();
        sleep(15000L);
        $x("//div[@data-test-id=\"notification\"]").should(hidden);

    }

    @Test
    public void shouldFailNoValidPhoneManyNumbers() {
        $x("//span/input[@placeholder = 'Город']").setValue("Брянск");
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").setValue(validDateFormated);
        $x("//span[@data-test-id= 'name']//child::input[@name= 'name']").setValue("Большой-Маленький Лебовски");
        $x("//span[@data-test-id= 'phone']//child::input[@name= 'phone']").setValue("+79155377834000");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Забронировать\"]/ancestor::button").click();
        sleep(15000L);
        $x("//div[@data-test-id=\"notification\"]").should(hidden);

    }

    @Test
    public void shouldFailNoValidPhonePlusNotFirst() {
        $x("//span/input[@placeholder = 'Город']").setValue("Брянск");
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").setValue(validDateFormated);
        $x("//span[@data-test-id= 'name']//child::input[@name= 'name']").setValue("Большой-Маленький Лебовски");
        $x("//span[@data-test-id= 'phone']//child::input[@name= 'phone']").setValue("791553+77834");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Забронировать\"]/ancestor::button").click();
        sleep(15000L);
        $x("//div[@data-test-id=\"notification\"]").should(hidden);

    }

    @Test
    public void shouldFailNoValidPhoneHaveLetters() {
        $x("//span/input[@placeholder = 'Город']").setValue("Брянск");
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").setValue(validDateFormated);
        $x("//span[@data-test-id= 'name']//child::input[@name= 'name']").setValue("Большой-Маленький Лебовски");
        $x("//span[@data-test-id= 'phone']//child::input[@name= 'phone']").setValue("+семь9155377834");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Забронировать\"]/ancestor::button").click();
        sleep(15000L);
        $x("//div[@data-test-id=\"notification\"]").should(hidden);

    }

    @Test
    public void shouldFailNoValidNoAgreement() {
        $x("//span/input[@placeholder = 'Город']").setValue("Брянск");
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id= 'date']//child::input[@placeholder= 'Дата встречи']").setValue(validDateFormated);
        $x("//span[@data-test-id= 'name']//child::input[@name= 'name']").setValue("Большой-Маленький Лебовски");
        $x("//span[@data-test-id= 'phone']//child::input[@name= 'phone']").setValue("+79155377834");
        $x("//span[text()=\"Забронировать\"]/ancestor::button").click();
        sleep(15000L);
        $x("//div[@data-test-id=\"notification\"]").should(hidden);

    }

}
