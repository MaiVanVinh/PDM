package general;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import java.awt.Font;
import java.awt.Color;

public class Menu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Menu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton restart = new JButton("Restart");
		restart.setBounds(62, 375, 132, 56);
		contentPane.add(restart);
		
		JButton continueButton = new JButton("Continue");
		continueButton.setBounds(285, 375, 132, 56);
		contentPane.add(continueButton);
		
		JLabel gamePause = new JLabel("Game Paused");
		gamePause.setFont(new Font("Tahoma", Font.PLAIN, 30));
		gamePause.setBounds(153, 11, 213, 43);
		contentPane.add(gamePause);
		
		JLabel music = new JLabel("Music");
		music.setFont(new Font("Tahoma", Font.PLAIN, 20));
		music.setBounds(37, 116, 62, 23);
		contentPane.add(music);
		
		JLabel sound = new JLabel("Sound");
		sound.setFont(new Font("Tahoma", Font.PLAIN, 20));
		sound.setBounds(37, 248, 62, 23);
		contentPane.add(sound);
		
		JSlider slider = new JSlider();
		slider.setBounds(135, 116, 200, 26);
		contentPane.add(slider);
		
		JSlider slider_1 = new JSlider();
		slider_1.setBounds(135, 248, 200, 26);
		contentPane.add(slider_1);
		
		JButton nextSong = new JButton("Next Song");
		nextSong.setBounds(374, 119, 100, 33);
		contentPane.add(nextSong);
	}

}
