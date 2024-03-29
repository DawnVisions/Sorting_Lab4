public class QuickSort {
    /* This function takes last element as pivot,
       places the pivot element at its correct
       position in sorted array, and places all
       smaller (smaller than pivot) to left of
       pivot and all greater elements to right
       of pivot */
    static int partition(long arr[], int low, int high)
    {
        long pivot = arr[high];
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than the pivot
            if (arr[j] < pivot)
            {
                i++;

                // swap arr[i] and arr[j]
                long temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        long temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;

        return i+1;
    }


    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    static void quickSort(long arr[], int low, int high)
    {
        if (low < high)
        {
            //Choose a random pivot between high and low
            int randomPivot =(int) (Math.random() * (high - low)) + low;

            //Swap high and randomPivot
            long temp = arr[high];
            arr[high] = arr[randomPivot];
            arr[randomPivot] = temp;

            /* pi is pivot index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            quickSort(arr, low, pi-1);
            quickSort(arr, pi+1, high);
        }
    }

    static void sort(long arr[])
    {
        quickSort(arr, 0, arr.length-1);
    }
}
