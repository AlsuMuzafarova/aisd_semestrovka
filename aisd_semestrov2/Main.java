package aisd_semestrov2;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static double calculateAverage(long[] array) {
        long sum = 0;
        for (long num : array) {
            sum += num;
        }
        return (double) sum / array.length;
    }

    public static void main(String[] args) {
        int numElements = 10000;
        int numSearches = 100;
        int numDeletes = 1000;
        IntegerWrapper actionCount = new IntegerWrapper(0);

        TwoThreeTree tree = new TwoThreeTree();

        long[] insertionTimes = new long[numElements];
        long[] insertionActions = new long[numElements];
        long[] searchTimes = new long[numSearches];
        long[] searchActions = new long[numSearches];
        long[] deletionTimes = new long[numDeletes];
        long[] deletionActions = new long[numDeletes];

        Random random = new Random();
        int[] randomNumbers = new int[numElements];
        for (int i = 0; i < numElements; i++) {
            randomNumbers[i] = random.nextInt(10000);
        }

        for (int i = 0; i < numElements; i++) {
            long startTime = System.nanoTime();
            tree.insert(randomNumbers[i], actionCount);
            insertionActions[i] = actionCount.value;
            long endTime = System.nanoTime();
            insertionTimes[i] = endTime - startTime;
        }

        for (int i = 0; i < numSearches; i++) {
            int randomNumberIndex = random.nextInt(numElements);
            int randomNumber = randomNumbers[randomNumberIndex];
            long startTime = System.nanoTime();
            tree.search(randomNumber, actionCount);
            searchActions[i] = actionCount.value;
            long endTime = System.nanoTime();
            searchTimes[i] = endTime - startTime;
        }

        for (int i = 0; i < numDeletes; i++) {
            int randomNumberIndex = random.nextInt(numElements);
            int randomNumber = randomNumbers[randomNumberIndex];
            long startTime = System.nanoTime();
            tree.remove(randomNumber, actionCount);
            deletionActions[i] = actionCount.value;
            long endTime = System.nanoTime();
            deletionTimes[i] = endTime - startTime;
        }

        System.out.println("Insertion times: " + Arrays.toString(insertionTimes));
        System.out.println("Insertion actions: " + Arrays.toString(insertionActions));
        System.out.println("Search times: " + Arrays.toString(searchTimes));
        System.out.println("Search Actions: " + Arrays.toString(searchActions));
        System.out.println("Deletion times: " + Arrays.toString(deletionTimes));
        System.out.println("Deletion Actions: " + Arrays.toString(deletionActions));

        double averageInsertionTime = calculateAverage(insertionTimes);
        double averageInsertionActions = calculateAverage(insertionActions);
        double averageSearchTime = calculateAverage(searchTimes);
        double averageSearchActions = calculateAverage(searchActions);
        double averageDeletionTime = calculateAverage(deletionTimes);
        double averageDeletionActions = calculateAverage(deletionActions);


        System.out.println("Average Insertion Time: " + averageInsertionTime);
        System.out.println("Average Insertion Actions: " + averageInsertionActions);
        System.out.println("Average Search Time: " + averageSearchTime);
        System.out.println("Average Search Actions: " + averageSearchActions);
        System.out.println("Average Deletion Time: " + averageDeletionTime);
        System.out.println("Average Deletion Actions: " + averageDeletionActions);
    }
}

