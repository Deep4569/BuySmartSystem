import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Item {

    double price;
    String productNum, description, name, seller;

    public Item(double price, String des, String num, String name, String seller){
        this.price = price;
        this.description = des;
        this.productNum = num;
        this.name = name;
        this.seller = seller;
    }

    public Item(String name){ this.name = name; }

    public double getPrice(){
        return price;
    }

    public String getProductNum(){
        return productNum;
    }

    public String getDescription(){
        return description;
    }

    public String getName(){
        return name;
    }

    public String getSeller(){
        return seller;
    }

    public JLabel getImage() {
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(".\\pictures\\" + name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JLabel(new ImageIcon(myPicture));
    }
}
