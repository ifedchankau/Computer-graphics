import conversion.Converter;
import conversion.HSV;
import conversion.RGB;
import conversion.LAB;
import exception.OutOfBoundsException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Ilya Fedchenkov, group 13
 * Variant 4
 */

public class Main extends Application {
    
    private final GridPane grid = new GridPane();
    final ColorPicker colorPicker = new ColorPicker();
    
    private final Label label = new Label();
    
    private String[][] prompts = {
            { "r", "g", "b" },
            { "h", "s", "v" },
            { "l", "a", "b" }
        };
    
    private double[][] upperBounds = new double[3][3];
    private double[][] lowerBounds = new double[3][3];
    
    private boolean technicalChange = false;

    @Override
    public void start(Stage stage) {
        setBounds();

        Scene scene = new Scene(new HBox(5), 800, 340);
        scene.getStylesheets().add("style.css");

        HBox box = (HBox) scene.getRoot();
        box.setPadding(new Insets(5, 300, 7, 300));

        VBox withWarningLabel = new VBox();
        withWarningLabel.setPadding(new Insets(170, 0, 1500, -400));
        createGridPane();
        withWarningLabel.getChildren().addAll(grid, label);

        createColorPicker();
        box.getChildren().addAll(colorPicker, withWarningLabel);

        stage.setScene(scene);
        stage.show();

        setAllColorsFromRGB(new RGB(255.0, 255.0, 255.0));
    }
    
    private void setBounds() {
        for (int i = 0; i < 3; ++i) {
            upperBounds[0][i] = RGB.upperBounds[i];
            upperBounds[1][i] = HSV.upperBounds[i];
            upperBounds[2][i] = LAB.upperBounds[i];
            
            lowerBounds[0][i] = RGB.lowerBounds[i];
            lowerBounds[1][i] = HSV.lowerBounds[i];
            lowerBounds[2][i] = LAB.lowerBounds[i];
        }
    }
    
    private void displayOrRemoveWarning(boolean doDisplay) {
        if (doDisplay) {
            label.setVisible(true);
        } else {
            label.setVisible(false);
        }
    }
    
    private void setAllColorsFromRGB(RGB rgb) {
        boolean outOfBounds = false;
        HSV hsv = Converter.convertRGBtoHSV(rgb);
        LAB lab = Converter.convertHSVtoLAB(hsv);
        try {
            hsv.trim();
        } catch (OutOfBoundsException ex) {
            outOfBounds = true;
        }
        try {
            lab.trim();
        } catch (OutOfBoundsException ex) {
            outOfBounds = true;
        }
        
        displayOrRemoveWarning(outOfBounds);
        setAllColors(rgb, hsv, lab);
    }
    
    private void setAllColorsFromHSV(HSV hsv) {
        boolean outOfBounds = false;
        RGB rgb = Converter.convertHsvToRgb(hsv);
        LAB lab = Converter.convertHSVtoLAB(hsv);
        try {
            rgb.trim();
        } catch (OutOfBoundsException ex) {
            outOfBounds = true;
        }
        try {
            lab.trim();
        } catch (OutOfBoundsException ex) {
            outOfBounds = true;
        }
        
        displayOrRemoveWarning(outOfBounds);
        setAllColors(rgb, hsv, lab);
    }
    
    private void setAllColorsFromLab(LAB lab) {
        boolean outOfBounds = false;
        HSV hsv = Converter.convertLabToHSV(lab);
        RGB rgb = Converter.convertHsvToRgb(hsv);
        try {
            hsv.trim();
        } catch (OutOfBoundsException ex) {
            outOfBounds = true;
        }
        try {
            rgb.trim();
        } catch (OutOfBoundsException ex) {
            outOfBounds = true;
        }
        
        displayOrRemoveWarning(outOfBounds);
        setAllColors(rgb, hsv, lab);
    }
    
    private void updateColorComponentChooser(HBox box, double value) {
        Slider slider = (Slider)box.getChildren().get(1);
        technicalChange = true;
        slider.setValue(value);
        technicalChange = false;
        
        TextField tf = (TextField)box.getChildren().get(2);
        tf.setText(Long.toString(Math.round(value)));
    }
    
    private void setAllColors(RGB rgb, HSV hsv, LAB lab) {
        for (int i = 0; i < 3; ++i) {
            HBox box = getColorComponentChooserFromGrid(0, i);
            updateColorComponentChooser(box, rgb.getRgb()[i]);
            
            box = getColorComponentChooserFromGrid(1, i);
            updateColorComponentChooser(box, hsv.getHsv()[i]);
            
            box = getColorComponentChooserFromGrid(2, i);
            updateColorComponentChooser(box, lab.getLab()[i]);
        }
        
        float r = (float)(rgb.getR() / 255);
        float g = (float)(rgb.getG() / 255);
        float b = (float)(rgb.getB() / 255); 
        colorPicker.setValue(new Color(r, g, b, 1.0));
    }
    
