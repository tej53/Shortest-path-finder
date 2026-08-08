package main.ui;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class ControlPanel extends JPanel{
    public JButton startButton;
    public JButton resetButton;

    public ControlPanel(){
        setLayout(new FlowLayout());

        startButton = new JButton("Start");
        resetButton = new JButton("Reset");

        add(startButton);
        add(resetButton);
    }
}
