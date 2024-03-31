package aisd_semestrov.random_aisd;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainRandom {
    public static void main(String [] args) throws IOException {
        File myFile = new File("myfile.txt");
        myFile.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter("myfile.txt", false));
        for (int i = 0; i<100; i++){
            String [] test = new String[2];
            int lengthPattern = 2; //random.generateRandomNumber(2, 2);
            int lendthText = random.generateRandomNumber(100, 10000);
            test[0] = random.generateRandomString(lengthPattern);
            test[1] = random.generateRandomString(lendthText);
            String strok = test[0] + " " + test[1];
            writer.write(strok);
            writer.newLine();
        }
        writer.close();
    }
}
