package com.question6.customer;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.question6.helper.AddressDBHelper;

public class AddressFrame extends JFrame {
	
	private static final long serialVersionUID = 6226149255282575986L;

    private JTable table;
    private DefaultTableModel model;

    public AddressFrame(int customerId) {
        setTitle("Customer Addresses");
        setSize(700, 400);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"ID", "Address1", "City", "Postal Code"}, 0);
        table = new JTable(model);

        AddressDBHelper.load(customerId, model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton modifyBtn = new JButton("Modify");
        JButton delBtn = new JButton("Delete");
        btnPanel.add(addBtn);
        btnPanel.add(modifyBtn);
        btnPanel.add(delBtn);
        add(btnPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> AddressDBHelper.add(customerId, model));
        modifyBtn.addActionListener(e -> AddressDBHelper.update(customerId, model, table));
        delBtn.addActionListener(e -> AddressDBHelper.delete(customerId, model, table));
    }

}
