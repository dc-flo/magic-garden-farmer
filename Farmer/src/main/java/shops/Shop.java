package shops;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import items.Purchasable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class Shop<T extends Enum<T> & Purchasable> {
    private static final Logger logger = LoggerFactory.getLogger(Shop.class);

    protected Class<T> clazz;
    protected Map<T,Integer> inventory = new HashMap<>();

    protected SelenideElement base;

    public abstract Boolean isOpen();
    public abstract void gotoShop();
    public Map<T, Integer> getInventory() {
        if (!isOpen()) {
            gotoShop();
        }
        base.$$("div.McFlex > div.McFlex > button")
            .stream().map(InventoryInfo::parse)
            .forEach(info -> inventory.put(Arrays.stream(clazz.getEnumConstants())
                    .filter( t -> t.getName().equals(info.getName())).findFirst().orElseThrow(), info.getCount()));
        return inventory;
    }
    public void close() {
        if (isOpen()) {
            base.$("button[aria-label='Close']").click();
        }
    }

    public void buy(T item, Integer count) {
        if (count <= 0) {
            return;
        }
        if (!isOpen()) {
            gotoShop();
        }
        SelenideElement button = base.$$("div.McFlex > div.McFlex > button")
                .stream().filter(b -> b.getText().contains(item.getName())).findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found: " + item.getName()));
        button.scrollIntoCenter();
        button.click();
        Selenide.sleep(300);
        SelenideElement buyButton = button.parent().$(":scope> div button", 0);
        buyButton.scrollIntoCenter();
        for (int i = 0; i < count; i++) {
            buyButton.click();
            Selenide.sleep(100);
        }
        button.click();
    }

    public void buyAll(T item) {
        getInventory();
        logger.info("{}: {}", item.getName(), inventory.get(item));
        buy(item, inventory.get(item));
    }
}
