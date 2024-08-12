package app;

import gui.ExpenseManager;
import gui.WelcomeFrame;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(WelcomeFrame::new);
    }
}