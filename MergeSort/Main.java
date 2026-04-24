package MergeSort;

public class Main {
    public static void main(String[] args) {
        try {
            Benchmark.main(args);
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