    private HBox getColorComponentChooserFromGrid(int i, int j) {
        for (Node node : grid.getChildren()) {
            if (GridPane.getColumnIndex(node) == i && GridPane.getRowIndex(node) == j) {
                return ((HBox)node);
            }
        }
        return null;
    }
    
    private double getSliderValue(int i, int j) {
        HBox box = getColorComponentChooserFromGrid(i, j);
        Slider slider = (Slider)box.getChildren().get(1);
        return slider.getValue();
    }
    
    private void handleNewSliderValue(int i) {
        switch (i) {
            case 0:
                setAllColorsFromRGB(new RGB(
                        getSliderValue(i, 0),
                        getSliderValue(i, 1),
                        getSliderValue(i, 2)
                ));
                break;
            case 1:
                setAllColorsFromHSV(new HSV(
                        getSliderValue(i, 0),
                        getSliderValue(i, 1),
                        getSliderValue(i, 2)
                ));
                break;
            case 2:
                setAllColorsFromLab(new LAB(
                        getSliderValue(i, 0),
                        getSliderValue(i, 1),
                        getSliderValue(i, 2)
                ));
                break;
            default:
                break;
        }
    }
    
    private double getTextFieldValue(int i, int j) {
        HBox box = getColorComponentChooserFromGrid(i, j);
        TextField tf = (TextField)box.getChildren().get(2);
        return Double.parseDouble(tf.getText());
    }
    
    private void handleNewTextFieldValue(int i) {
        boolean outOfBounds = false;
        double component1 = 0.0, component2 = 0.0, component3 = 0.0;
        try {
            component1 = getTextFieldValue(i, 0);
            component2 = getTextFieldValue(i, 1);
            component3 = getTextFieldValue(i, 2);
        } catch (NumberFormatException ex) {
            return;
        }
        
        switch (i) {
            case 0:
                RGB rgb = new RGB(component1, component2, component3);
                try {
                    rgb.trim();
                } catch (OutOfBoundsException ex) {
                    outOfBounds = true;
                }
                setAllColorsFromRGB(rgb);
                break;
            case 1:
                HSV hsv = new HSV(component1, component2, component3);
                try {
                    hsv.trim();
                } catch (OutOfBoundsException ex) {
                    outOfBounds = true;
                }
                setAllColorsFromHSV(hsv);
                break;
            case 2:
                LAB lab = new LAB(component1, component2, component3);
                try {
                    lab.trim();
                } catch (OutOfBoundsException ex) {
                    outOfBounds = true;
                }
                setAllColorsFromLab(lab);
                break;
            default:
                break;
        }
        
        displayOrRemoveWarning(outOfBounds);
    }
    
    private void createColorPicker() {
        colorPicker.setValue(Color.WHITE);
        colorPicker.getStyleClass().add("button");
        
        colorPicker.setOnAction((EventHandler) event -> {
            Color c = colorPicker.getValue();
            setAllColorsFromRGB(new RGB(c));
        });
    }
    
    private HBox createColorChooser(int i, int j) {
        HBox box = new HBox();
        box.setPadding(new Insets(5, 5, 5, 5));
        box.setMinSize(220, 50);
        
        final Label componentLabel = new Label();
        componentLabel.setText(prompts[i][j]);
        
        final TextField textInput = new TextField();
        textInput.setPrefColumnCount(4);
        textInput.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
               handleNewTextFieldValue(i);
            }
        });
        textInput.focusedProperty().addListener(new ChangeListener< Boolean >() {
            @Override
            public void changed(ObservableValue observable, 
                    Boolean oldValue, Boolean newValue) {
                if (!newValue)
                    handleNewTextFieldValue(i);
            }
        });
        
        Slider slider = new Slider(lowerBounds[i][j], upperBounds[i][j], lowerBounds[i][j]);
        slider.setMinWidth(150);
        slider.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable,
                    Object oldValue, Object newValue) {
                if (!technicalChange) {
                    handleNewSliderValue(i);
                }
            }
        });
        
        box.getChildren().addAll(componentLabel, slider, textInput);
        return box;
    }
    
    private void createGridPane() {
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                HBox colorChooser = createColorChooser(i, j);
                GridPane.setConstraints(colorChooser, i, j);
                grid.getChildren().add(colorChooser);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
