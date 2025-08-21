package shops;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class InventoryInfo {
    private static final Logger logger = LoggerFactory.getLogger(InventoryInfo.class);

    public Integer count;
    public String name;
    public Integer price;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public static InventoryInfo parse(SelenideElement element) {
        InventoryInfo inventoryInfo = new InventoryInfo();

        //inventoryInfo.setPrice(Integer.valueOf(element.$("text").text().replace(".", "")));

        List<String> c = element.$$("div.McFlex > div.McGrid p")
                .stream().map(SelenideElement::text).filter(s -> !s.contains("EXCLUSIVE")).toList();
        inventoryInfo.setName(c.get(0));

        String countText = c.get(1);
        if (countText.contains("X")) {
            inventoryInfo.setCount(Integer.valueOf(countText.split("[\\s+X]")[1].trim()));
        } else {
            inventoryInfo.setCount(0);
        }

        String rarity = c.get(3);

        return inventoryInfo;
    }
}
