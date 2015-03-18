import javax.swing.*;

import models.User;
import recommender.Recommender;
import session.SingletonSession;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;


public class LikeArtists extends JFrame {

	JLabel welcome = new JLabel("Hello, " + SingletonSession.getUserLogged().getName());
	JPanel panel = new JPanel();
	JTextField txartist = new JTextField(30);
	JButton bsearch = new JButton("Search");
	Recommender recommender = new Recommender();
	
	public static void main(String[] args) {
		LikeArtists frameTabel = new LikeArtists();
	}

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
		
		txartist.setBounds(180, 140, 120, 25);
		panel.add(txartist);
		
		bsearch.setBounds(219,170,80,23);
		panel.add(bsearch);
		

		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		actionSearchArtist();
	}
	

	public void actionSearchArtist(){
		bsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String artist = txartist.getText();
				System.out.println("ARTIST TEXT BOX: " + artist);
				//JComboBox<String> artistList = new JComboBox<String>();
				List<Artist> artists = recommender.findArtist(artist);
				
				//ArrayList<String> artistNames = new ArrayList<String>();
				//String[] artistsNames = new String[artists.size()];
				DefaultListModel<String> model = new DefaultListModel();
				int i = 0;
				for (Artist a : artists){
					try {
						//artistsNames[i] = a.getName();
						model.addElement(a.getName());
						//artistList.addItem(a.getName());
					} catch (EchoNestException e) {
						e.printStackTrace();
					}
				}
				JList<String> listbox = new JList<>(model);
				listbox.setBounds(10, 170, 80, 23);
				listbox.setVisible(true);
				//JList listbox = new JList( artistsNames );
				panel.add(listbox);
				//artistsList.setBounds(194, 300, 130, 20);
				//panel.add(artistList);
				getContentPane().add(panel);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setVisible(true);
				
				
				//JOptionPane.showMessageDialog(null,"Wrong Password / Username");
				//txuser.setText("");
				//pass.setText("");
				//txuser.requestFocus();
			}	
		});
	}

}
