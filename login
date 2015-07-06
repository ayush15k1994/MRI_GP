import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.itextpdf.awt.geom.Dimension;
import com.itextpdf.text.log.SysoLogger;

public class login extends JFrame {

	JMenuBar menubar = new JMenuBar();
	String username = "";
	String password = "";
	JFrame login = new JFrame();
	JLabel label = new JLabel();
	JLabel passwordLabel = new JLabel("<html><font color= 'blue'>Password");
	JLabel userLabel = new JLabel("<html><font color= 'blue'>User name");
	JTextField userText = new JTextField("");
	JPasswordField passwordText = new JPasswordField(20);
	JButton loginButton = new JButton("<html><font color= 'blue'>login");
	JButton registerButton = new JButton("<html><font color= 'blue'>register");
	String id = "";

	login() {
		super("psa");
		createMenuBar();
		login.setJMenuBar(menubar);
		// login.add(menubar) ;
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// validate
				try {

					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection(
							"jdbc:oracle:thin:@localhost:1521:proj", "yasso",
							"yasso");
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("select * from doctor ");

					if (rs != null)
						while (rs.next()) {

							if (username.equalsIgnoreCase(rs.getString(2))
									&& password.equalsIgnoreCase(rs
											.getString(3))) {
								report r = new report();
								r.doctor_name = username;
								
								try {
									PreparedStatement stmt2 = con
											.prepareStatement("select doctor_id from doctor where doctor_name = ? ");
									stmt2.setString(1, username);
									ResultSet rs2 = stmt2.executeQuery();
									if (rs2.next())
										id = rs2.getString(1);
									r.doctor_id = id;
									rs2.close();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								//
								close();
								afterLogin a = new afterLogin(username, r);
								//
							}
						}
					else {
						
						close();
						register r = new register();
					}

				} catch (SQLException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}

			}

		});

		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				register r = new register();
			}

		});

		userText.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				warn();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				warn();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				warn();
			}

			public void warn() {
				username = userText.getText();
				
			}
		});

		passwordText.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				warn();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				warn();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				warn();
			}

			public void warn() {
				password = passwordText.getText();
			
			}
		});

		label.setIcon(new ImageIcon("C:/Users/DreamOnline/Desktop/6.png"));
		userLabel.setBounds(100, 100, 80, 25);
		label.add(userLabel);
		userText.setBounds(200, 100, 160, 25);

		userLabel.setBackground(Color.white);
		userLabel.setBackground(Color.white);

		label.add(userText);
		passwordLabel.setBounds(100, 140, 80, 25);
		label.add(passwordLabel);
		passwordText.setBounds(200, 140, 160, 25);
		label.add(passwordText);
		loginButton.setBounds(200, 200, 80, 25);
		label.add(loginButton, BorderLayout.NORTH);
		registerButton.setBounds(300, 200, 80, 25);
		label.add(registerButton);
		login.setTitle("psa");
		login.add(label);
		login.setSize(600, 400);
		login.show();
		login.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

	private void createMenuBar() {

		ImageIcon iconAbout = new ImageIcon("open.png");
		ImageIcon iconExit = new ImageIcon("exit.png");
		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");
		JMenuItem exitMi = new JMenuItem("Exit", iconExit);
		JMenuItem aboutMi = new JMenuItem("About", iconAbout);
		exitMi.setToolTipText("Exit application");
		exitMi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		aboutMi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				JTextField textarea = new JTextField(10);
				JOptionPane.showMessageDialog(null,
						"welcome in our prostate cancer detection", "PCD",
						JOptionPane.PLAIN_MESSAGE);

			}
		});

		fileMenu.addSeparator();
		fileMenu.add(exitMi);
		helpMenu.add(aboutMi);
		menubar.add(fileMenu);
		menubar.add(helpMenu);

	}

	private void close() {
		login.setVisible(false);
	}
	private String getId (){
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			Connection con = DriverManager.getConnection(
//					"jdbc:oracle:thin:@localhost:1521:proj", "yasso",
//					"yasso");
//			PreparedStatement stmt2 = con
//					.prepareStatement("select doctor_id from doctor where doctor_name = '?' ");
//			stmt2.setString(1, username);
//			ResultSet rs2 = stmt2.executeQuery();
//			if (rs2.next())
//				id = rs2.getString(1);
//			r.doctor_id = id;
//			System.out.println("id = " + id);
//			rs2.close();
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
		return ""; 
	}

}
