package shops;

import items.Seed;
import org.openqa.selenium.Keys;


import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.actions;

public class SeedShop extends Shop<Seed> {
    public SeedShop() {
        base = $("#SeedShopModal");
        clazz = Seed.class;
    }

    @Override
    public Boolean isOpen() {
        return base.exists();
    }

    @Override
    public void gotoShop() {
        actions().keyDown(Keys.LEFT_SHIFT)
                .sendKeys("1")
                .keyUp(Keys.LEFT_SHIFT)
                .sendKeys(Keys.SPACE)
                .perform();
    }
}
