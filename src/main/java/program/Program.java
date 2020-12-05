package program;

import tools.Iterator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please, enter path to file which we need read");
        try(Iterator it = new Iterator(sc.nextLine())){
            while(it.hasNext()){
                System.out.println(it.next());
                sc.nextLine();
            }
        }
        catch (IOException | UncheckedIOException e){
            System.out.println("ERROR: some problems with file reading: "+e.getMessage());
        }
    }
}
