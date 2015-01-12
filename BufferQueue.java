package cs.tcd.ie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class BufferQueue {

    File file;
    FileInputStream fin;
    BufferedReader in;
    FileOutputStream fout;

    BufferQueue(String filename) {
        try {
            
            // create temp file first
            file = File.createTempFile("tmp-file-",".txt");
            file.deleteOnExit();

            // setup actual file
            File f = new File(filename);

            // copy
            Files.copy(f.toPath(), file.toPath(), StandardCopyOption.COPY_ATTRIBUTES,StandardCopyOption.REPLACE_EXISTING);

            fin = new FileInputStream(file);
            in = new BufferedReader(new InputStreamReader(fin));
            fout = new FileOutputStream(file.getPath(), true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void push(byte[] array) {
        try {
            fout.write(array);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] pop(int number) {
        byte[] result = new byte[number * Constants.MAX_AVERAGE_NAME_LENGTH]; // average name will not exceed 20 chars long.
        int byteCount = 0;
        int count = 0;
        int i = 0;
        String l = "";
        try {
            // If file is empty, just return an empty array.
            if (fin.available() == 0) {
                return new byte[0];
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        try {
            while(count < number) {
                l = in.readLine();
                
                if (l == null) {
                    break; // reached end of file
                }

                byte[] line = l.getBytes();
                for (i = 0; i < line.length; i++) {
                    result[byteCount + i] = line[i];
                }
                // add \n to end
                result[byteCount + line.length] = 10; // \n in bytes
                byteCount += line.length + 1;
                count++;
            }
        }
        catch (Exception e) {
            System.out.println("byteCount: " + byteCount);
            System.out.println("count: " + count);
            System.out.println("i: " + i);
            System.out.println("l: " + l);
            System.out.println("l == null: " + l == null);
            System.out.println("l.equals(''): " + l.equals(""));
            System.out.println("l.getBytes().length: " + l.getBytes().length); 
            e.printStackTrace();
        }

        return result;
    }

}
