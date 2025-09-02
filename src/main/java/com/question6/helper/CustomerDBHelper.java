package com.question6.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.question6.customer.AddressFrame;
import com.question6.util.DBUtil;

public class CustomerDBHelper {

	public static void load(DefaultTableModel model) {

		try (Connection conn = DBUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT customer_id, short_name, full_name FROM customer")) {
			model.setRowCount(0);
			while (rs.next()) {
				System.out.println(rs.getInt(1) + ", " + rs.getString(2) + ", " + rs.getString(3));
				model.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3) });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "DB Connection Error: " + e.getMessage());
		}
	}

	public static void viewAddresses(JTable table, DefaultTableModel model) {
		int row = table.getSelectedRow();
		if (row >= 0) {
			int customerId = (int) model.getValueAt(row, 0);
			new AddressFrame(customerId).setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "Select a customer first!");
		}
	}

	public static void add(JTable table, DefaultTableModel model) {
		JTextField shortName = new JTextField();
		JTextField fullName = new JTextField();
		Object[] fields = { "Short Name:", shortName, "Full Name:", fullName };

		int option = JOptionPane.showConfirmDialog(null, fields, "Add Customer", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			try (Connection conn = DBUtil.getConnection();
					PreparedStatement ps = conn
							.prepareStatement("INSERT INTO customer(short_name, full_name) VALUES (?, ?)")) {
				ps.setString(1, shortName.getText());
				ps.setString(2, fullName.getText());
				ps.executeUpdate();
				load(model);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "DB Connection Error: " + e.getMessage());
			}
		}
	}

	public static void delete(JTable table, DefaultTableModel model) {
		int row = table.getSelectedRow();
		if (row >= 0) {
			int id = (int) model.getValueAt(row, 0);
			int confirm = JOptionPane.showConfirmDialog(null, "Delete Customer?");
			if (confirm == JOptionPane.YES_OPTION) {
				try (Connection conn = DBUtil.getConnection();
						PreparedStatement ps = conn.prepareStatement("DELETE FROM customer WHERE customer_id=?")) {
					ps.setInt(1, id);
					ps.executeUpdate();
					load(model);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "DB Connection Error: " + e.getMessage());
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Select a customer first!");
		}
	}

}
