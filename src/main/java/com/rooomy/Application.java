package com.rooomy;

import com.rooomy.gui.MainPage;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

public class Application extends JFrame {

    private static final long serialVersionUID = 1L;

    public Application() {
        super("CLTFConverter");
        if (validLicence()) {
            this.getContentPane().add(new MainPage());
            this.setSize(620, 650);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);
            this.setVisible(true);
            this.setResizable(false);
        }
    }

    private boolean validLicence() {
        Date today = new Date();
        Date endDay = new Date(2020 - 1900, 1, 1);
        return today.before(endDay);
    }

    public static void main(String[] args) {
        new Application();
    }
}
