import items.Seed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static String URL = "https://magiccircle.gg";
    public  static void main(String[] args) {
        Farmer f = new Farmer();
        int i = 0;
        while (true) {
            f.farm(0, 2, 21, 8);
            if (i == 10) {
                logger.info("Buying seeds...");
                f.buyAll(Seed.SUNFLOWER);
                f.buyAll(Seed.DRAGOON_FRUIT);
                f.buyAll(Seed.PASSION_FRUIT);
                f.buyAll(Seed.PEPPER);
                i = 0;
            } else {
                i++;
            }
        }
    }
}
