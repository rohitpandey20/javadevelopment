import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

// Main class to run the application
public class OnlineExamApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.show();
        });
    }
}

// LoginScreen class
class LoginScreen extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private HashMap<String, String> users;
    private Timer timer;
    private int timeLeft;

    public LoginScreen() {
        // Initialize users with dummy data (In a real application, you should use a database)
        users = new HashMap<>();
        users.put("user1", "password1");
        users.put("user2", "password2");

        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        timeLeft = 30; // Time in seconds for login (you can change this as per your requirements)

        timer = new Timer(1000, e -> {
            timeLeft--;
            if (timeLeft == 0) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Time's up! Closing the application.");
                dispose();
            }
        });
        timer.start();

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");

        loginButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel);
    }

    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (users.containsKey(username) && users.get(username).equals(password)) {
            timer.stop(); // Stop the timer when the user logs in successfully
            ExamScreen examScreen = new ExamScreen(username);
            examScreen.show();
            dispose(); // Close the login screen
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.");
        }
    }
}

// ExamScreen class
class ExamScreen extends JFrame implements ActionListener {
    private String username;
    private int timeLeft;
    private Timer timer;
    private JLabel timerLabel;
    private JLabel questionLabel;
    private JRadioButton[] options;
    private JButton submitButton;

    private int currentQuestionIndex;
    private int correctAnswers;

    private String[] questions = {
        "What is the size of float and double in Java?",
        "What is the default value of the instance variables in Java?",
        "Who invented Java Programming?",
        "Which component is used to compile, debug and execute the java programs?",
        // Add more questions here
    };

    private String[][] choices = {
        {"32 and 64", "32 and 32", "64 and 64", "64 and 32"},
        {"0", "null", "undefined", "Depends on the type"},
        {"Guido van Rossum","James Gosling","Dennis Ritchie","Bjarne Stroustrup"},
        {"JRE","JIT","JDK","JVM"}
        // Add more choices here for each question
    };

    private int[] correctAnswersIndex = {0, 1,1,2};
    // Set the index of the correct answer for each question (0-based)

    public ExamScreen(String username) {
        this.username = username;
        timeLeft = 60; // Time in seconds (you can change this as per your requirements)

        setTitle("Online Exam");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Initialize components
        timerLabel = new JLabel("Time Left: " + timeLeft + " seconds");
        questionLabel = new JLabel(questions[0]);
        options = new JRadioButton[choices[0].length];
        for (int i = 0; i < choices[0].length; i++) {
            options[i] = new JRadioButton(choices[0][i]);
        }
        submitButton = new JButton("Submit");

        // Set action listeners for buttons
        submitButton.addActionListener(this);

        // Add components to the panel
        JPanel panel = new JPanel(new GridLayout(choices[0].length + 3, 1));
        panel.add(timerLabel);
        panel.add(questionLabel);
        ButtonGroup buttonGroup = new ButtonGroup();
        for (JRadioButton option : options) {
            panel.add(option);
            buttonGroup.add(option);
        }
        panel.add(submitButton);

        add(panel);

        // Start the timer
        timer = new Timer(1000, this);
        timer.start();
        updateQuestion();
    }

    // Method to update the displayed question
    private void updateQuestion() {
        if (currentQuestionIndex < questions.length) {
            questionLabel.setText(questions[currentQuestionIndex]);
            for (int i = 0; i < choices[currentQuestionIndex].length; i++) {
                options[i].setText(choices[currentQuestionIndex][i]);
            }
        } else {
            // Exam completed, show result
            timer.stop();
            showResult();
        }
    }

    // Method to show the exam result
    private void showResult() {
        JOptionPane.showMessageDialog(this, "Exam completed!\n" + "You scored: " + correctAnswers + "/" + questions.length);
        // You can add more functionality, like saving the result to a database, etc.
        // After showing the result, you can return to the login screen or close the application.
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.show();
        dispose(); // Close the exam screen
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            timeLeft--;
            if (timeLeft >= 0) {
                timerLabel.setText("Time Left: " + timeLeft + " seconds");
            } else {
                timer.stop();
                showResult();
            }
        } else if (e.getSource() == submitButton) {
            // Check the selected answer and update score
            if (currentQuestionIndex < questions.length) {
                int selectedAnswerIndex = -1;
                for (int i = 0; i < choices[currentQuestionIndex].length; i++) {
                    if (options[i].isSelected()) {
                        selectedAnswerIndex = i;
                        break;
                    }
                }

                if (selectedAnswerIndex == correctAnswersIndex[currentQuestionIndex]) {
                    correctAnswers++;
                }

                currentQuestionIndex++;
                updateQuestion();
            }
        }
    }
}
