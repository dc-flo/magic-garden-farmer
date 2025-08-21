import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.Duration;
import java.util.Optional;

import static com.codeborne.selenide.Selenide.$;

public class Header {
    private static final Logger logger = LoggerFactory.getLogger(Header.class);
    private SelenideElement base;

    Header() {
        base = $("#SystemHeaderContainer").shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public Boolean isLoggedIn() {
        return base.$$("button").stream()
                .noneMatch(b ->  b.getAttribute("textContent").contains("Log in"));
    }

    public void waitForLogin() {
        Optional<SelenideElement> e = base.$$("button").stream()
                .filter(b ->  b.getAttribute("textContent").contains("Log in")).findFirst();
        e.ifPresent(el -> el.should(Condition.disappear, Duration.ofSeconds(10)));
    }

    public void login() {
        base.$("button > img").click();
        WebDriver driver = WebDriverRunner.getWebDriver();
        while (!driver.getCurrentUrl().contains("discord.com")) {}
        while (driver.getCurrentUrl().contains("oauth")) {}
        Selenide.sleep(3000);
        Cookie c = driver.manage().getCookieNamed("mc_jwt");
        saveCookie(c);
    }

    private void saveCookie(Cookie cookie) {
        try {
            FileOutputStream fileOutputStream
                    = new FileOutputStream("cookie.txt");
            ObjectOutputStream objectOutputStream
                    = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(cookie);
            objectOutputStream.flush();
            objectOutputStream.close();
            logger.info("Your file has been written to cookie.txt.");
        } catch (Exception e) {
            logger.error("Error saving cookie: ", e);
        }
    }
}
