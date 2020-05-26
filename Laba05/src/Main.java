import model.logic.Algorithm;
import view.Graphic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Ilya Fedchankau, group 13
 * Variant 4
 */

public class Main extends JFrame {

    private int WIDTH = 800;
    private int HEIGHT = 600;
    private final double GRAPHIC_W = 0.9;
    private final double GRAPHIC_H = 0.8;
    private Graphic graphic = new Graphic();
    private JButton runButton;
    private Algorithm algorithm = new Algorithm();
    private ArrayList<ArrayList<Integer>> segments = new ArrayList<>();
    private ArrayList<Integer> rectangle = new ArrayList<>();


    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setLayout(null);

        graphic.setBounds((int) (0.05 * WIDTH), 70, (int) (GRAPHIC_W * WIDTH), (int) (GRAPHIC_H * HEIGHT));
        add(graphic);

        runButton = new JButton("RUN");
        runButton.setBackground(Color.green);
        runButton.setBounds((WIDTH - 150) / 2, 20, 150, 40);
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                segments = algorithm.run(segments, rectangle);
                graphic.clear();
                drawLines();
            }
        });

        add(runButton);

        setResizable(false);
    }

    private void getParameters(String filePath) {
        try {
            Scanner scanner = new Scanner(new File(filePath));
            int n = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < n; i++) {
                ArrayList<Integer> segment = new ArrayList<>(4);
                for (int j = 0; j < 4; j++) {
                    segment.add(Integer.parseInt(scanner.next()));
                }
                segments.add(segment);
            }
            rectangle = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                rectangle.add(Integer.parseInt(scanner.next()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void drawLines() {
        for (ArrayList<Integer> segment : segments) {

            int x1 = segment.get(0);
            int y1 = segment.get(1);
            int x2 = segment.get(2);
            int y2 = segment.get(3);

            graphic.drawPoints(algorithm.stepByStep(x1, y1, x2, y2));
        }
        graphic.drawRectangle(rectangle);
    }

    public static void main(String[] args) {
        Main mainWindow = new Main();
        mainWindow.getParameters("linesCoordinates.txt");
        mainWindow.drawLines();
        mainWindow.repaint();
    }
}
