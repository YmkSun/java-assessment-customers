package com.question6.customer;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.question6.helper.CustomerDBHelper;

public class CustomerApp {
	

    private static JTable table;
    private static DefaultTableModel model;
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Customer List App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.setVisible(true);
            
            model = new DefaultTableModel(new String[]{"ID", "Short Name", "Full Name"}, 0);
            table = new JTable(model);
            
            CustomerDBHelper.load(model);
            frame.add(new JScrollPane(table), BorderLayout.CENTER);
            
            JPanel btnPanel = new JPanel();
            JButton addBtn = new JButton("Add");
            JButton deleteBtn = new JButton("Delete");
            JButton viewBtn = new JButton("View Addresses");
            btnPanel.add(addBtn);
            btnPanel.add(deleteBtn);
            btnPanel.add(viewBtn);
            
            frame.add(btnPanel, BorderLayout.SOUTH);

            viewBtn.addActionListener(e -> CustomerDBHelper.viewAddresses(table, model));
            addBtn.addActionListener(e -> CustomerDBHelper.add(table, model));
            deleteBtn.addActionListener(e -> CustomerDBHelper.delete(table, model));

        });
    }
	
}
