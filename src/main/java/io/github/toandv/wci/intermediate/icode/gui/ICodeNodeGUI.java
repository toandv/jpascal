package io.github.toandv.wci.intermediate.icode.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by toan on 5/27/16.
 */
public class ICodeNodeGUI extends JPanel {
    @Override
    public void paintComponent(Graphics g) {
        // Draw Tree Here
        g.drawOval(5, 5, 25, 25);
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.add(new ICodeNodeGUI());
        jFrame.setSize(500, 500);
        jFrame.setVisible(true);
    }
}
