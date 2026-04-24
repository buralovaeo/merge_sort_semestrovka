package MergeSort;

public class MergeSort {
    private long iterationCount;

    public MergeSort() {
        this.iterationCount = 0;
    }

    public long getIterationCount() {
        return iterationCount;
    }

    public void resetIterationCount() {
        this.iterationCount = 0;
    }

    public void sort(int[] array) {
        if (array == null || array.length < 2) return;
        int[] temp = new int[array.length];
        mergeSort(array, temp, 0, array.length - 1);
    }

    private void mergeSort(int[] array, int[] temp, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(array, temp, left, mid);
            mergeSort(array, temp, mid + 1, right);
            merge(array, temp, left, mid, right);
        }
    }

    private void merge(int[] array, int[] temp, int left, int mid, int right) {
        // Копирование в временный массив
        for (int i = left; i <= right; i++) {
            temp[i] = array[i];
            iterationCount++; // операция копирования
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        // Слияние двух половин
        while (i <= mid && j <= right) {
            iterationCount++; // сравнение
            if (temp[i] <= temp[j]) {
                array[k++] = temp[i++];
            } else {
                array[k++] = temp[j++];
            }
            iterationCount++; // запись в массив
        }

        // Копирование остатка левой части
        while (i <= mid) {
            array[k++] = temp[i++];
            iterationCount++; // запись в массив
        }

        // Правая часть уже на месте
    }
}
