package csc650project;
import Crypto.Crypto;

public class Csc650project {

    public static void main(String[] args) {
        int[] plaintext = new int[64];
        //int[] key = new int[64];
        //int[] ciphertext = new int[64];
        
        int[] key = { 0, 0, 0, 1, 0, 0, 1, 1, 
                    0, 0, 1, 1, 0, 1, 0, 0, 
                    0, 1, 0, 1, 0, 1, 1, 1, 
                    0, 1, 1, 1, 1, 0, 0, 1, 
                    1, 0, 0, 1, 1, 0, 1, 1, 
                    1, 0, 1, 1, 1, 1, 0, 0, 
                    1, 1, 0, 1, 1, 1, 1, 1,
                    1, 1, 1, 1, 0, 0, 0, 1 };
        
        for (int i = 0; i < 64; i++) {
            plaintext[i] = i + 1;
            //key[i] = i + 1;
        }
        
        new_key = Crypto.DES(plaintext, key);
     
        System.out.println();
    }
    
     public static void display_key(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if ((i % 6) == 0)
                System.out.print(" ");
            
            System.out.print(array[i]);
        }
    }
    
    public static void display(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if ((i % 8) == 0)
                System.out.println();
            
            if (array[i] < 10)
                System.out.print(array[i] + "  ");
            else
                System.out.print(array[i] + " ");
        }
    }
}