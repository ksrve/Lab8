import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Registration extends JFrame {
    public static Locale currentLocale;
    public static int currentLocaleI;
    private JLabel welcomeLabel,emailLabel, loginLabel, passwordLabel,infoLabel;
    private JButton signUpButton, exitButton;
    private JComboBox<Locale> languageComboBox = new JComboBox<>();

    private Locale ruLocale = new Locale("ru","RU");
    private Locale slLocale = new Locale("sl","SL");
    private Locale frLocale = new Locale("fr","FR");
    private Locale esLocale = new Locale("es","ES");

    private ResourceBundle bundle;


    public Registration(Locale locale){
        if (currentLocale == null) {
            currentLocale = ruLocale;
            currentLocaleI = 1;
        }

        bundle = ResourceBundle.getBundle("Bundle", currentLocale);
        setTitle(bundle.getString("start_page"));


        int w = 600;
        int h = 400;
        this.setBounds(100, 100, w, h);
        super.getContentPane().setBackground(Color.white);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Font font = new Font("Times New Roman", Font.BOLD, 14);

        Box mainBox = Box.createVerticalBox();
        mainBox.add(Box.createVerticalGlue());

        welcomeLabel = new JLabel(bundle.getString("welcome") + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
        welcomeLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        mainBox.add(welcomeLabel);
        mainBox.add(Box.createVerticalStrut(20));

        JPanel loginPasswordPanel = new JPanel(new GridLayout(3,3,5,10));

        loginLabel = new JLabel(bundle.getString("login") + ":", SwingConstants.LEFT);
        loginLabel.setFont(font);
        passwordLabel = new JLabel(bundle.getString("password") + ":", SwingConstants.LEFT);
        passwordLabel.setFont(font);
        emailLabel = new JLabel(bundle.getString("email") + ":", SwingConstants.LEFT);
        emailLabel.setFont(font);

        JTextField emailInput = new JTextField("", SwingConstants.CENTER);
        JTextField loginInput = new JTextField("", SwingConstants.CENTER);
        JPasswordField passwordInput = new JPasswordField("", SwingConstants.CENTER);
        loginInput.setText("");
        passwordInput.setText("");

        loginPasswordPanel.add(emailLabel);
        loginPasswordPanel.add(emailInput);
        loginPasswordPanel.add(loginLabel);
        loginPasswordPanel.add(loginInput);
        loginPasswordPanel.add(passwordLabel);
        loginPasswordPanel.add(passwordInput);


        loginPasswordPanel.setMaximumSize(new Dimension(300,200));

        mainBox.add(loginPasswordPanel);
        mainBox.add(Box.createVerticalStrut(20));

        JLabel messageLabel = new JLabel("",SwingConstants.CENTER);
        messageLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        messageLabel.setFont(font);

        mainBox.add(messageLabel);
        mainBox.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel(new GridLayout(1,3,5,10));

        signUpButton = new JButton(bundle.getString("sign_up"));
        exitButton = new JButton(bundle.getString("back"));

        buttonPanel.add(signUpButton);
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


        signUpButton.setBackground(Color.PINK);
        signUpButton.addActionListener(actionEvent ->{
            try {
                String email = emailInput.getText();
                String login = loginInput.getText();
                String password = passwordInput.getText();
                String data = email + " " + login + " " + password;


                JFrame tokenRegistration = new TokenRegistration(locale);
                tokenRegistration.setVisible(true);
                super.setVisible(false);
            }catch (Exception ex){
                ex.printStackTrace();
            }


        });
        exitButton.setBackground(Color.PINK);
        exitButton.addActionListener(actionEvent -> {
            try {
                JFrame StartPage = new StartPage(locale);
                StartPage.setVisible(true);
                super.setVisible(false);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
        setContentPane(mainBox);
    }
    private void updateLanguage(Locale locale) {
        bundle = ResourceBundle.getBundle("Bundle", locale);
        welcomeLabel.setText(bundle.getString("welcome") + "!");
        loginLabel.setText(bundle.getString("login"));
        passwordLabel.setText(bundle.getString("password"));
        signUpButton.setText(bundle.getString("sign_up"));
        exitButton.setText(bundle.getString("back"));
        infoLabel.setText(bundle.getString("info"));
        setTitle(bundle.getString("start_page"));
    }

}
