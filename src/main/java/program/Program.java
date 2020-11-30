package program;

import tools.Iterator;

import java.util.Scanner;

public class Program {
    public static void main(String[] args){
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("Please enter the path to file we need to read.");

            Iterator it = new Iterator(sc.nextLine());

            if (!it.startReading()) {
                System.out.println(it.FILE_READING_ERROR);
                return;
            }

            while (it.isAlive()) {
                System.out.println(it.getNextString());
                sc.nextLine();
            }
        }
        catch (InterruptedException e){
            return;
        }
    }
}
