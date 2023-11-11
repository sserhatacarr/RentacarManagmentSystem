package core;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static void setTheme() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Metal".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
        }
    }

    public static void showMessage(String str) {
        String message;
        String title;
        switch (str) {
            case "success":
                message = "Operation is successful!";
                title = "Success";
                break;
            case "error":
                message = "An error occurred!";
                title = "Error";
                break;
            case "warning":
                message = "Warning!";
                title = "Warning";
                break;
            case "fill":
                message = "Please fill all fields!";
                title = "Warning";
                break;
                case "notFound":
                message = "Not Found!";
                title = "Warning";
                break;
            default:
                message = str;
                title = "Info";
                break;
        }
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldListEmpty(JTextField[] fieldList) {
        for (JTextField field : fieldList) {
            if (field.getText().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static int getLocationPoint(String type, Dimension size) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - size.width) / 2;
        int y = (screenSize.height - size.height) / 2;
        switch (type) {
            case "x":
                return x;
            case "y":
                return y;
            default:
                return 0;
        }
    }
}
