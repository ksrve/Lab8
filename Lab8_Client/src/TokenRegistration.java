import java.util.ResourceBundle;
import javax.swing.*;
import java.util.Locale;
import java.awt.*;


class TokenRegistration extends JFrame{
    public static Locale currentLocale;
    public static int currentLocaleI;
    public static String selectedLocation;
    private JLabel tokenLabel, infoLabel, lilTokenLabel;
    private JButton tokenButton, exitButton;
    private JComboBox<Locale> languageComboBox = new JComboBox<>();
    public static String token;

    private Locale ruLocale = new Locale("ru","RU");
    private Locale slLocale = new Locale("sl","SL");
    private Locale frLocale = new Locale("fr","FR");
    private Locale esLocale = new Locale("es","ES");

    private ResourceBundle bundle;



    public TokenRegistration(Locale locale) throws Exception {
        selectedLocation = "";

        if (currentLocale == null) {
            currentLocale = ruLocale;
            currentLocaleI = 1;
        }

        bundle = ResourceBundle.getBundle("Bundle", currentLocale);
        setTitle(bundle.getString("token_page"));


        int w = 600;
        int h = 400;
        this.setBounds(100, 100, w, h);
        super.getContentPane().setBackground(Color.white);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Font font = new Font("Times New Roman", Font.BOLD, 14);

        Box mainBox = Box.createVerticalBox();
        mainBox.add(Box.createVerticalGlue());

        tokenLabel = new JLabel(bundle.getString("token") + "!", SwingConstants.CENTER);
        tokenLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));


        mainBox.add(tokenLabel);
        mainBox.add(Box.createVerticalStrut(20));

        JPanel tokenPanel = new JPanel(new GridLayout(2,1,5,10));

        lilTokenLabel = new JLabel(bundle.getString("token") + ":", SwingConstants.LEFT);
        lilTokenLabel.setFont(font);

        JTextField tokenInput = new JTextField("", SwingConstants.CENTER);
        tokenInput.setText("");


        tokenPanel.add(lilTokenLabel);
        tokenPanel.add(tokenInput);



        tokenPanel.setMaximumSize(new Dimension(300,200));

        mainBox.add(tokenPanel);
        mainBox.add(Box.createVerticalStrut(20));

        JLabel messageLabel = new JLabel("",SwingConstants.CENTER);
        messageLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        messageLabel.setFont(font);

        mainBox.add(messageLabel);
        mainBox.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel(new GridLayout(1,2,5,10));

        tokenButton = new JButton(bundle.getString("token"));
        exitButton = new JButton(bundle.getString("exit"));


        buttonPanel.add(tokenButton);
        buttonPanel.add(exitButton);

        buttonPanel.setMinimumSize(new Dimension(350, 600));
        buttonPanel.setMaximumSize(new Dimension(500, 1000));

        mainBox.add(buttonPanel);
        mainBox.add(Box.createVerticalStrut(20));

        languageComboBox.addItem(ruLocale);
        languageComboBox.addItem(slLocale);
        languageComboBox.addItem(frLocale);
        languageComboBox.addItem(esLocale);
        languageComboBox.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        languageComboBox.setMaximumSize(new Dimension(100,20));
        languageComboBox.setSelectedItem(locale);

        languageComboBox.addActionListener(actionEvent -> updateLanguage(languageComboBox.getItemAt(languageComboBox.getSelectedIndex())));

        mainBox.add(languageComboBox);
        mainBox.add(Box.createVerticalStrut(20));

        infoLabel = new JLabel(bundle.getString("info"),SwingConstants.CENTER);
        infoLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        infoLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));

        mainBox.add(infoLabel);
        mainBox.add(Box.createVerticalGlue());

        tokenButton.setBackground(Color.PINK);
        tokenButton.addActionListener(actionEvent -> {
        });

        exitButton.setBackground(Color.PINK);
        exitButton.addActionListener(actionEvent -> System.exit(0));
        setContentPane(mainBox);
    }
    private void updateLanguage(Locale locale) {
        bundle = ResourceBundle.getBundle("Bundle", locale);
        tokenLabel.setText(bundle.getString("token") + "!");
        lilTokenLabel.setText(bundle.getString("token"));
        tokenButton.setText(bundle.getString("token"));
        exitButton.setText(bundle.getString("exit"));
        infoLabel.setText(bundle.getString("info"));
        setTitle(bundle.getString("token_page"));
    }


}
