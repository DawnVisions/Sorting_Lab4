public class Verification {
    static boolean checkSortCorrectness(Sorting sortMethod)
    {
        for(int i = 0; i< 20; i++)
        {
            long[] list = createRandomListForPrinting(20);
            printArray(list);
            sortMethod.sort(list);
            printArray(list);
            if (!verifySorted(list))
                return false;
        }
        return true;
    }

    static boolean verifySorted(long arr[])
    {
        for(int i = 0; i<arr.length-2; i++){
            if(arr[i] > arr[i+1])
                return false;
        }
        return true;
    }

    public static long[] createRandomListForPrinting(int size){

        long[] list = new long[size];
        for (int i = 0; i<size; i++){
            list[i] = (long) (Math.random() * (100));
        }
        return list;
    }

    static void printArray(long arr[]) {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }
}
