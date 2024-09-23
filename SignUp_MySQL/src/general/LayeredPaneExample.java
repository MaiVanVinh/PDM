package general;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LayeredPaneExample {
    public static void main(String[] args) {
        // Create the JFrame
        JFrame frame = new JFrame("JLayeredPane Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Create a JPanel to hold everything
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);  // Use null layout to place components manually

        // Create the JLayeredPane with black background
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(50, 50, 300, 200);  // Set size and position of the pane
        layeredPane.setOpaque(true);
        layeredPane.setBackground(Color.BLACK);
        layeredPane.setVisible(false);  // Initially hidden

        // Create a JLabel and add it to the JLayeredPane as an example content
        JLabel label = new JLabel("This is the JLayeredPane", SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setBounds(0, 0, 300, 200);  // Set the size of the label
        layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);  // Add label to default layer

        // Create a JButton to show the JLayeredPane
        JButton showButton = new JButton("Show LayeredPane");
        showButton.setBounds(150, 300, 150, 30);  // Set position and size of the button

        // Add ActionListener to the button to toggle visibility of the JLayeredPane
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layeredPane.setVisible(!layeredPane.isVisible());  // Toggle visibility
            }
        });

        // Add components to the main panel
        mainPanel.add(layeredPane);
        mainPanel.add(showButton);

        // Add the main panel to the frame
        frame.add(mainPanel, BorderLayout.CENTER);

        // Set the frame visible
        frame.setVisible(true);
    }
}
