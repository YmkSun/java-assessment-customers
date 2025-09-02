package com.question6.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.question6.util.DBUtil;

public class AddressDBHelper {

	public static void load(int customerId, DefaultTableModel model) {
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(
						"SELECT address_id, address1, city, postal_code FROM Address WHERE customer_id = ?")) {
			ps.setInt(1, customerId);
			ResultSet rs = ps.executeQuery();
			model.setRowCount(0);
			while (rs.next()) {
				model.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4) });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "DB Connection Error: " + e.getMessage());
		}
	}

	public static void add(int customerId, DefaultTableModel model) {
		if (model.getRowCount() >= 3) {
			JOptionPane.showMessageDialog(null, "Custome can have maximum 3 addresses!.");
			return;
		}

		JTextField address1 = new JTextField();
		JTextField address2 = new JTextField();
		JTextField address3 = new JTextField();
		JTextField city = new JTextField();
		JTextField postal = new JTextField();

		Object[] fields = { "Address 1:", address1, "Address 2:", address2, "Address 3:", address3, "City:", city,
				"Postal Code:", postal };

		int option = JOptionPane.showConfirmDialog(null, fields, "Add Address", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			if (!postal.getText().matches("\\d{5,6}")) {
				JOptionPane.showMessageDialog(null, "Invalid Postal Code!");
				return;
			}
			try (Connection conn = DBUtil.getConnection();
					PreparedStatement ps = conn.prepareStatement(
							"INSERT INTO Address(customer_id, address1, address2, address3, city, postal_code) "
									+ "VALUES (?, ?, ?, ?, ?, ?)")) {
				ps.setInt(1, customerId);
				ps.setString(2, address1.getText());
				ps.setString(3, address2.getText());
				ps.setString(4, address3.getText());
				ps.setString(5, city.getText());
				ps.setString(6, postal.getText());
				ps.executeUpdate();
				load(customerId, model);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "DB Connection Error: " + e.getMessage());
			}
		}
	}

	public static void update(int customerId, DefaultTableModel model, JTable table) {
		int row = table.getSelectedRow();
		if (row >= 0) {
			int id = (int) model.getValueAt(row, 0);

			JTextField address1 = new JTextField((String) model.getValueAt(row, 1));
			JTextField city = new JTextField((String) model.getValueAt(row, 2));
			JTextField postal = new JTextField((String) model.getValueAt(row, 3));

			Object[] fields = { "Address 1:", address1, "City:", city, "Postal Code:", postal };

			int option = JOptionPane.showConfirmDialog(null, fields, "Modify Address", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				if (!postal.getText().matches("\\d{5,6}")) {
					JOptionPane.showMessageDialog(null, "Invalid Postal Code!");
					return;
				}
				try (Connection conn = DBUtil.getConnection();
						PreparedStatement ps = conn.prepareStatement(
								"UPDATE Address SET address1=?, city=?, postal_code=? WHERE address_id=?")) {
					ps.setString(1, address1.getText());
					ps.setString(2, city.getText());
					ps.setString(3, postal.getText());
					ps.setInt(4, id);
					ps.executeUpdate();
					load(customerId, model);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "DB Connection Error: " + e.getMessage());
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Select an address to modify!");
		}
	}

	public static void delete(int customerId, DefaultTableModel model, JTable table) {
		int row = table.getSelectedRow();
		if (row >= 0) {
			int id = (int) model.getValueAt(row, 0);
			int confirm = JOptionPane.showConfirmDialog(null, "Delete Address?");
			if (confirm == JOptionPane.YES_OPTION) {
				try (Connection conn = DBUtil.getConnection();
						PreparedStatement ps = conn.prepareStatement("DELETE FROM Address WHERE address_id=?")) {
					ps.setInt(1, id);
					ps.executeUpdate();
					load(customerId, model);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "DB Connection Error: " + e.getMessage());
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Select Address to delete!");
		}
	}
}
