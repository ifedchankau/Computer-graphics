package controller;

import model.HistogramAlignment;
import model.LinearContrast;
import model.Segmentation;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Ilya Fedcnhenkov, 13 group
 * Variant 4
 */


public class ImageProcess extends JFrame {
    private JLabel imageLabel;
    private JLabel processedImageLabel;

    private String selectedImagePath;

    public ImageProcess() {
        super("Image in process");

        JButton browseButton = new JButton("Choose image");
        browseButton.setBounds(20, 30, 200, 40);
        browseButton.addActionListener(e -> {
            JFileChooser file = new JFileChooser();
            file.setCurrentDirectory(new File(System.getProperty("user.home")));

            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
            file.addChoosableFileFilter(filter);
            int result = file.showSaveDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = file.getSelectedFile();
                this.selectedImagePath = selectedFile.getAbsolutePath();
                imageLabel.setIcon(resizeImage(this.selectedImagePath));
            } else if (result == JFileChooser.CANCEL_OPTION) {
                System.out.println("No file selected");
            }
        });
        this.add(browseButton);


        JButton segmentateButton = new JButton("Segmentation");
        segmentateButton.setBounds(240, 30, 200, 40);
        segmentateButton.addActionListener(e -> {
            if (this.selectedImagePath != null) {
                File imageFile = new File(this.selectedImagePath);
                try {
                    BufferedImage originalImage = ImageIO.read(imageFile);
                    BufferedImage processedImage = Segmentation.Segmented(originalImage);

                    String destFileName = Paths.get(imageFile.getParentFile().getAbsolutePath(),
                            "segmentation" + imageFile.getName()).toString();
                    ImageIO.write(processedImage, "jpg", new File(destFileName));
                    processedImageLabel.setIcon(resizeImage(destFileName));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.add(segmentateButton);


        JButton linearContrastingButton = new JButton("Linear contras");
        linearContrastingButton.setBounds(460, 30, 200, 40);
        linearContrastingButton.addActionListener(e -> {
            if (this.selectedImagePath != null) {
                File imageFile = new File(this.selectedImagePath);
                try {
                    BufferedImage originalImage = ImageIO.read(imageFile);
                    BufferedImage processedImage = LinearContrast.whiteBalance(originalImage);
                    String destFileName = Paths.get(imageFile.getParentFile().getAbsolutePath(),
                            "linear" + imageFile.getName()).toString();

                    ImageIO.write(processedImage, "jpg", new File(destFileName));
                    processedImageLabel.setIcon(resizeImage(destFileName));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.add(linearContrastingButton);


        JButton equalizeButton = new JButton("Equalization");
        equalizeButton.setBounds(680, 30, 200, 40);
        equalizeButton.addActionListener(e -> {
            if (this.selectedImagePath != null) {
                File imageFile = new File(this.selectedImagePath);
                try {
                    BufferedImage originalImage = ImageIO.read(imageFile);
                    BufferedImage processedImage = HistogramAlignment.align(originalImage);
                    String destFileName = Paths.get(imageFile.getParentFile().getAbsolutePath(),
                            "equal" + imageFile.getName()).toString();

                    ImageIO.write(processedImage, "jpg", new File(destFileName));
                    processedImageLabel.setIcon(resizeImage(destFileName));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.add(equalizeButton);

        imageLabel = new JLabel();
        imageLabel.setBounds(33, 100, 400, 300);
        this.add(imageLabel);

        processedImageLabel = new JLabel();
        processedImageLabel.setBounds(466, 100, 400, 300);
        this.add(processedImageLabel);

        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(50, 50);
        setSize(900, 450);
        setVisible(true);
    }

    private ImageIcon resizeImage(String ImagePath) {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    public static void main(String[] args) {
        new ImageProcess();
    }
}

