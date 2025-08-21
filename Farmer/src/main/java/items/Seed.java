package items;

public enum Seed implements Purchasable {
    CARROT("Carrot Seed"),
    STRAWBERRY("Strawberry Seed"),
    ALOE("Aloe Seed"),
    BLUEBERRY("Blueberry Seed"),
    APPLE("Apple Seed"),
    TULIP("Tulip Seed"),
    TOMATO("Tomato Seed"),
    DAFFODIL("Daffodil Seed"),
    CORN("Corn Kernel"),
    WATERMELON("Watermelon Seed"),
    PUMPKIN("Pumpkin Seed"),
    ECHEVERIA("Echeveria Cutting"),
    COCONUT("Coconut Seed"),
    BANANA("Banana Seed"),
    LILY("Lily Seed"),
    BURROS_TAIL("Burro's Tail Cutting"),
    MUSHROOM("Mushroom Spore"),
    CACTUS("Cactus Seed"),
    BAMBOO("Bamboo Seed"),
    GRAPE("Grape Seed"),
    PEPPER("Pepper Seed"),
    LEMON("Lemon Seed"),
    PASSION_FRUIT("Passion Fruit Seed"),
    DRAGOON_FRUIT("Dragon Fruit Seed"),
    LYCHEE("Lychee Pit"),
    SUNFLOWER("Sunflower Seed"),
    STARWEAVER("Starweaver Pod");

    private String name;

    Seed(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
