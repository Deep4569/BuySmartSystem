import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BuySmartGUI extends JFrame {

    private JPanel Root;
    private JButton checkoutButton;
    private JTextField searchTextField;
    private JList list1;
    private JScrollPane displayScroll;
    private JPanel Items;
    private JPanel pic;
    private JPanel cart;
    private JPanel display;
    private JPanel search;
    private JTextArea price;
    private JScrollPane shopList;
    private JPanel shoppingList;
    private JComboBox<String> dropdown;
    private JPanel dropdownHolder;
    private JButton addItem;
    private Border blackLine;

    private JPanel popup = new JPanel(new BorderLayout(5, 5));

    private String[] companies = {"Walmart", "Apple", "BestBuy", "Amazon", "Ebay", "Costco", "Rogers", "Bell"};
    private String[] possibleSearches = {"iPhone 3G", "iPhone 3GS", "iPhone 4", "iPhone 4S", "iPhone 5", "iPhone 5c", "iPhone 5s", "iPhone 6", "iPhone 6 Plus", "iPhone 6s", "iPhone 6s Plus", "iPhone SE",
            "iPhone 7", "iPhone 7 Plus", "iPhone 8", "iPhone 8 Plus", "iPhone X", "iPhone XR", "iPhone XS", "iPhone"};
    private String[] possibleSearchesLower = {"iphone 3g", "iphone 3gs", "iphone 4", "iphone 4s", "iphone 5", "iphone 5c", "iphone 5s", "iphone 6", "iphone 6 plus", "iphone 6s", "iphone 6s plus", "iphone se",
            "iphone 7", "iphone 7 plus", "iphone 8", "iphone 8 plus", "iphone x", "iphone xr", "iphone xs", "iphone"};
    private Map<Item, Integer> cartList = new HashMap<Item, Integer>();

    private int total = 0;

    private Login login = new Login();
    private User user = new User();

    public BuySmartGUI() throws IOException {
        add(Root);
        Root.setBackground(Color.white);
        setSize(900, 750);
        Items.setLayout(new BoxLayout(Items, BoxLayout.Y_AXIS));
        displayScroll.getVerticalScrollBar().setUnitIncrement(16);
        shopList.getVerticalScrollBar().setUnitIncrement(16);
        blackLine = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
        Border blackLineLeft = BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK);
        Border blackLineRight = BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK);
        String[] options = {"Signup", "Login", "Supplier Login", "Admin Login"};
        dropdown.setModel(new DefaultComboBoxModel<>(options));
        dropdownHolder.setLayout(new BorderLayout());
        dropdownHolder.add(dropdown, BorderLayout.CENTER);

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

                if (Arrays.asList(possibleSearchesLower).contains(searchTextField.getText().toLowerCase().trim())) {
                    pic.removeAll();
                    pic.add(dropdownHolder, BorderLayout.SOUTH);
                    Items.removeAll();
                    Item x = new Item(possibleSearches[Arrays.asList(possibleSearchesLower).indexOf(searchTextField.getText().toLowerCase().trim())]);
                    pic.add(x.getImage(), BorderLayout.CENTER);

                    if (searchTextField.getText().toLowerCase().equals("iphone")) {
                        int count = 0;
                        for (int i = 0; i < companies.length; i++) {
                            for (int j = 0; j < possibleSearches.length - 1; j++) {
                                addItem(new Item(500.0, "iPhone by Apple", i + "", possibleSearches[j], companies[i]), count++);
                            }
                        }
                    } else {
                        String product = possibleSearches[Arrays.asList(possibleSearchesLower).indexOf(searchTextField.getText().toLowerCase().trim())];
                        for (int i = 0; i < companies.length; i++) {
                            addItem(new Item(500.0, "iPhone by Apple", Arrays.asList(possibleSearches).indexOf(product) + "" + i, product, companies[i]), i);
                        }
                    }
                    displayScroll.getViewport().setViewPosition(new Point(0, 0));
                    revalidate();

                } else {
                    JOptionPane.showMessageDialog(popup, "Product Not Found");
                }
            }
        });

        dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idx = dropdown.getSelectedIndex();
                popup.removeAll();
                JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
                label.add(new JLabel("Username"));
                label.add(new JLabel("Password"));
                JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
                JTextField username = new JTextField();
                controls.add(username);
                JPasswordField password = new JPasswordField();
                controls.add(password);

                if (idx == 0) {
                    label.add(new JLabel("Confirm Password"));
                    label.add(new JLabel("Address"));
                    label.add(new JLabel("Phone Number"));
                    popup.add(label, BorderLayout.WEST);
                    JPasswordField password2 = new JPasswordField();
                    controls.add(password2);

                    JTextField address = new JTextField();
                    controls.add(address);

                    MaskFormatter mask = null;
                    try {
                        mask = new MaskFormatter("###-###-####");
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    JFormattedTextField phone = new JFormattedTextField(mask);
                    controls.add(phone);

                    popup.add(controls, BorderLayout.CENTER);
                    int prompt = JOptionPane.showConfirmDialog(Root, popup, "Signup", JOptionPane.OK_CANCEL_OPTION);

                    if (prompt == 0) {
                        String pass = new String(password.getPassword());
                        String confirmPassword = new String(password2.getPassword());
                        if (user.checkPassword(pass, confirmPassword)) {
                            try {
                                if (user.registerUser(username.getText(), pass, address.getText(), phone.getText())) {
                                    JOptionPane.showMessageDialog(popup, "Signup Successful");
                                    user.currentUser = username.getText();
                                    JButton logout = new JButton("Logout");
                                    logout.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            dropdownHolder.removeAll();
                                            dropdownHolder.add(dropdown);
                                            display.remove(addItem);
                                            user.currentUser = null;
                                            revalidate();
                                        }
                                    });
                                    dropdownHolder.add(logout);
                                    dropdownHolder.remove(dropdown);
                                    addItemButton();
                                    revalidate();
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
                }
                //TODO: check for the different types of login if time permits
                else {
                    popup.add(label, BorderLayout.WEST);

                    popup.add(controls, BorderLayout.CENTER);
                    int prompt = JOptionPane.showConfirmDialog(Root, popup, "login", JOptionPane.OK_CANCEL_OPTION);
                    String pass = new String(password.getPassword());
                    if (prompt == 0) {
                        try {
                            if (login.checkLogin(username.getText(), pass)) {
                                JOptionPane.showMessageDialog(popup, "Login Successful");
                                addItemButton();
                                JButton logout = new JButton("Logout");
                                logout.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        dropdownHolder.removeAll();
                                        dropdownHolder.add(dropdown);
                                        display.remove(addItem);
                                        user.currentUser = null;
                                        revalidate();
                                    }
                                });
                                dropdownHolder.add(logout);
                                dropdownHolder.remove(dropdown);
                                user.currentUser = login.username;
                                revalidate();
                            } else {
                                JOptionPane.showMessageDialog(popup, "Login Failed. Try Again.");
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    }
                }
            }
        });

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (user.currentUser == null)
                    JOptionPane.showMessageDialog(popup, "Please Login/Register before checkout");
                else {
                    popup.removeAll();
                    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
                    label.add(new JLabel("Payment"));
                    label.add(new JLabel("Shipping Method"));
                    popup.add(label, BorderLayout.WEST);
                    JPanel controls = new JPanel(new GridLayout(2, 2, 2, 2));
                    ButtonGroup group1 = new ButtonGroup();
                    ButtonGroup group2 = new ButtonGroup();
                    JRadioButton b1 = new JRadioButton("Credit Card");
                    JRadioButton b2 = new JRadioButton("PayPal");
                    JRadioButton b3 = new JRadioButton("Standard");
                    JRadioButton b4 = new JRadioButton("Express");
                    group1.add(b1);
                    group1.add(b2);
                    group2.add(b3);
                    group2.add(b4);
                    controls.add(b1);
                    controls.add(b2);
                    controls.add(b3);
                    controls.add(b4);
                    popup.add(controls, BorderLayout.CENTER);
                    JOptionPane.showConfirmDialog(Root, popup, "Checkout", JOptionPane.OK_CANCEL_OPTION);
                }
            }
        });


    }

    private void addItem(Item i, int num) {

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
        total = 0;
        if (cartList.containsKey(i)) {
            int c = cartList.get(i);
            cartList.put(i, ++c);
        } else {
            cartList.put(i, 1);
        }
        shoppingList.removeAll();
        shoppingList.revalidate();

        for (Map.Entry<Item, Integer> entry : cartList.entrySet()) {
            JPanel stuff = new JPanel();
            stuff.setLayout(new BorderLayout());
            JTextArea shoppingCart = new JTextArea(entry.getKey().getName() + "\n");
            shoppingCart.append("(" + entry.getKey().getSeller() + ")\n");
            shoppingCart.append("\n");
            shoppingCart.append("Quantity: " + entry.getValue());
            shoppingCart.append("\n");
            shoppingCart.setDisabledTextColor(new Color(0, 0, 0));
            shoppingCart.setEnabled(false);
            shoppingCart.setBorder(blackLine);
            JButton delete = new JButton("Delete");

            shoppingCart.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    pic.removeAll();
                    pic.add(dropdownHolder, BorderLayout.SOUTH);
                    pic.add(entry.getKey().getImage(), BorderLayout.CENTER);
                    repaint();
                    revalidate();
                }
            });


            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (cartList.get(entry.getKey()) == 1) {
                        shoppingList.remove(stuff);
                        cartList.remove(entry.getKey());
                    } else {
                        cartList.put(entry.getKey(), entry.getValue() - 1);
                        shoppingCart.setText(null);
                        shoppingCart.append(entry.getKey().getName() + "\n(" + entry.getKey().getSeller() + ")\n");
                        shoppingCart.append("\n");
                        shoppingCart.append("Quantity: " + entry.getValue());
                        shoppingCart.append("\n");

                    }
                    total -= entry.getKey().getPrice();
                    price.setText(null);
                    price.append("TOTAL: $" + total);
                    revalidate();
                    repaint();
                }
            });
            stuff.add(shoppingCart, BorderLayout.CENTER);
            stuff.add(delete, BorderLayout.EAST);
            shoppingList.add(stuff);
            total += entry.getKey().getPrice() * entry.getValue();
        }

        price.setText(null);
        price.append("TOTAL: $" + total);
    }

    public void addItemButton() {
        addItem = new JButton("Edit Profile");
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popup.removeAll();
                JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
                label.add(new JLabel("Change"));
                JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
                JComboBox<String> choices = new JComboBox<String>(new String[]{"Username", "Password", "Address", "Phone Number"});
                controls.add(choices);
                popup.add(label, BorderLayout.WEST);
                popup.add(controls, BorderLayout.CENTER);
                int prompt = JOptionPane.showConfirmDialog(Root, popup, "Edit Profile", JOptionPane.OK_CANCEL_OPTION);

                if (prompt == 0) {
                    int idx = choices.getSelectedIndex();
                    popup.removeAll();
                    label.removeAll();
                    controls.removeAll();
                    label = new JPanel(new GridLayout(0, 1, 2, 2));
                    String[] x = {"Username", "Password", "Address", "Phone Number"};
                    label.add(new JLabel("New " + x[idx]));
                    controls = new JPanel(new GridLayout(0, 1, 2, 2));
                    if (idx == 0 || idx == 2) {
                        JTextField update = new JTextField();
                        controls.add(update);
                        popup.add(label, BorderLayout.WEST);
                        popup.add(controls, BorderLayout.CENTER);
                        int p = JOptionPane.showConfirmDialog(Root, popup, "New " + x[idx], JOptionPane.OK_CANCEL_OPTION);
                        if (p == 0) {
                            try {
                                if (idx == 0) {
                                    user.setUsername(update.getText());
                                } else {
                                    user.setAddress(update.getText());
                                }
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } else if (idx == 1) {
                        JPasswordField password = new JPasswordField();
                        controls.add(password);

                        popup.add(label, BorderLayout.WEST);
                        popup.add(controls, BorderLayout.CENTER);

                        int p = JOptionPane.showConfirmDialog(Root, popup, "New " + x[idx], JOptionPane.OK_CANCEL_OPTION);
                        if (p == 0) {
                            try {
                                user.setPassword(new String(password.getPassword()));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } else {
                        MaskFormatter mask = null;
                        try {
                            mask = new MaskFormatter("###-###-####");
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        JFormattedTextField phone = new JFormattedTextField(mask);
                        controls.add(phone);

                        popup.add(label, BorderLayout.WEST);
                        popup.add(controls, BorderLayout.CENTER);

                        int p = JOptionPane.showConfirmDialog(Root, popup, "New" + x[idx], JOptionPane.OK_CANCEL_OPTION);
                        if (p == 0) {
                            try {
                                user.setPhone(phone.getText());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
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
        search.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        search.setBackground(new Color(-1));
        Root.add(search, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        pic = new JPanel();
        pic.setLayout(new BorderLayout(0, 0));
        pic.setBackground(new Color(-1));
        pic.setEnabled(true);
        pic.setForeground(new Color(-1));
        search.add(pic, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        dropdownHolder = new JPanel();
        dropdownHolder.setLayout(new BorderLayout(0, 0));
        pic.add(dropdownHolder, BorderLayout.SOUTH);
        dropdown = new JComboBox();
        dropdownHolder.add(dropdown, BorderLayout.CENTER);
        searchTextField = new JTextField();
        searchTextField.setBackground(new Color(-1));
        searchTextField.setEnabled(true);
        searchTextField.setForeground(new Color(-16777216));
        searchTextField.setText("Search");
        searchTextField.setToolTipText("Enter what you want to find here");
        search.add(searchTextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        display = new JPanel();
        display.setLayout(new BorderLayout(0, 0));
        display.setBackground(new Color(-1));
        display.setForeground(new Color(-1));
        Root.add(display, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        displayScroll = new JScrollPane();
        displayScroll.setBackground(new Color(-1));
        displayScroll.setForeground(new Color(-1));
        display.add(displayScroll, BorderLayout.CENTER);
        Items = new JPanel();
        Items.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, 5, true, false));
        Items.setBackground(new Color(-1));
        Items.setForeground(new Color(-1));
        displayScroll.setViewportView(Items);
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
