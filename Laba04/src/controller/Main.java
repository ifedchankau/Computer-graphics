package controller;

import model.logic.Algorithm;
import view.Graphic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Ilya Fedchenkov, group 13
 */

public class Main extends JFrame {

    private int WIDTH = 1200;
    private int HEIGHT = 600;

    private Graphic drawingPanel = new Graphic();

    private JTextField x1Input = new JTextField(21);
    private JTextField y1Input = new JTextField(21);
    private JTextField x2Input = new JTextField(21);
    private JTextField y2Input = new JTextField(21);
    private JTextField radiusInput = new JTextField(21);

    private JButton stepByStepButton;
    private JButton DADButton;
    private JButton brezenkhemButton;
    private JButton brezenkhemCircleButton;
    private JButton clearButton;


    private Algorithm algorithm = new Algorithm();

    public Main(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setLayout(null);

        drawingPanel.setBounds((int)((1 - 0.75) * WIDTH), 0, (int)(0.7 * WIDTH), (int)(0.9 * HEIGHT));
        add(drawingPanel);

        Font labelFont = new Font("", Font.ITALIC, 27);

        JLabel x1Label = new JLabel("From (x, y):");

        x1Label.setFont(labelFont);
        x1Label.setBounds(10, 10, 180, 40);
        add(x1Label);

        x1Input.setBounds(160, 10, 50, 40);
        x1Input.setFont(labelFont);
        add(x1Input);

        y1Input.setBounds(220, 10, 50, 40);
        y1Input.setFont(labelFont);
        add(y1Input);


        JLabel x2Label = new JLabel("to ");
        x2Label.setFont(labelFont);
        x2Label.setBounds(110, 60, 50, 40);
        add(x2Label);

        x2Input.setBounds(160, 60, 50, 40);
        x2Input.setFont(labelFont);
        add(x2Input);

        y2Input.setBounds(220, 60, 50, 40);
        y2Input.setFont(labelFont);
        add(y2Input);

        JLabel radiusLabel = new JLabel("Radius: ");
        radiusLabel.setFont(labelFont);
        radiusLabel.setBounds(10, 110, 120, 40);
        add(radiusLabel);

        radiusInput.setBounds(160, 110, 100, 40);
        radiusInput.setFont(labelFont);
        add(radiusInput);


        stepByStepButton = new JButton("Step by step");
        stepByStepButton.setBackground(Color.green);
        stepByStepButton.setBounds(10, 190, 180, 60);
        stepByStepButton.addActionListener(e -> {
            int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
            try {
                x1 = Integer.parseInt(x1Input.getText());
                y1 = Integer.parseInt(y1Input.getText());
                x2 = Integer.parseInt(x2Input.getText());
                y2 = Integer.parseInt(y2Input.getText());
            } catch (Exception ex) {

            }
            drawingPanel.drawPoints(algorithm.stepByStep(x1, y1, x2, y2));
        });
        add(stepByStepButton);

        DADButton = new JButton("DAD");
        DADButton.setBackground(Color.green);
        DADButton.setBounds(10, 260, 180, 60);
        DADButton.addActionListener(e -> {
            int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
            try {
                x1 = Integer.parseInt(x1Input.getText());
                y1 = Integer.parseInt(y1Input.getText());
                x2 = Integer.parseInt(x2Input.getText());
                y2 = Integer.parseInt(y2Input.getText());
            } catch (Exception ex) {

            }
            drawingPanel.drawPoints(algorithm.DAD(x1, y1, x2, y2));

        });
        add(DADButton);

        brezenkhemButton = new JButton("Brezenkhem");
        brezenkhemButton.setBackground(Color.green);
        brezenkhemButton.setBounds(10, 330, 180, 60);
        brezenkhemButton.addActionListener(e -> {
            int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
            try {
                x1 = Integer.parseInt(x1Input.getText());
                y1 = Integer.parseInt(y1Input.getText());
                x2 = Integer.parseInt(x2Input.getText());
                y2 = Integer.parseInt(y2Input.getText());
            } catch (Exception ex) {

            }
            drawingPanel.drawPoints(algorithm.brezenkhem(x1, y1, x2, y2));
        });
        add(brezenkhemButton);

        brezenkhemCircleButton = new JButton("Bezenkhem circle");
        brezenkhemCircleButton.setBackground(Color.green);
        brezenkhemCircleButton.setBounds(10, 400, 180, 60);
        brezenkhemCircleButton.addActionListener(e -> {
            int x1 = 0, y1 = 0, r = 0;
            try {
                x1 = Integer.parseInt(x1Input.getText());
                y1 = Integer.parseInt(y1Input.getText());
                r = Integer.parseInt(radiusInput.getText());
            } catch (Exception ex) {

            }
            drawingPanel.drawPoints(algorithm.brezenkhemCircle(x1, y1, r));
        });
        add(brezenkhemCircleButton);

        clearButton = new JButton("Clear");
        clearButton.setBackground(Color.WHITE);
        clearButton.setBounds(10, 470, 180, 60);
        clearButton.addActionListener(e -> drawingPanel.drawPoints(new ArrayList<Point>()));

        add(clearButton);

        setResizable(false);
    }

    public static void main(String[] args) {
        Main mainWindow = new Main();
        mainWindow.repaint();
    }
}
