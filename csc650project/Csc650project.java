package csc650project;
import Crypto.Crypto;

public class Csc650project {

    public static void main(String[] args) {
        int[] plaintext = new int[64];
        int[] key = new int[64];
        int[] ciphertext = new int[64];
        
        for (int i = 0; i < 64; i++) {
            plaintext[i] = i + 1;
            key[i] = i + 1;
        }
        
        //ciphertext = Crypto.DES(plaintext, key);
        key = Crypto.DES(plaintext, key);
        
        for (int i = 0; i < 56; i++) {
            System.out.print(key[i] + " ");
        }
        
        /*
        for (int i = 0; i < 64; i++) {
            
            if ((i % 8) == 0)
                System.out.println();
            
            System.out.print(ciphertext[i] + " ");
            
        }
        */
        
        System.out.println();
    }
    
}