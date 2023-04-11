package se2203.assignment1;

import javafx.application.Platform;

public class SelectionSort implements SortingStrategy {
    private int[] list;
    private SortingHubController controller;

    public SelectionSort(int[] list, SortingHubController controller) {
        this.list = list;
        this.controller= controller;
    }

    public void sort(int[] list) {
        long startTime = System.nanoTime();
        for (int i = 0; i < list.length - 1; i++) { //reads through the whole array
            int min = i;// assign the current index to be the minimum element's index
            for (int k = i + 1; k < list.length; k++) {// reads through the next index of i
                if (list[k] < list[min])  {// compares the element of index k with the assumed to be min index
                    min = k; // if the k index element is greater than the min index then re-define min to equal k
                    //building a sorted list from the left side of the list since we want ascending order
                }
            }
            int temp = list[min];// temp element a[min]
            list[min] = list[i];//move index i element to index min
            list[i] = temp;//make index i element the temp element
           try{
                Thread.sleep(50);
                Platform.runLater(()-> controller.updateGraph(list));
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            }
        }

    @Override
    public void run() {
        sort(list);
    }
}
