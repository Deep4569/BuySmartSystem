import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class BuySmartGUI extends JFrame {

    private JPanel Root;
    private JButton checkoutButton;
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
    private JButton signupButton;
    private Border blackLine, blackLineRight, blackLineLeft;

    private JPanel popup = new JPanel(new BorderLayout(5, 5));

    private String[] companies = {"Walmart", "Apple", "BestBuy", "Amazon", "Ebay", "Costco", "Rogers", "Bell"};
    private String[] possibleSearches = {"iPhone 3G", "iPhone 3GS", "iPhone 4", "iPhone 4S", "iPhone 5", "iPhone 5c", "iPhone 5s", "iPhone 6", "iPhone 6 Plus", "iPhone 6s", "iPhone 6s Plus", "iPhone SE",
            "iPhone 7", "iPhone 7 Plus", "iPhone 8", "iPhone 8 Plus", "iPhone X", "iPhone XR", "iPhone XS", "iPhone"};
    ArrayList<Item> cartList = new ArrayList<>();


    public BuySmartGUI() throws IOException {
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
        User user = new User();

        shoppingList.setLayout(new BoxLayout(shoppingList, BoxLayout.Y_AXIS));
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
                popup.removeAll();
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
                    addItemButton();
                    username.setText(null);
                    password.setText(null);
                } else {
                    JOptionPane.showMessageDialog(popup, "Login Failed. Try Again.");
                    username.setText(null);
                    password.setText(null);
                }
            }
        });

        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popup.removeAll();
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


        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popup.removeAll();
                JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
                label.add(new JLabel("Username"));
                label.add(new JLabel("Password"));
                label.add(new JLabel("Confirm Password"));
                popup.add(label, BorderLayout.WEST);

                JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
                JTextField username = new JTextField();
                controls.add(username);
                JPasswordField password = new JPasswordField();
                controls.add(password);
                JPasswordField password2 = new JPasswordField();
                controls.add(password2);
                popup.add(controls, BorderLayout.CENTER);
                JOptionPane.showConfirmDialog(Root, popup, "Signup", JOptionPane.OK_CANCEL_OPTION);
                String pass = new String(password.getPassword());
                String confirmPassword = new String(password2.getPassword());

                if (user.checkPassword(pass, confirmPassword)) {

                    try {
                        if (user.registerUser(username.getText(), pass)) {
                            JOptionPane.showMessageDialog(popup, "Signup Successful");
                        } else {
                            JOptionPane.showMessageDialog(popup, "Username Taken or your username contains Special Characters");
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(popup, "The two passwords do not match. Please try again.");
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

        shoppingList.removeAll();
        shoppingList.revalidate();

        for (Item item : cartList) {
            JPanel stuff = new JPanel();
            stuff.setLayout(new BorderLayout());
            JTextArea shoppingCart = new JTextArea(item.getName() + "\n");
            shoppingCart.append("(" + item.getSeller() + ")\n");
            shoppingCart.append("\n");
            JButton delete = new JButton("Delete");

            stuff.add(shoppingCart, BorderLayout.CENTER);
            stuff.add(delete, BorderLayout.EAST);
            shoppingList.add(stuff);
            total += item.getPrice();
        }

        price.setText(null);
        price.append("TOTAL: $" + total);
    }

    public void addItemButton() {
        JButton addItem = new JButton("Add Item");
        display.add(addItem, BorderLayout.NORTH);
        revalidate();
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        Root = new JPanel();
        Root.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        Root.setBackground(new Color(-1));
        Root.setBorder(BorderFactory.createTitledBorder("BuySmartSystem"));
        cart = new JPanel();
        cart.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        cart.setBackground(new Color(-1));
        cart.setForeground(new Color(-16777216));
        Root.add(cart, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-1));
        label1.setForeground(new Color(-16777216));
        label1.setHorizontalTextPosition(0);
        label1.setText("Shopping Cart");
        cart.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-1));
        panel1.setForeground(new Color(-1));
        cart.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        checkoutButton = new JButton();
        checkoutButton.setBackground(new Color(-1));
        checkoutButton.setForeground(new Color(-16777216));
        checkoutButton.setHideActionText(true);
        checkoutButton.setText("Checkout");
        panel1.add(checkoutButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        price = new JTextArea();
        price.setEditable(false);
        price.setEnabled(true);
        Font priceFont = this.$$$getFont$$$("Times New Roman", Font.BOLD, 24, price.getFont());
        if (priceFont != null) price.setFont(priceFont);
        price.setForeground(new Color(-16729343));
        panel3.add(price, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        shopList = new JScrollPane();
        panel2.add(shopList, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        shoppingList = new JPanel();
        shoppingList.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        shopList.setViewportView(shoppingList);
        search = new JPanel();
        search.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        search.setBackground(new Color(-1));
        Root.add(search, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        pic = new JPanel();
        pic.setLayout(new BorderLayout(0, 0));
        pic.setBackground(new Color(-1));
        pic.setEnabled(true);
        pic.setForeground(new Color(-1));
        search.add(pic, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        searchTextField = new JTextField();
        searchTextField.setBackground(new Color(-1));
        searchTextField.setEnabled(true);
        searchTextField.setForeground(new Color(-16777216));
        searchTextField.setText("Search");
        searchTextField.setToolTipText("Enter what you want to find here");
        search.add(searchTextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        supplierLoginButton = new JButton();
        supplierLoginButton.setBackground(new Color(-1));
        supplierLoginButton.setForeground(new Color(-16777216));
        supplierLoginButton.setText("Supplier Login");
        search.add(supplierLoginButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        adminLoginButton = new JButton();
        adminLoginButton.setBackground(new Color(-1));
        adminLoginButton.setForeground(new Color(-16777216));
        adminLoginButton.setText("Admin Login");
        search.add(adminLoginButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        signupButton = new JButton();
        signupButton.setText("Signup");
        search.add(signupButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        display = new JPanel();
        display.setLayout(new BorderLayout(0, 0));
        display.setBackground(new Color(-1));
        display.setForeground(new Color(-1));
        Root.add(display, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        yeet = new JScrollPane();
        yeet.setBackground(new Color(-1));
        yeet.setForeground(new Color(-1));
        display.add(yeet, BorderLayout.CENTER);
        Items = new JPanel();
        Items.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, 5, true, false));
        Items.setBackground(new Color(-1));
        Items.setForeground(new Color(-1));
        yeet.setViewportView(Items);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return Root;
    }

}
