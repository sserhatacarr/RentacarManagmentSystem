package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginView extends Layout {
    private JPanel container;
    private JPanel w_top;
    private JLabel lbl_welcome;
    private JLabel lbl_welcome2;
    private JPanel w_bottom;
    private JButton btn_login;
    private JTextField fld_username;
    private JPasswordField fld_pass;
    private JLabel lbl_username;
    private JLabel lbl_pass;
    private final UserManager userManager;

    public LoginView() {
        this.userManager = new UserManager();
        this.add(container);
        this.guiInitialize(400, 400);
        fld_pass.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    //login();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        btn_login.addActionListener(e -> {
            JTextField[] checkFieldList = {this.fld_username, this.fld_pass};
            if(Helper.isFieldListEmpty(checkFieldList)){
                Helper.showMessage("fill");
            }else {
                User loginUser = this.userManager.findByLogin(this.fld_username.getText(), this.fld_pass.getText());
                if (loginUser == null) {
                    Helper.showMessage("notFound");
                } else {
                    AdminView adminView = new AdminView(loginUser);
                    dispose();
                }
            }
        });
    }
}
