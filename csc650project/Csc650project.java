package csc650project;
import Crypto.Crypto;

public class Csc650project {

    public static void main(String[] args) {
        //test_DES();
        test_ECB();
        //test_CBC();
    }
    
    public static void test_CBC() {
        String plaintext1 = "I LOVE SECURITY";
        String plaintext2 = "SECURITYSECURITY";
        String key = "ABCDEFGH";
        String IV = "ABCDEFGH";
        
        int[] ciphertext = Crypto.CBC(plaintext2, key, IV);
        
        display(ciphertext);
        
    }
    
    public static void test_ECB() {
        String plaintext1 = "I LOVE SECURITY";
        String plaintext2 = "GO GATORS!";
        String plaintext3 = "GO";
        String key = "ABCDEFGH";
        
        int[] ciphertext = Crypto.ECB(plaintext2, key);
        
        display(ciphertext);
        
    }
    
    public static void test_DES() {
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
            //if ( i % 8 == 0)
            //    System.out.print(" ");
            
            System.out.print(array[i] + " ");
        }
    }
}