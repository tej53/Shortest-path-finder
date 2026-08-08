package main;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import main.ui.GridPanel;
import main.ui.ControlPanel;
import main.algorithm.*;

public class Main {
    public static void main(String args[]){
        JFrame frame = new JFrame("Shortest Path Visualizer - Dijkstra's Algorithm");

        frame.setSize(700, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        GridPanel gridPanel = new GridPanel();
        ControlPanel controlPanel = new ControlPanel();

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        //Connect to Reset button
        controlPanel.resetButton.addActionListener(e -> {
            gridPanel.resetGrid();
        });

        //start button
        // Start button
        controlPanel.startButton.addActionListener(e -> {

            if (gridPanel.isAnimating()) return;

            gridPanel.clearAnimation();

            Dijkstra.Result result = Dijkstra.findShortestPath(
                gridPanel.getGrid(),
                gridPanel.getRows(),
                gridPanel.getCols()
            );

            gridPanel.animate(result.visited, result.path);
        });
    }
}
