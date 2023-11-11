package view;

import business.BrandManager;
import core.Helper;
import entity.Brand;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminView extends Layout {
    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_welcome;
    private JTabbedPane tab_menu;
    private JButton btn_logout;
    private JPanel pnl_brand;
    private JScrollPane scrl_brand_pane;
    private JTable tbl_brand;
    private User user;
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private BrandManager brandManager;
    private JPopupMenu popupMenu;

    public AdminView(User user) {
        this.brandManager = new BrandManager();
        this.add(container);
        this.guiInitialize(400, 400);
        this.user = user;
        if (this.user == null) {
            dispose();
        }
        this.lbl_welcome.setText("Welcome : " + this.user.getUsername());

        loadBrandTable();
        loadBrandComponent();

        this.tbl_brand.setComponentPopupMenu(popupMenu);
    }

    public void loadBrandComponent() {
        this.tbl_brand.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_brand.rowAtPoint(e.getPoint());
                tbl_brand.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });
        this.popupMenu = new JPopupMenu();
        this.popupMenu.add("Add").addActionListener(e -> {
            BrandView brandView = new BrandView(null);
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                }
            });
        });
        this.popupMenu.add("Edit").addActionListener(e -> {
            int selectedBrandId = this.getSelectedTableRow(tbl_brand, 0);
            Brand brand = brandManager.getById(selectedBrandId);
            BrandView brandView = new BrandView(brand);
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                }
            });
        });
        this.popupMenu.add("Delete").addActionListener(e -> {
            int selectedBrandId = Integer.parseInt(tbl_brand.getValueAt(tbl_brand.getSelectedRow(), 0).toString());
            Brand brand = brandManager.getById(selectedBrandId);
            boolean result = Helper.confirm("sure");
            if (result) {
                boolean deleteResult = brandManager.delete(brand.getId());
                if (deleteResult) {
                    Helper.showMessage("success");
                    loadBrandTable();
                } else {
                    Helper.showMessage("error");
                }
            }
        });
    }

    public void loadBrandTable() {
        Object[] colBrand = {"ID", "Brand Name"};
        ArrayList<Object[]> brandList = this.brandManager.getForTable(colBrand.length);
        this.createTable(this.tmdl_brand, this.tbl_brand, colBrand, brandList);
    }
}