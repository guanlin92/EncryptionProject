package csc650project;
import Crypto.Crypto;

public class Csc650project {

    public static void main(String[] args) {
        int[] ciphertext;
        
        int[] key = { 
            0, 0, 0, 1, 0, 0, 1, 1, 
            0, 0, 1, 1, 0, 1, 0, 0, 
            0, 1, 0, 1, 0, 1, 1, 1, 
            0, 1, 1, 1, 1, 0, 0, 1, 
            1, 0, 0, 1, 1, 0, 1, 1, 
            1, 0, 1, 1, 1, 1, 0, 0, 
            1, 1, 0, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 0, 0, 0, 1 
        };
        
        int[] plaintext = {
            0, 0, 0, 0, 0, 0, 0, 1, 
            0, 0, 1, 0, 0, 0, 1, 1,
            0, 1, 0, 0, 0, 1, 0, 1,
            0, 1, 1, 0, 0, 1, 1, 1, 
            1, 0, 0, 0, 1, 0, 0, 1, 
            1, 0, 1, 0, 1, 0, 1, 1, 
            1, 1, 0, 0, 1, 1, 0, 1, 
            1, 1, 1, 0, 1, 1, 1, 1
        };
   
        ciphertext = Crypto.DES(plaintext, key);
        
        display(ciphertext);
     
        System.out.println();
    }
    
    public static void display(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if ( i % 8 == 0)
                System.out.print(" ");
            
            System.out.print(array[i]);
        }
    }
}