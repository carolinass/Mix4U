import javax.swing.*;

import recommender.Recommender;
import session.SingletonSession;

import java.awt.*;
import java.awt.event.*;


public class LikeArtists extends JFrame {

	public static void main(String[] args) {
		LikeArtists frameTabel = new LikeArtists();
	}

	JLabel welcome = new JLabel("Hello, " + SingletonSession.getUserLogged().getName());
	JPanel panel = new JPanel();

	LikeArtists(){
		super("Welcome");
		JLabel status = new JLabel();  
		status.setBounds(0, -68, 0, 0);
		status.setSize(400, 200);
		status.setIcon(new ImageIcon("logo_menor.png"));
		panel.add(status);  
		setSize(800,500);
		setLocation(500,280);
		Color color = new Color(255, 255, 255);
		panel.setBackground(color);
		panel.setLayout (null); 

		welcome.setBounds(10,50,150,60);
		
		JLabel search_label = new JLabel("Search artist: ");
		search_label.setBounds(80, 120, 150, 60);
		panel.add(search_label);
		panel.add(welcome);
		
		JTextField txartist = new JTextField(15);
		txartist.setBounds(180, 140, 120, 25);
		panel.add(txartist);
		

		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}
