package hotel;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.sql.*;

public class loginPage {
	public JFrame frame;
	public JTextField tfusername;
	public JPasswordField tfpwd;
	public JLabel Ustar = new JLabel("*");
	public JLabel Pstar = new JLabel("*");
	public JButton btnLogin;
	JButton btnCancel;

	private GetConnection connect;

	public loginPage(GetConnection connect) {
		this.connect = connect;
		initialize();
		Ustar.setVisible(false);
		Pstar.setVisible(false);
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Times New Roman", Font.BOLD, 23));
		frame.setBounds(50, 50, 898, 624);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblLogin = new JLabel("LOGIN ");
		lblLogin.setFont(new Font("Times New Roman", Font.BOLD, 32));
		lblLogin.setBounds(369, 190, 212, 67);
		frame.getContentPane().add(lblLogin);

		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 23));
		lblUsername.setBounds(269, 324, 155, 50);
		frame.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 23));
		lblPassword.setBounds(280, 413, 133, 38);
		frame.getContentPane().add(lblPassword);

		tfusername = new JTextField();
		tfusername.setFont(new Font("Times New Roman", Font.BOLD, 23));
		tfusername.setBounds(489, 331, 208, 38);
		tfusername.setName("tfusername");  // Set name for AssertJ to access
		frame.getContentPane().add(tfusername);

		tfpwd = new JPasswordField();
		tfpwd.setFont(new Font("Times New Roman", Font.BOLD, 23));
		tfpwd.setBounds(489, 414, 208, 38);
		tfpwd.setName("tfpwd");  // Set name for AssertJ to access
		frame.getContentPane().add(tfpwd);

		btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ustar.setVisible(tfusername.getText().isEmpty());
				Pstar.setVisible(String.valueOf(tfpwd.getPassword()).isEmpty());

				if (!Ustar.isVisible() && !Pstar.isVisible()) {
					try (Connection conn = connect.getConnection()) {
						PreparedStatement ps = conn.prepareStatement(
								"SELECT * FROM login WHERE username = ? AND password = ?"
						);
						ps.setString(1, tfusername.getText());
						ps.setString(2, String.valueOf(tfpwd.getPassword()));

						ResultSet rs = ps.executeQuery();
						if (rs.next()) {
							JOptionPane.showMessageDialog(frame, "Login Successful!");
							frame.setVisible(false);
						} else {
							JOptionPane.showMessageDialog(frame, "Invalid username or password",
									"Login Error", JOptionPane.ERROR_MESSAGE);
						}
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		btnLogin.setFont(new Font("Times New Roman", Font.BOLD, 23));
		btnLogin.setBounds(288, 513, 147, 50);
		btnLogin.setName("btnLogin");  // Set name for AssertJ to access
		frame.getContentPane().add(btnLogin);

		btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(e -> System.exit(0));
		btnCancel.setFont(new Font("Times New Roman", Font.BOLD, 23));
		btnCancel.setBounds(565, 513, 155, 51);
		btnCancel.setName("btnCancel");  // Set name for AssertJ to access
		frame.getContentPane().add(btnCancel);

		Ustar.setForeground(Color.RED);
		Ustar.setFont(new Font("Times New Roman", Font.BOLD, 28));
		Ustar.setBounds(699, 347, 46, 21);
		Ustar.setName("Ustar");  // Set name for AssertJ to access
		frame.getContentPane().add(Ustar);

		Pstar.setForeground(Color.RED);
		Pstar.setFont(new Font("Times New Roman", Font.BOLD, 28));
		Pstar.setBounds(699, 430, 46, 21);
		Pstar.setName("Pstar");  // Set name for AssertJ to access
		frame.getContentPane().add(Pstar);
	}

	// Getters for testing
	public JButton getLoginButton() {
		return btnLogin;
	}

	public JButton getCancelButton() {
		return btnCancel;
	}

	public JFrame getFrame() {
		return frame;
	}

	public JTextField getUsernameField() {
		return tfusername;
	}

	public JPasswordField getPasswordField() {
		return tfpwd;
	}

	public JLabel getUsernameStar() {
		return Ustar;
	}

	public JLabel getPasswordStar() {
		return Pstar;
	}
}
