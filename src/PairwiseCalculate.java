import java.util.ArrayList;
import java.util.concurrent.Callable;

public class PairwiseCalculate implements Callable<ArrayList<Integer>> {
    private final ArrayList<Integer> inputArray;

    public PairwiseCalculate(ArrayList<Integer> inputArray) {
        this.inputArray = inputArray;
    }

    @Override
    public ArrayList<Integer> call() throws IllegalArgumentException{
        int size = inputArray.size();
        if (inputArray.size() % 2 != 0) {
            size--;
        }
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; i+=2) {
            result.add(inputArray.get(i)*inputArray.get(i+1));
        }
        Main.resultArray.addAll(result);
        return result;
    }
}
