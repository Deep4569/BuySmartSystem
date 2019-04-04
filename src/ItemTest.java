import junit.framework.TestCase;

public class ItemTest extends TestCase {

    Item item = new Item(250.0, "Smartphone", "5", "iPhone 5", "Costco");

    public void testGetPrice() {
        assertTrue(item.getPrice() == 250.0);
        assertFalse(item.getPrice() == 50.51);
    }

    public void testGetProductNum() {
        assertTrue(item.getProductNum().equals("5"));
        assertFalse(item.getProductNum().equals("51"));
    }

    public void testGetDescription() {
        assertTrue(item.getDescription().equals("Smartphone"));
        assertFalse(item.getDescription().equals("Smartphone made by LG"));
    }

    public void testGetName() {
        assertTrue(item.getName().equals("iPhone 5"));
        assertFalse(item.getName().equals("iPhone X"));
    }

    public void testGetSeller() {
        assertTrue(item.getSeller().equals("Costco"));
        assertFalse(item.getSeller().equals("Apple"));
    }

}