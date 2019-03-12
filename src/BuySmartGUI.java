import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class BuySmartGUI extends JFrame {

    private JPanel Root;
    private JButton checkoutButton;
    private JButton testButton;
    private JTextField searchTextField;
    private JList list1;
    private JButton supplierLoginButton;
    private JButton adminLoginButton;
    private JScrollPane yeet;
    private JPanel Items;
    private JPanel pic;
    private JPanel cart;
    private JPanel display;
    private JPanel search;
    private JTextArea price;
    private JScrollPane shopList;
    private JPanel shoppingList;
    private JTextArea shoppingCart;
    private Border blackLine, blackLineRight, blackLineLeft;

    JPanel popup = new JPanel(new BorderLayout(5, 5));

    public String[] companies = {"Walmart", "Apple", "BestBuy", "Amazon", "Ebay", "Costco", "Rogers", "Bell"};
    public String[] possibleSearches = {"iPhone 3G", "iPhone 3GS", "iPhone 4", "iPhone 4S", "iPhone 5", "iPhone 5c", "iPhone 5s", "iPhone 6", "iPhone 6 Plus", "iPhone 6s", "iPhone 6s Plus", "iPhone SE",
            "iPhone 7", "iPhone 7 Plus", "iPhone 8", "iPhone 8 Plus", "iPhone X", "iPhone XR", "iPhone XS", "iPhone"};
    ArrayList<Item> cartList = new ArrayList<>();


    public BuySmartGUI() {
        add(Root);
        Root.setBackground(Color.white);
        setSize(1000, 700);
        Items.setLayout(new BoxLayout(Items, BoxLayout.Y_AXIS));
        yeet.getVerticalScrollBar().setUnitIncrement(16);
        shopList.getVerticalScrollBar().setUnitIncrement(16);
        blackLine = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
        blackLineLeft = BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK);
        blackLineRight = BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK);
        Login login = new Login();

        search.setBorder((new CompoundBorder(blackLineRight, new EmptyBorder(0, 0, 0, 10))));
        cart.setBorder((new CompoundBorder(blackLineLeft, new EmptyBorder(0, 10, 0, 0))));

        searchTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchTextField.setText("");
            }
        });
        searchTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (Arrays.asList(possibleSearches).contains(searchTextField.getText())) {
                    pic.removeAll();
                    Items.removeAll();
                    Item x = new Item(searchTextField.getText());
                    pic.add(x.getImage(), BorderLayout.CENTER);

                    if (searchTextField.getText().equals("iPhone")) {
                        int count = 0;
                        for (int i = 0; i < companies.length; i++) {
                            for (int j = 0; j < possibleSearches.length - 1; j++) {
                                addItem(new Item(20.0, "Fuck Apple", i + "", possibleSearches[j], companies[i]), count++);
                            }
                        }
                    } else {

                        String product = searchTextField.getText();
                        for (int i = 0; i < companies.length; i++) {
                            addItem(new Item(20.0, "Fuck Apple", i + "", product, companies[i]), i);
                        }
                    }
                    yeet.getViewport().setViewPosition(new Point(0, 0));
                    revalidate();

                } else {
                    JOptionPane.showMessageDialog(popup, "Product Not Found");
                }
            }
        });


        supplierLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
                label.add(new JLabel("Username"));
                label.add(new JLabel("Password"));
                popup.add(label, BorderLayout.WEST);

                JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
                JTextField username = new JTextField();
                controls.add(username);
                JPasswordField password = new JPasswordField();
                controls.add(password);
                popup.add(controls, BorderLayout.CENTER);
                JOptionPane.showConfirmDialog(Root, popup, "login", JOptionPane.OK_CANCEL_OPTION);
                String pass = new String(password.getPassword());

                if (login.checkLogin(username.getText(), pass)) {
                    JOptionPane.showMessageDialog(popup, "Login Successful");
                } else {
                    JOptionPane.showMessageDialog(popup, "Login Failed. Try Again.");
                }
            }
        });

        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
                label.add(new JLabel("Username"));
                label.add(new JLabel("Password"));
                popup.add(label, BorderLayout.WEST);

                JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
                JTextField username = new JTextField();
                controls.add(username);
                JPasswordField password = new JPasswordField();
                controls.add(password);
                popup.add(controls, BorderLayout.CENTER);
                JOptionPane.showConfirmDialog(Root, popup, "login", JOptionPane.OK_CANCEL_OPTION);
                String pass = new String(password.getPassword());

                if (login.checkLogin(username.getText(), pass)) {
                    JOptionPane.showMessageDialog(popup, "Login Successful");
                } else {
                    JOptionPane.showMessageDialog(popup, "Login Failed. Try Again.");
                }
            }
        });
    }

    public void addItem(Item i, int num) {

        JPanel x = new JPanel();
        x.setLayout(new BorderLayout());
        JTextArea text = new JTextArea("Name: " + i.getName() + "\n");
        text.append("Seller: " + i.getSeller() + "\n");
        text.append("Item Description: " + i.getDescription() + "\n");
        text.append("Product #: " + i.getProductNum() + "\n");
        text.append("Price: $" + i.getPrice() + "");
        text.setBackground(num % 2 == 0 ? Color.white : Color.lightGray);
        x.add(text, BorderLayout.CENTER);
        JButton addToCart = new JButton("Add to Cart");

        addToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart(i);
                revalidate();
            }
        });

        x.add(addToCart, BorderLayout.EAST);
        x.setBorder(blackLine);

        Items.add(x);
        Items.add(Box.createHorizontalStrut(10));

    }

    public void addToCart(Item i) {
        int total = 0;
        cartList.add(i);
        shoppingCart.setText(null);
        for (Item item : cartList) {
            shoppingCart.append(item.getName() + "\n");
            shoppingCart.append("(" + item.getSeller() + ")\n");
            shoppingCart.append("\n");
            total += item.getPrice();
        }
        price.setText(null);
        price.append("TOTAL: $" + total);
    }
}
