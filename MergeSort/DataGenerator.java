package MergeSort;

import java.io.*;
import java.util.*;

public class DataGenerator {

    public static void generateDatasets(int count, int minSize, int maxSize) throws IOException {
        File dataDir = new File("data");
        // Очищаем старые данные
        if (dataDir.exists()) {
            for (File file : dataDir.listFiles()) {
                file.delete();
            }
        } else {
            dataDir.mkdirs();
        }

        Random random = new Random(42); // фиксированный seed для воспроизводимости
        int[] sizes = generateSizes(count, minSize, maxSize);

        System.out.println("Генерация " + count + " наборов данных...");

        for (int i = 0; i < sizes.length; i++) {
            int size = sizes[i];
            int[] data = new int[size];
            for (int j = 0; j < size; j++) {
                data[j] = random.nextInt(1_000_000);
            }
            String filename = String.format("data/dataset_%04d_%d.txt", i, size);
            saveToFile(filename, data);

            if ((i + 1) % 10 == 0) {
                System.out.println("Сгенерировано " + (i + 1) + " наборов...");
            }
        }
        System.out.println("Генерация завершена!");
    }

    private static int[] generateSizes(int count, int min, int max) {
        int[] sizes = new int[count];
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            sizes[i] = min + rand.nextInt(max - min + 1);
        }
        Arrays.sort(sizes);
        return sizes;
    }

    private static void saveToFile(String filename, int[] data) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (int value : data) {
                writer.println(value);
            }
        }
    }
}
