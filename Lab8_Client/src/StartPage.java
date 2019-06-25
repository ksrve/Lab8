import java.io.Console;
import java.net.DatagramSocket;
import java.util.ResourceBundle;
import javax.swing.*;
import java.util.Locale;
import java.awt.*;
import java.util.Scanner;


class StartPage extends JFrame{
    public static Locale currentLocale;
    public static int currentLocaleI;
    public static String selectedLocation;
    private JLabel welcomeLabel, loginLabel, passwordLabel,infoLabel;
    private JButton signInButton, signUpButton, exitButton;
    private JComboBox<Locale> languageComboBox = new JComboBox<>();
    public static String token;

    private static Scanner scanner = new Scanner(System.in);
    private MessageSender sender;
    private MessageReceiver receiver;
    private PlannedReceiver plannedReceiver;
    private DatagramSocket socket;
    private DatagramSocket plannedSocket;
    private static String input_command;
    private static String input;
    private static String data;
    public static String input_login;
    public static String input_email;
    public static String input_password;
    Console console = System.console();
    public static char[] passwordArray;
    public static String email;
    public static String login;
    public static String password;

    private Locale ruLocale = new Locale("ru","RU");
    private Locale slLocale = new Locale("sl","SL");
    private Locale frLocale = new Locale("fr","FR");
    private Locale esLocale = new Locale("es","ES");

    private ResourceBundle bundle;



    public StartPage(Locale locale) throws Exception {
        selectedLocation = "";

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

        JPanel loginPasswordPanel = new JPanel(new GridLayout(2,2,5,10));

        loginLabel = new JLabel(bundle.getString("login") + ":", SwingConstants.LEFT);
        loginLabel.setFont(font);
        passwordLabel = new JLabel(bundle.getString("password") + ":", SwingConstants.LEFT);
        passwordLabel.setFont(font);

        JTextField loginInput = new JTextField("", SwingConstants.CENTER);
        JPasswordField passwordInput = new JPasswordField("", SwingConstants.CENTER);
        loginInput.setText("");
        passwordInput.setText("");

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

        signInButton = new JButton(bundle.getString("sign_in"));
        signUpButton = new JButton(bundle.getString("sign_up"));
        exitButton = new JButton(bundle.getString("exit"));

        buttonPanel.add(signInButton);
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

        signInButton.setBackground(Color.PINK);
        signInButton.addActionListener(actionEvent -> {
            String login = loginInput.getText();
            String password = passwordInput.getText();
            String data = login + " " + password;
            Client.data = data;

        });
        signUpButton.setBackground(Color.PINK);
        signUpButton.addActionListener(actionEvent ->{
            JFrame registration = new Registration(locale);
            registration.setVisible(true);
            super.setVisible(false);
        });
        exitButton.setBackground(Color.PINK);
        exitButton.addActionListener(actionEvent -> System.exit(0));
        setContentPane(mainBox);
    }
    private void updateLanguage(Locale locale) {
        bundle = ResourceBundle.getBundle("Bundle", locale);
        welcomeLabel.setText(bundle.getString("welcome") + "!");
        loginLabel.setText(bundle.getString("login"));
        passwordLabel.setText(bundle.getString("password"));
        signInButton.setText(bundle.getString("sign_in"));
        signUpButton.setText(bundle.getString("sign_up"));
        exitButton.setText(bundle.getString("exit"));
        infoLabel.setText(bundle.getString("info"));
        setTitle(bundle.getString("start_page"));
    }

    public boolean authorize(String command, String data) {
        String token;
        switch (command) {
            case "register":
                token = register(data);
                if (token != null) {
                    completeRegistration(data + " " + token);
                    return false;
                } else {
                    return false;
                }
            case "authorize":
                token = login(data);
                if (token != null) {
                    this.token = token;
                    return true;
                } else {
                    return false;
                }
            default:
                System.err.println("You enter incorrect command. Try again");
                System.out.println("---------------------------------------------");
                return false;
        }
    }

    private String register(String data)  {
        String[] dataArr = data.split(" ");
        if (dataArr.length != 3) {
            System.err.println("You made a mistake in syntax. Try again");
            return null;
        }
        System.out.println(data);
        sender.sendCommand("register", data);
        Response response = plannedReceiver.listenServer();
        if (response == null) {
            Client.startAgain();
        }
        System.out.println("Status " + response.getStatus());
        if (response.getStatus() == Status.OK) {
            String token = Response.getStringFromResponse(response.getResponse());
            System.out.println("\n" + "You received an 8-digit token" +
                    "\ncopy it to the line below to confirm registration" +
                    "\nBe careful, you have only 3 attempts and 3,5 minutes!!!");
            int attemptCounter = 0;
            while (attemptCounter < 3) {
                System.out.print("Token >>> ");
                if (scanner.nextLine().trim().equals(token)) {
                    return token;
                }
                attemptCounter++;
            }
        } else if (response.getStatus() == Status.USER_EXIST) {
            System.err.println("User with this email or login already exists. Try again");
        } else if (response.getStatus() == Status.NO_MAIL) {
            System.err.println("Invalid mail, the letter to complete the registration was not delivered. Try again");
        }
        return null;
    }

    private boolean completeRegistration(String data) {
        sender.sendCommand("AcceptRegister", data);
        Response response = plannedReceiver.listenServer();
        if (response == null) {
            Client.startAgain();
        }
        if (response.getStatus() == Status.OK) {
            System.out.println("You are registered on the server");
            return true;
        } else if (response.getStatus() == Status.WRONG_TOKEN) {
            System.err.println("Wrong token\n\n");
            return false;
        } else if (response.getStatus() == Status.EXPIRED_TOKEN) {
            System.err.println("Expired token\n\n");
            return false;
        }
        return false;
    }

    public String login(String data) {
        String[] dataArr = data.split(" ");
        if (dataArr.length != 2) {
            System.err.println("You made a mistake in syntax. Try again");
            return null;
        }
        // Получаем и обрабатываем запрос к серверу
        sender.sendCommand("login", data);
        Response response = plannedReceiver.listenServer();
        if (response == null) {
            Client.startAgain();
        }
        if (response.getStatus() == Status.OK) {
            return Response.getStringFromResponse(response.getResponse());
        } else if (response.getStatus() == Status.USER_IN_SYSTEM) {
            System.err.println("A user with this login is already in the system\n\n");
        } else if (response.getStatus() == Status.WRONG_PASSWORD) {
            System.err.println("Password is wrong\n\n");
        } else if (response.getStatus() == Status.USER_NOT_FOUND) {
            System.err.println("User with such login not found\n\n");
        }

        return null;
    }



}
