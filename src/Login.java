import javax.swing.*;

import models.User;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import session.SingletonSession;

public class Login extends JFrame {

	JButton blogin = new JButton("Login");
	JPanel panel = new JPanel();
	JTextField txuser = new JTextField(15);
	JPasswordField pass = new JPasswordField(15);

	Login(){
		super("Login to your Mix4U account");
		setSize(500,500);
		setLocation(500,280);
		Color color = new Color(255, 255, 255);
		panel.setBackground(color);
		panel.setLayout (null); 
		JLabel status = new JLabel();  
		status.setBounds(100, 60, 150, 20);
		status.setSize(400, 200);
		status.setIcon(new ImageIcon("logo.png"));  
		panel.add(status);  
		txuser.setBounds(160,210,150,20);
		pass.setBounds(160,235,150,20);
		blogin.setBounds(194,265,80,20);
		panel.add(blogin);
		panel.add(txuser);
		panel.add(pass);		

		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		actionlogin();
	}

	public void actionlogin(){
		blogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String puname = txuser.getText();
				String ppaswd = pass.getText();
				@SuppressWarnings("static-access")
				ArrayList<User> users = SingletonSession.getInstance().getUsers();
				if((users.size() > 0)){
					for (User user : users) {
						System.out.println(user);
						if(puname.equals(user.getLogin()) && ppaswd.equals(user.getPassword())) {
							SingletonSession.setUserLogged(user);
							LikeArtists regFace = new LikeArtists();
							regFace.setVisible(true);
							dispose();
						}else {
							JOptionPane.showMessageDialog(null,"Wrong Password / Username");
							txuser.setText("");
							pass.setText("");
							txuser.requestFocus();
						}	
					}
				}else {
					JOptionPane.showMessageDialog(null,"Wrong Password / Username");
					txuser.setText("");
					pass.setText("");
					txuser.requestFocus();
				}
			}
		});
	}
	
}
