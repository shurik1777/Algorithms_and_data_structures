package TreeSecond;

import java.io.*;
import java.util.*;

/**
 * Главный класс, выполняющий различные операции с данными в красно-черном дереве.
 */
public class Main {
    /**
     * Главный метод программы, который читает данные из файла, выполняет операции вставки, поиска и удаления в красно-черном дереве,
     * и выводит результаты на консоль.
     *
     * @param args Параметры командной строки (не используются в этой программе)
     */
    public static void main(String[] args) {
        // Создаем массив для хранения данных из файла
        Integer[] array = new Integer[10000];
        BufferedReader reader = null;
        try {
            // Открываем файл для чтения
            reader = new BufferedReader(new FileReader("data.txt"));
            int indx = 0;

            // Читаем данные из файла и сохраняем их в массив
            while (reader.ready()) {
                array[indx] = Integer.parseInt(reader.readLine());
                indx++;
            }

            // Создаем экземпляр красно-черного дерева
            RedBlackTree redBlackTree = new RedBlackTree();
            long start = 0;
            long time = 0;
            long cntOperations = 0;

            try {
                // Выполняем операцию вставки для каждого элемента массива
                for (int num : array) {
                    start = System.nanoTime();
                    redBlackTree.insert(num);
                    cntOperations += RedBlackTree.COUNT_OF_OPERATIONS;
                    RedBlackTree.COUNT_OF_OPERATIONS = 0;
                    time += System.nanoTime() - start;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            // Выводим среднее время и среднее количество операций для вставки элементов
            System.out.println("Среднее время для вставки элементов: " + time / array.length + " наносекунд");
            System.out.println("Среднее количество операций для вставки элементов: " + cntOperations / array.length);

            time = 0;
            cntOperations = 0;

            // Выполняем операцию поиска случайных элементов 100 раз
            for (int i = 0; i < 100; i++) {
                int num = array[(int) (Math.random() * array.length)];
                start = System.nanoTime();
                redBlackTree.contains(num);
                cntOperations += RedBlackTree.COUNT_OF_OPERATIONS;
                RedBlackTree.COUNT_OF_OPERATIONS = 0;
                time += System.nanoTime() - start;
            }

            // Выводим среднее время и среднее количество операций для поиска элементов
            System.out.println("Среднее время для поиска элементов: " + time / 100 + " наносекунд");
            System.out.println("Среднее количество операций для поиска элементов: " + cntOperations / 100);

            time = 0;
            cntOperations = 0;

            // Создаем список и добавляем в него все элементы массива
            ArrayList<Integer> arrayList = new ArrayList<>();
            Collections.addAll(arrayList, array);

            // Выполняем операцию удаления случайных элементов из списка 100 раз
            for (int i = 0; i < 100; i++) {
                int index = (int) (Math.random() * arrayList.size());
                int num = arrayList.get(index);
                start = System.nanoTime();
                redBlackTree.delete(num);
                cntOperations += RedBlackTree.COUNT_OF_OPERATIONS;
                RedBlackTree.COUNT_OF_OPERATIONS = 0;
                time += System.nanoTime() - start;
            }

            // Выводим среднее время и среднее количество операций для удаления элементов
            System.out.println("Среднее время для удаления элементов: " + time / 100 + " наносекунд");
            System.out.println("Среднее количество операций для удаления элементов: " + cntOperations / 100);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
