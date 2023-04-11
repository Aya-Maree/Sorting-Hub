package se2203.assignment1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class SortingHubController implements Initializable {

    private int[] intArray;
    private int arraySz; // will hold the size of the array
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Slider ArraySizeSlider;
    @FXML
    private Label arraySzLbl;
    @FXML
    private Button resetBtn;
    @FXML
    private Pane DisplayScreen;

    private SortingStrategy sortingStrategy;

    Thread t;

    public void setSortStrategy(){ //sets the sort strategy based on the value of the comboBox
        String choice = comboBox.getValue();
        if (choice.equals("Merge Sort")) {
            sortingStrategy = new MergeSort(intArray, this); //call the MergeSort constructor and give it the required arguments
            t = new Thread(sortingStrategy); // create a new Thread and pass the sortingStrategy object
        }
       else if(choice.equals("Selection Sort")){
            sortingStrategy = new SelectionSort(intArray,this);
            t = new Thread(sortingStrategy);
        }
    }

    @FXML
    public void sliderSize(){// helps us change the size of the array based off of the slider value and also display the size
        arraySz = (int)ArraySizeSlider.getValue(); // this gets the value of the slider and sets it to the array size field, cast to int
        arraySzLbl.setText(String.valueOf(arraySz)); //displays the size
        intArray = populateArray(arraySz);
        int[] bars = populateArray(arraySz);
        updateGraph(bars);
    }
    @FXML
    public void reset(){
        ArraySizeSlider.setValue(64);// this sets the initial value of the array size to 64
        comboBox.getSelectionModel().select(0); // this sets the initial value of the comoBox to Merge Sort
        sliderSize();

    }
    @FXML
    /*
    This method takes in an array and then displays the value of each index in the array as a rectangle "bar" in the "DiplayScreen" Pane I created
    - I did this by first scaling the rectangles to the size of the DisplayScreen to make sure it displays in a proper way
    - I set the height and width of the DisplayScreen
     */

    public void updateGraph(int[] bars){
        ArrayList<Rectangle> barsList = new ArrayList<>(arraySz); // arraylist of type Rectangle to hold all the bars
        double dHeight = DisplayScreen.getPrefHeight();
        double dWidth = DisplayScreen.getPrefWidth();
        double barsWidth = (dWidth / bars.length) - 2; // -2 is to help with keeping a gap between each bar and the next

        Platform.runLater(()-> {
            DisplayScreen.getChildren().clear(); // clears the DisplayScreen allowing the graph to update easily

            for (int i = 0; i < bars.length; i++) { //this for loop iterates through the indices of the array and creates a rectangle to represent each bar
                double barsHeight = (bars[i] * dHeight) / bars.length; //sets the height of the bar
                double xPos = i * (barsWidth + 2); //sets the x position of the bar
                double yPos = dHeight - barsHeight; //sets the y position of the bar
                Rectangle bar = new Rectangle(xPos, yPos, barsWidth, barsHeight);
                bar.setFill(Color.RED); // fills the bar with a red fill
                barsList.add(bar); //add the bar into the arrayList
            }
            DisplayScreen.getChildren().setAll(barsList); // setting the contents of the arraylist as children of the DisplayScreen pane
        });

    }
    @FXML
    /*
    this method takes in an int variable called size and returns an unsorted array of that numbers ranging from 1 to the size , with no duplicates
    - how I created this method is first I created an array of the given size and then populated from 1 to the size using a for loop (this takes care of no duplicates)
    - to unsort the values in the array I then used the Fisher Yates shuffle algorithm to change up the order
     */
    public static int[] populateArray(int size){
        int [] sortedArray = new int[size];
        for (int i = 0; i < size; i++) {
            sortedArray[i] = (i+1);
        }
        Random rn = new Random();
        //Fisher Yates shuffle algorithm
        for (int i = sortedArray.length-1; i > 0 ; i--) {
            int j = rn.nextInt(i + 1);
            int temp = sortedArray[j];
            sortedArray[j] = sortedArray[i];
            sortedArray[i] = temp;
        }
        return sortedArray;
    }
    public void orderArray() { //this will order the array using the desired sortStrategy
        sliderSize();
        setSortStrategy();
        t.start(); //starts the thread to display the animation of how the sorting is done

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reset();
        sliderSize(); //this is a method used to change the size of the array and also display the size
        comboBox.getItems().addAll("Merge Sort","Selection Sort");
        comboBox.getSelectionModel().select(0); // this sets the initial value of the comoBox to Merge Sort
        intArray = populateArray(arraySz);
        updateGraph(intArray);
        }
    }
