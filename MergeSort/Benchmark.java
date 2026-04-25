package MergeSort;

import java.io.*;
import java.util.*;

public class Benchmark {

    public static void main(String[] args) throws IOException {
        // Шаг 1: Генерация данных
        DataGenerator.generateDatasets(80, 100, 10000);

        // Шаг 2: Бенчмаркинг
        System.out.println("\nЗапуск бенчмаркинга...\n");
        List<Result> results = new ArrayList<>();

        File dataDir = new File("data");
        File[] files = dataDir.listFiles((dir, name) -> name.startsWith("dataset_"));

        if (files == null || files.length == 0) {
            System.err.println("Файлы с данными не найдены!");
            return;
        }

        Arrays.sort(files); // сортируем по имени

        for (File file : files) {
            int[] data = loadFromFile(file);
            int size = data.length;

            // Создаём копию для каждого замера
            int[] dataCopy = Arrays.copyOf(data, data.length);

            MergeSort sorter = new MergeSort();

            // Измерение времени
            long startTime = System.nanoTime();
            sorter.sort(dataCopy);
            long endTime = System.nanoTime();

            double timeMs = (endTime - startTime) / 1_000_000.0;
            long iterations = sorter.getIterationCount();

            Result result = new Result(size, timeMs, iterations);
            results.add(result);

            System.out.printf("Размер: %6d | Время: %8.3f мс | Итераций: %8d%n",
                    size, timeMs, iterations);
        }

        // Шаг 3: Сохранение результатов
        saveResults(results, "merge_sort_results_final.csv");
        System.out.println("\nРезультаты сохранены в merge_sort_results_final.csv");

        // Шаг 4: Вывод статистики
        printStatistics(results);
    }

    private static int[] loadFromFile(File file) throws IOException {
        List<Integer> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    list.add(Integer.parseInt(line));
                }
            }
        }
        return list.stream().mapToInt(i -> i).toArray();
    }

    private static void saveResults(List<Result> results, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, false))) {
            writer.println("Размер_массива,Время_мс,Количество_итераций");
            for (Result r : results) {
                writer.printf("%d;%.6f;%d%n", r.size, r.timeMs, r.iterations);
            }
        }
    }

    private static void printStatistics(List<Result> results) {
        System.out.println("\n=== СТАТИСТИКА ===");
        double avgTimePerElement = results.stream()
                .mapToDouble(r -> r.timeMs / r.size)
                .average()
                .orElse(0);
        double avgIterationsPerElement = results.stream()
                .mapToDouble(r -> (double) r.iterations / r.size)
                .average()
                .orElse(0);

        System.out.printf("Среднее время на элемент: %.6f мс%n", avgTimePerElement);
        System.out.printf("Среднее кол-во итераций на элемент: %.2f%n", avgIterationsPerElement);
        System.out.printf("Теоретическое значение (3*log2(n)): ~%.2f для n=5000%n", 3 * Math.log(5000) / Math.log(2));
    }

    static class Result {
        int size;
        double timeMs;
        long iterations;

        Result(int size, double timeMs, long iterations) {
            this.size = size;
            this.timeMs = timeMs;
            this.iterations = iterations;
        }
    }
}
