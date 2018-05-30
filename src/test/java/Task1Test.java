import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mariya
 */

    public class Task1Test {
        WebDriver driver;
        String baseUrl;

        @Before
        //1.  Перейти на страницу http://www.sberbank.ru/ru/person
        public void beforeTask(){
            System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
            baseUrl = "http://www.sberbank.ru/ru/person";
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driver.get(baseUrl);
        }

        @Test
        public void testInsurance(){
            Wait<WebDriver> wait = new WebDriverWait(driver, 40, 1000);

            //2.  Нажать на – Застраховать себя и имущество
            driver.findElement(By.xpath("//div[contains(@class,'header_more_nav')]//span[contains(text(),'Застраховать себя')]")).click();

            //3.  Выбрать – Страхование путешественников
            driver.findElement(By.xpath("//div[contains(@class,'header_more_nav')]//a[contains(text(),'Страхование путешественников')]")).click();

            //4.  Проверить наличие на странице заголовка – Страхование путешественников
            WebElement title = driver.findElement(By.xpath("//div[@class='sbrf-rich-outer']/*[contains(text(),'Страхование путешественников')]"));
            wait.until(ExpectedConditions.visibilityOf(title));
            assertEquals("Страхование путешественников", title.getText());

            //5.  Нажать на – Оформить Онлайн
            driver.findElement(By.xpath("//a//img[contains(@src,'banner-zashita-traveler')]")).click();

            for (String handle : driver.getWindowHandles()){ //Переключение экрана
                driver.switchTo().window(handle);
            }

            //6.  На вкладке – Выбор полиса  выбрать сумму страховой защиты – Минимальная
            driver.findElement(By.xpath("//div[contains(text(),'Минимальная')]")).click();

            //7.  Нажать Оформить
            driver.findElement(By.xpath("//span[contains(text(),'Оформить')]")).click();

            //8.  На вкладке Оформить заполнить поля:
            //    Фамилию и Имя, Дату рождения застрахованных
            fillField(By.name("insured0_surname"), "Smirnova");
            fillField(By.name("insured0_name"), "Anna");
            fillField(By.name("insured0_birthDate"), "12.11.1990");

            //    Данные страхователя: Фамилия, Имя, Отчество, Дата рождения, Пол
            fillField(By.name("surname"), "Смирнова");
            fillField(By.name("name"), "Олеся");
            fillField(By.name("middlename"), "Петровна");
            driver.findElement(By.name("birthDate")).click();
            fillField(By.name("birthDate"), "13.05.1987");
            driver.findElement(By.name("female")).click();

            //    Паспортные данные
            fillField(By.name("passport_series"), "5204");
            fillField(By.name("passport_number"), "424578");
            fillField(By.name("issueDate"), "02.06.2014");
            fillField(By.name("issuePlace"), "ОУФМС России по г. Новосибирск");

            //    Контактные данные не заполняем

            //9.  Проверить, что все поля заполнены правильно
            assertEquals("Smirnova", driver.findElement(By.name("insured0_surname")).getAttribute("value"));
            assertEquals("Anna", driver.findElement(By.name("insured0_name")).getAttribute("value"));
            assertEquals("12.11.1990", driver.findElement(By.name("insured0_birthDate")).getAttribute("value"));

            assertEquals("Смирнова", driver.findElement(By.name("surname")).getAttribute("value"));
            assertEquals("Олеся", driver.findElement(By.name("name")).getAttribute("value"));
            assertEquals("Петровна", driver.findElement(By.name("middlename")).getAttribute("value"));
            assertEquals("13.05.1987", driver.findElement(By.name("birthDate")).getAttribute("value"));
            assertEquals("1", driver.findElement(By.name("female")).getAttribute("value"));

            assertEquals("5204", driver.findElement(By.name("passport_series")).getAttribute("value"));
            assertEquals("424578", driver.findElement(By.name("passport_number")).getAttribute("value"));
            assertEquals("02.06.2014", driver.findElement(By.name("issueDate")).getAttribute("value"));
            assertEquals("ОУФМС России по г. Новосибирск", driver.findElement(By.name("issuePlace")).getAttribute("value"));

            //10. Нажать продолжить
            driver.findElement(By.xpath("//span[contains(text(),'Продолжить')]")).click();

            //11. Проверить, что появилось сообщение - Заполнены не все обязательные поля
            WebElement webElement = driver.findElement(By.xpath("//div[@ng-show='tryNext && myForm.$invalid']"));
            assertEquals("Заполнены не все обязательные поля", webElement.getText());
        }

        public void fillField(By locator, String value){
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(value);
        }

        @After
        public void afterTask(){
            driver.quit();
        }
    }
