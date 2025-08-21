import com.codeborne.selenide.*;
import items.Seed;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shops.SeedShop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class Farmer {
    private static final Logger logger = LoggerFactory.getLogger(Farmer.class);
    private Header header;
    private SeedShop seedShop = new SeedShop();

    Farmer() {
        open(Main.URL);
        loadCookie();
        if (header == null) header = new Header();
        if (!header.isLoggedIn()) {
            logger.info("Please login manually to continue.");
            header.login();
        }
        $("div.chakra-spinner").should(Condition.disappear, Duration.ofSeconds(10));
    }

    private void loadCookie() {
        try {
            FileInputStream fileInputStream
                    = new FileInputStream("cookie.txt");
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(fileInputStream);
            Cookie c = (Cookie) objectInputStream.readObject();
            objectInputStream.close();
            WebDriverRunner.getWebDriver().manage().addCookie(c);
            refresh();
            header = new Header();
            logger.info("Cookie loaded successfully.");
            header.waitForLogin();
        } catch (FileNotFoundException e) {
            logger.warn("no cookie.txt found");
        } catch (Exception e) {
            logger.error("Error reading cookie: ", e);
            new File("cookie.txt").delete();
        }
    }

    public void farm(Integer x, Integer y, Integer width, Integer height) {
        goTo(x, y);
        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                if(!clearField()) {
                    sell();
                    goTo(x + i, y + j);
                    clearField();
                }
                Selenide.sleep(100);
                if (i != width - 1) {
                    if (j % 2 == 0) {
                        go_right();
                    } else  {
                        go_left();
                    }
                }
            }
            go_down();
        }
        sell();
    }

    public boolean clearField() {
        int i = 10;
        while (!$$x("//button[contains(text(), 'Harvest')]").isEmpty() && i > 0) {
            actions().sendKeys(Keys.SPACE).perform();
            i--;
            Selenide.sleep(50);
        }
        return i > 0;
    }

    public void sell() {
        logger.info("selling items...");
        actions().keyDown(Keys.LEFT_SHIFT)
                .sendKeys("3")
                .keyUp(Keys.LEFT_SHIFT)
                .sendKeys(Keys.SPACE)
                .perform();
        SelenideElement warn = $("section[role='alertdialog']");
        if (warn.is(Condition.exist, Duration.ofSeconds(1))
                && warn.text().contains("You have crops that can be logged in your journal.")) {
            logger.info("going to journal");
            warn.$$("footer > button").get(0).click();
            actions().sendKeys(Keys.SPACE).perform();
            sell();
        }
    }

    public void go_right() {
        actions().sendKeys("d").perform();
    }

    public void go_left() {
        actions().sendKeys("a").perform();
    }

    public void go_up() {
        actions().sendKeys("w").perform();
    }

    public void go_down() {
        actions().sendKeys("s").perform();
    }

    public void reset() {
        actions().keyDown(Keys.LEFT_SHIFT)
                .sendKeys("2")
                .keyUp(Keys.LEFT_SHIFT)
                .perform();
    }

    public void goTo(Integer x, Integer y) {
        reset();
        for (int i = 0; i < 10 - y; i++) {
            go_up();
        }
        if (x < 10) {
            for (int i = 0; i < 10 - x; i++) {
                go_left();
            }
        } else {
            for (int i = 0; i < x - 10; i++) {
                go_right();
            }
        }
    }

    public void buyAll(Seed seed) {
        seedShop.buyAll(seed);
    }
}
