import java.io.FileWriter;
import java.io.PrintWriter;

public class SortingTests {
    /* define constants */
    static long MAXVALUE = 2000000000;
    static long MINVALUE = -2000000000;
    static int numberOfTrials = 50;
    static int MAXINPUTSIZE = (int) Math.pow(2,24);
    static int MININPUTSIZE = 1;
    // static int SIZEINCREMENT =  10000000; // not using this since we are doubling the size each time

    static String ResultsFolderPath = "/home/elizabeth/IdeaProjects/Results/"; // pathname to results folder
    static FileWriter resultsFile;
    static PrintWriter resultsWriter;

    public static void main(String[] args) {

        boolean correct = Verification.checkSortCorrectness(QuickSort::sort);
        System.out.println("Verification Pass?: " + correct);

        // run the whole experiment at least twice, and expect to throw away the data from the earlier runs, before java has fully optimized
        runFullExperiment("QuickSort-Exp1-ThrowAway.txt");
        runFullExperiment("QuickSort-Exp2.txt");
        runFullExperiment("QuickSort-Exp3.txt");
    }

    private static void runFullExperiment(String resultsFileName) {

        try {
            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);
            resultsWriter = new PrintWriter(resultsFile);
        } catch (Exception e) {
            System.out.println("*****!!!!!  Had a problem opening the results file " + ResultsFolderPath + resultsFileName);
            return;
        }

        ThreadCpuStopWatch BatchStopwatch = new ThreadCpuStopWatch(); // for timing an entire set of trials
        ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch(); // for timing an individual trial
        double lastAverageTime = -1;
        double doublingRatio = 0;

        resultsWriter.println("#InputSize    AverageTime"); // # marks a comment in gnuplot data
        resultsWriter.flush();

        for (int inputSize = MININPUTSIZE; inputSize <= MAXINPUTSIZE; inputSize *= 2) {
            System.out.println("Running test for input size " + inputSize + " ... ");
            System.out.print("    Running trial batch...");
            System.gc();
            long batchElapsedTime = 0;
            for (long trial = 0; trial < numberOfTrials; trial++) {
                System.out.print("    Generating test data...");
                long[] testList = createRandomList(inputSize);
                System.gc();
                System.out.println("...done.");
                TrialStopwatch.start();
                QuickSort.sort(testList);
                batchElapsedTime = batchElapsedTime + TrialStopwatch.elapsedTime();
            }
            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double) numberOfTrials; // calculate the average time per trial in this batch
            if (lastAverageTime != -1) {
                doublingRatio = averageTimePerTrialInBatch / lastAverageTime;
            }
            lastAverageTime = averageTimePerTrialInBatch;

            /* print data for this size of input */
            resultsWriter.printf("%12d  %15.2f %10.2f\n", inputSize, averageTimePerTrialInBatch, doublingRatio);
            resultsWriter.flush();
            System.out.println(" ....done.");
        }
    }

    public static long[] createRandomList(int size){

        long[] list = new long[size];
        for (int i = 0; i<size; i++){
            list[i] = (long) (MINVALUE + Math.random() * (MAXVALUE-MINVALUE));
        }
        return list;
    }

    public static long[] createAscendingList(int size){
        long[] list = new long[size];
        long lastVal = 0;
        for (int i = 0; i<size; i++){
            long nextVal = lastVal + (long) (Math.random()*(MAXVALUE-MINVALUE)%10000+1);
            list[i] = nextVal;
            lastVal = nextVal;
        }
        return list;
    }

}

