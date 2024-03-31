package aisd_semestrov;

import java.io.IOException;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;


public class Main {
    public static void main(String [] args) throws IOException {
        List<String> strings = Files.readAllLines(Paths.get("C:\\Users\\alsum\\IdeaProjects\\aisd\\src\\aisd_semestrov\\myfile.txt"));
        String [][] newStrings = new String[strings.size()][2];
        for (int i = 0; i < strings.size(); i++){
            newStrings[i] = strings.get(i).split(" ");
        }
        FileWriter writer = new FileWriter("resultFile.txt", true);
        for (int i = 0; i<newStrings.length; i++) {
            long timeStart = System.nanoTime();
            int j = 0;
            char[] c1 = newStrings[i][j].toCharArray();
            char[] c2 = newStrings[i][j+1].toCharArray();
            int countIterations = indexesOf(c1, c2)[1][0];
            int count = 0;
            int[] result = indexesOf(c1, c2)[0];
            for (int k : result) {
                count +=1;
                System.out.print(k + " ");
            }
            System.out.println();
            countIterations += count;
            long time = System.nanoTime() - timeStart;
            int patternLength = c1.length;
            int textLength = c2.length;
            System.out.println("Time to work: " + time/1000 + " ");
            System.out.println("Count iterations: " + countIterations);
            System.out.println("Length pattern: " + c1.length);
            System.out.println("Length text: " + c2.length);
            System.out.println();
            String resultStr = "" + time/1000 + " " + countIterations + " " + patternLength + " " + textLength;
            System.out.println(resultStr);
            writer.write(resultStr);
            writer.write("\n");
        }
        writer.close();
    }
    public static int[][] indexesOf(char[] pattern, char[] text) //pattern - образ, text - строка
    {
        int[] pfl = preficsFunction(pattern)[0];
        int [] countIterations = preficsFunction(pattern)[1];
        int countIteration = countIterations[0];
        int[] indexes = new int[text.length];
        int size = 0;
        int k = 0;
        int flag = 0;
        int j = 0;
        for (int i = 0; i < text.length; ++i)
        {
            j = i;
            while (pattern[k] != text[i] && k > 0)
            {
                flag+=1;
                k = pfl[k - 1];
            }
            if (pattern[k] == text[i])
            {
                k = k + 1;
                if (k == pattern.length)
                {
                    indexes[size] = i + 1 - k;
                    size += 1;
                    k = pfl[k - 1];
                }
            }
            else
            {
                k = 0;
            }
        }
        countIteration = countIteration + flag*j;
        int [] f = new int [1];
        f[0] = countIteration;
        int [][] result = new int [2][];
        result [0] = Arrays.copyOfRange(indexes, 0, size);
        result [1] = f;
        return result;
    }

    public static int[][] preficsFunction(char[] pattern) //функция создания префиксного массива
    {
        int[] pfl = new int[pattern.length]; // создаем массив по длине образа
        pfl[0] = 0; // для первого элемента всегда будет равно нулю, т.к. нет суффикса не совпадающего с длиной строки
        int countIterations = 0;
        int count = 0;
        for (int i = 1; i < pattern.length; ++i) // проходимся по образу
        {
            count = i;
            int k = pfl[i - 1]; // k - значение префикса предыдущего элемента
            while (pattern[k] != pattern[i] && k > 0) // подсчет длины совпадающих преффикса и суффикса
            {
                k = pfl[k - 1];
                countIterations += 1;
            }
            if (pattern[k] == pattern[i])
            {
                pfl[i] = k + 1;
            }
            else
            {
                pfl[i] = 0;
            }
        }
        countIterations = countIterations * count;
        int [] countIteration = new int [1];
        countIteration[0] = countIterations;
        int [][] array = new int [2][];
        array[0] = pfl;
        array[1] = countIteration;
        return array; //матрица функции преффиксов
    }
}
