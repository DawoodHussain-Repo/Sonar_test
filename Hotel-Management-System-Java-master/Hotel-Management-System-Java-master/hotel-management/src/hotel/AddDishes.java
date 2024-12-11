package hotel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
public class AddDishes extends JFrame {

	private JPanel contentPane;
	public JTextField d1; // Dish Name
	public JTextField d2; // Dish Price
	public JTextField d3; // Dish Type
	public JTable table;

	// Create the frame
	public AddDishes() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				displayDishes(); // Display dishes when the window opens
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 972, 611);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Setup labels, buttons, text fields, etc.

		// Add Dish Button
		JButton btnAddDish = new JButton("ADD DISH");
		btnAddDish.setIcon(new ImageIcon("images\\plus (1).png"));
		btnAddDish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDishes(); // Add dish when clicked
			}
		});
		btnAddDish.setFont(new Font("High Tower Text", Font.BOLD, 20));
		btnAddDish.setBounds(45, 486, 176, 53);
		contentPane.add(btnAddDish);

		// Delete Dish Button
		JButton btnDeleteDish = new JButton("DELETE DISH");
		btnDeleteDish.setIcon(new ImageIcon("images\\iconfinder_f-cross_256_282471 (1).png"));
		btnDeleteDish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteDishes(); // Delete dish when clicked
			}
		});
		btnDeleteDish.setFont(new Font("High Tower Text", Font.BOLD, 20));
		btnDeleteDish.setBounds(245, 486, 221, 53);
		contentPane.add(btnDeleteDish);

		// Update Dish Button
		JButton btnUpdateDish = new JButton("UPDATE DISH");
		btnUpdateDish.setIcon(new ImageIcon("images\\updated.png"));
		btnUpdateDish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDishes(); // Update dish when clicked
			}
		});
		btnUpdateDish.setFont(new Font("High Tower Text", Font.BOLD, 20));
		btnUpdateDish.setBounds(502, 486, 221, 53);
		contentPane.add(btnUpdateDish);

		// Back Button
		JButton btnBack = new JButton("BACK");
		btnBack.setIcon(new ImageIcon("images\\back.png"));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminForm af = new AdminForm();
				af.setVisible(true);
				af.pack();
				af.setLocationRelativeTo(null);
				af.setBounds(100, 100, 1080, 633);
				setVisible(false);
			}
		});
		btnBack.setFont(new Font("High Tower Text", Font.BOLD, 20));
		btnBack.setBounds(765, 486, 143, 53);
		contentPane.add(btnBack);

		// Other UI elements...
	}

	// Display dishes in the table
	public void displayDishes() {
		GetConnection connect = new GetConnection();
		Connection conn = connect.getConnection();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("DISH NO");
		model.addColumn("DISH NAME");
		model.addColumn("DISH TYPE");
		model.addColumn("PRICE");

		try {
			String query = "SELECT * FROM restaurant";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				model.addRow(new Object[]{
						rs.getString("itemNo"),
						rs.getString("itemName"),
						rs.getString("Type"),
						rs.getString("Price")
				});
			}

			rs.close();
			st.close();
			conn.close();
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(70);
			table.getColumnModel().getColumn(1).setPreferredWidth(167);
			table.getColumnModel().getColumn(2).setPreferredWidth(90);
			table.getColumnModel().getColumn(3).setPreferredWidth(90);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Add dish to the database
	public void addDishes() {
		if (d1.getText().isEmpty() || d2.getText().isEmpty() || d3.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please fill all fields!");
			return;
		}

		try {
			GetConnection connect = new GetConnection();
			Connection conn = connect.getConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO restaurant(itemName, Price, Type) VALUES (?,?,?)");
			ps.setString(1, d1.getText());
			ps.setString(2, d2.getText());
			ps.setString(3, d3.getText());

			if (ps.executeUpdate() > 0) {
				JOptionPane.showMessageDialog(null, "New Dish Added");
				displayDishes(); // Refresh table after adding
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Update an existing dish
	public void updateDishes() {
		if (d1.getText().isEmpty() || d2.getText().isEmpty() || d3.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please fill all fields!");
			return;
		}

		try {
			int i = table.getSelectedRow();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			String no = model.getValueAt(i, 0).toString();

			GetConnection connect = new GetConnection();
			Connection conn = connect.getConnection();
			PreparedStatement ps = conn.prepareStatement("UPDATE restaurant SET itemName=?, Price=?, Type=? WHERE itemNo=?");
			ps.setString(1, d1.getText());
			ps.setString(2, d2.getText());
			ps.setString(3, d3.getText());
			ps.setString(4, no);

			if (ps.executeUpdate() > 0) {
				JOptionPane.showMessageDialog(null, "Dish Updated");
				displayDishes(); // Refresh table after updating
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Delete a selected dish
	public void deleteDishes() {
		try {
			int i = table.getSelectedRow();
			if (i == -1) {
				JOptionPane.showMessageDialog(null, "Please select a dish to delete!");
				return;
			}

			DefaultTableModel model = (DefaultTableModel) table.getModel();
			String no = model.getValueAt(i, 0).toString();

			GetConnection connect = new GetConnection();
			Connection conn = connect.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM restaurant WHERE itemNo=?");
			ps.setString(1, no);

			if (ps.executeUpdate() > 0) {
				JOptionPane.showMessageDialog(null, "Dish Deleted");
				displayDishes(); // Refresh table after deleting
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
