package TreeSecond;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Класс GenerateData используется для генерации случайных числовых данных и записи их в текстовый файл.
 */
public class GenerateData {
    /**
     * Главный метод, который выполняет генерацию данных и запись их в файл.
     *
     * @param args Параметры командной строки (не используются в этой программе)
     */
    public static void main(String[] args) {
        try {
            // Создаем буферизированный писатель для записи данных в файл "data.txt"
            BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"));

            // Генерируем и записываем 10 000 случайных чисел в файл
            for (long i = 0; i < 10000; i++) {
                int num = (int) (Math.random() * 10000);
                writer.write(Integer.toString(num));
                writer.write("\n");
            }

            // Сбрасываем буфер и закрываем файл
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
