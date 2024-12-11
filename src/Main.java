import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static CopyOnWriteArraySet<Integer> resultArray = new CopyOnWriteArraySet<>();
    public final static int[] valRange = {0, 100};
    public final static int[] arraySizeRange = {40, 60};

    public static ArrayList<Integer> generateArray(){
        Set<Integer> result = new LinkedHashSet<>();
        int size = (int) (Math.random() * (arraySizeRange[1] - arraySizeRange[0] + 1)) + arraySizeRange[0];

        while(result.size() != size){
            result.add((int) (Math.random() * (valRange[1] - valRange[0] + 1) + valRange[0]));
        }
        return new ArrayList<>(result);
    }

    public static ArrayList<ArrayList<Integer>> split(ArrayList<Integer> inputArr, int partsAmount) {
        ArrayList<ArrayList<Integer>> subArrays = new ArrayList<>();
        int size = inputArr.size();
        int partSize = size / partsAmount;
        int remainder = size % partsAmount;

        int leftIndex = 0;
        for (int i = 0; i < partsAmount; i++) {
            int currentPartSize = partSize + (i < remainder ? 1 : 0);
            ArrayList<Integer> part = new ArrayList<>(inputArr.subList(leftIndex, leftIndex + currentPartSize));
            subArrays.add(part);

            leftIndex += currentPartSize;
        }
        return subArrays;
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(4);
        ArrayList<Integer> inputArray = generateArray();

        System.out.printf("Згенерований масив[%d]: %s\n", inputArray.size(), inputArray);

        ArrayList<ArrayList<Integer>> arrayParts = split(inputArray, 4);
        List<PairwiseCalculate> pairwiseCalculateList = new ArrayList<>();
        System.out.println("Частини на які поділений.");
        for (ArrayList<Integer> arrayPart : arrayParts) {
            pairwiseCalculateList.add(new PairwiseCalculate(arrayPart));
            System.out.println(arrayPart);
        }

        List<Future<ArrayList<Integer>>> futures = new ArrayList<>();
        // Запуск таймер для відрахунку часу
        long startTimer = System.currentTimeMillis();
        for (PairwiseCalculate pairwiseCalculate : pairwiseCalculateList) {
            futures.add(es.submit(pairwiseCalculate));
        }

        for (Future<ArrayList<Integer>> future : futures) {
            while (!future.isDone());
        }

        // Зупинка таймеру після очікування усіх Futures
        long endTimer = System.currentTimeMillis();
        double elapsedTime = (endTimer - startTimer) / 1000.0;

        es.shutdown();
        System.out.println("Результуючий масив чисел: " + resultArray + ", size = " + resultArray.size());
        System.out.printf("Часу в секундах було використано на обчислення: %.6f сек\n", elapsedTime);
    }
}