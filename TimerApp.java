import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerApp {
    private static int timeLeft = 0; // Time remaining in seconds (always starts from 0)
    private static boolean isRunning = false;
    private static JLabel timeLabel;
    private static Timer timer;
    private static JButton startStopButton;
    private static JLabel progressLabel; // To show the current label as an image
    private static ImageIcon demonIcon, regularIcon, spiritIcon;

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Hunt Clock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        // Use BorderLayout for better control over positioning
        frame.setLayout(new BorderLayout());

        // Create the time label to display the countdown (starts at 00:00)
        timeLabel = new JLabel("00:00", JLabel.CENTER);
        Font currentFont = timeLabel.getFont();
        // Increase the font size by 300%
        timeLabel.setFont(new Font(currentFont.getName(), currentFont.getStyle(), (int) (currentFont.getSize() * 3.0)));
        frame.add(timeLabel, BorderLayout.CENTER); // Add label to center of the frame

        // Load images for "Demon", "Regular", and "Spirit"
        demonIcon = new ImageIcon("media/Demon.png"); // Ensure you have a demon.png file in your project directory
        regularIcon = new ImageIcon("media/Regular.png"); // Ensure you have a regular.png file
        spiritIcon = new ImageIcon("media/Spirit.png"); // Ensure you have a spirit.png file

        // Create a panel to hold the progress label (image)
        JPanel progressPanel = new JPanel();
        progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));

        // Create the progress label with the "Demon" image by default
        progressLabel = new JLabel(demonIcon);
        progressLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // This ensures horizontal centering
        progressPanel.add(Box.createVerticalStrut(10)); // Add some space between the time label and progress label
        progressPanel.add(progressLabel); // Add the image label to the panel under the time label

        // Add the progress panel to the frame
        frame.add(progressPanel, BorderLayout.NORTH);

        // Create a panel to hold the button and center it
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center the button inside the panel

        // Create a single button that toggles between Start and Stop
        startStopButton = new JButton("Start");
        startStopButton.setFont(new Font("Serif", Font.BOLD, 24)); // Increase font size
        startStopButton.setPreferredSize(new Dimension(200, 60)); // Set the button size (width, height)
        buttonPanel.add(startStopButton); // Add button to the panel

        // Add button panel to the bottom of the frame
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Button action to toggle between Start and Stop
        startStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    // Stop the timer
                    stopCountdown();
                    startStopButton.setText("Start"); // Change button text to "Start"
                } else {
                    // Start the timer from 0 seconds
                    timeLeft = 0; // Always start from 0
                    startCountdown();
                    startStopButton.setText("Stop"); // Change button text to "Stop"
                }
            }
        });

        // Create a timer object that updates the time every second
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft < 180) {  // Limit the time to 3 minutes (180 seconds)
                    timeLeft++;
                    updateTimeDisplay();
                    updateProgressLabel(); // Change the image based on time
                } else {
                    stopCountdown();
                    startStopButton.setText("Start"); // Reset the button text after time's up
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }

    // Start the countdown timer
    private static void startCountdown() {
        isRunning = true;
        timer.start(); // Start the timer
    }

    // Stop the countdown timer
    private static void stopCountdown() {
        isRunning = false;
        timer.stop(); // Stop the timer
    }

    // Update the time display on the label
    private static void updateTimeDisplay() {
        int minutes = timeLeft / 60;
        int seconds = timeLeft % 60;
        timeLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    // Update the progress label (change the image)
    private static void updateProgressLabel() {
        if (timeLeft < 30) {
            progressLabel.setIcon(demonIcon); // Show "Demon" image
        } else if (timeLeft < 180) {
            progressLabel.setIcon(regularIcon); // Show "Regular" image
        } else {
            progressLabel.setIcon(spiritIcon); // Show "Spirit" image
        }
    }
}
