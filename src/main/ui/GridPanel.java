package main.ui;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GridPanel extends JPanel {

    private int rows = 20;
    private int cols = 20;
    private int cellSize = 30;

    private List<int[]> visitedAnimation;
    private List<int[]> pathAnimation;
    private int animationIndex = 0;

    private boolean isAnimating = false;

    
    private int[][] grid = new int[rows][cols];

    private boolean startPlaced = false;
    private boolean endPlaced = false;

    public GridPanel() {

        MouseAdapter mouseHandler = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                handleMouse(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouse(e);
            }
        };

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    private void handleMouse(MouseEvent e) {

        if (isAnimating) return; //block clicks during animation

        int col = e.getX() / cellSize;
        int row = e.getY() / cellSize;

        if (row >= rows || col >= cols) return;

        if (e.getButton() == MouseEvent.BUTTON3) {
            if (grid[row][col] == 1) startPlaced = false;
            if (grid[row][col] == 2) endPlaced = false;
            grid[row][col] = 0;
        }
        else {

            if (!startPlaced) {
                grid[row][col] = 1;
                startPlaced = true;
            }
            else if (!endPlaced) {
                grid[row][col] = 2;
                endPlaced = true;
            }
            else {
                if (grid[row][col] == 0)
                    grid[row][col] = 3;
            }
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                int x = col * cellSize;
                int y = row * cellSize;

                if (grid[row][col] == 1) {
                    g.setColor(Color.GREEN);
                }
                else if (grid[row][col] == 2) {
                    g.setColor(Color.RED);
                }
                else if (grid[row][col] == 3) {
                    g.setColor(Color.BLACK);
                }
                else if (grid[row][col] == 4) {
                    g.setColor(Color.BLUE);
                }
                else if (grid[row][col] == 5) {
                    g.setColor(Color.YELLOW);
                }
                else {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(x, y, cellSize, cellSize);

                g.setColor(Color.GRAY);
                g.drawRect(x, y, cellSize, cellSize);
            }
        }
    }

    public void animate(List<int[]> visited, List<int[]> path) {

        isAnimating = true; // start lock

        this.visitedAnimation = visited;
        this.pathAnimation = path;
        animationIndex = 0;

        Timer timer = new Timer(30, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (animationIndex < visitedAnimation.size()) {

                    int[] cell = visitedAnimation.get(animationIndex);

                    if (grid[cell[0]][cell[1]] == 0)
                        grid[cell[0]][cell[1]] = 4;

                    animationIndex++;

                }
                else if (animationIndex < visitedAnimation.size() + pathAnimation.size()) {

                    int index = animationIndex - visitedAnimation.size();
                    int[] cell = pathAnimation.get(index);

                    if (grid[cell[0]][cell[1]] != 1 && grid[cell[0]][cell[1]] != 2)
                        grid[cell[0]][cell[1]] = 5;

                    animationIndex++;

                }
                else {
                    ((Timer)e.getSource()).stop();
                    isAnimating = false; // 🔥 unlock after finish
                }

                repaint();
            }
        });

        timer.start();
    }

    // 🔥 Clear only animation (blue & yellow)
    public void clearAnimation() {

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                if (grid[r][c] == 4 || grid[r][c] == 5) {
                    grid[r][c] = 0;
                }
            }
        }

        repaint();
    }

    public boolean isAnimating() {
        return isAnimating;
    }

    public void resetGrid() {
        grid = new int[rows][cols];
        startPlaced = false;
        endPlaced = false;
        repaint();
    }

    public int[][] getGrid(){
        return grid;
    }

    public int getRows(){
        return rows;
    }

    public int getCols(){
        return cols;
    }
}