package se2203.assignment1;

import javafx.application.Platform;

public class MergeSort extends SortingHubController implements SortingStrategy  {
    private int[] list;
    private SortingHubController controller;

    public MergeSort(int[] list, SortingHubController controller){ //constructor
        this.list = list;
        this.controller = controller;
    }


    public void sort(int[] array) {
        sort2(array, 0, array.length - 1);
    }

    private  void sort2(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }

        int middle = (left + right) / 2;
        sort2(array, left, middle);
        sort2(array, middle + 1, right);
        merge(array, left, middle, right);
    }

    private  void merge(int[] array, int left, int middle, int right) {
        int i = left;
        int j = middle + 1;

        while (i <= middle && j <= right) {
            if (array[i] <= array[j]) {
                i++;
            } else {
                int value = array[j];
                int index = j;
                while (index != i) {
                    array[index] = array[index - 1];
                    index--;

                    try{ // this delays the sorting animation
                        Thread.sleep(10);
                        Platform.runLater(()-> controller.updateGraph(list));
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
                array[i] = value;
                i++;
                middle++;
                j++;
                try{
                    Thread.sleep(10);
                    Platform.runLater(()-> controller.updateGraph(list));
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void run() {
        sort(list);
    }
}
