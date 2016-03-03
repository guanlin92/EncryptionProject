package Crypto;

public class Crypto {
    public static int[] DES(int[] plaintext, int[] key) {
        
        if (validateInput(plaintext, key)) {
            //return initial_permutation(plaintext);
            //return final_permutation(plaintext);
            //return final_permutation(initial_permutation(plaintext));
            
            return initial_key_permutation(key);
        }
        
        return null;
    }
    
    private static int[] generate_per_round_key(int[] key, int round) {
        int[] new_key = new int[48];
        
        
        
        return new_key;
    }
    
    private static int[] initial_key_permutation(int[] key) {
        int[] new_key = new int[56];
        
        int j = 0;
        for (int i = 0; i < key.length; i++) {
            if (((i + 1) % 8) != 0)
                new_key[j++] = key[i];
        }
        
        return new_key;
    }
    
    private static int[] shift_left(int[] key, int num_to_shift) {
        
        return key;
    }
    
    private static int[] initial_permutation(int[] input) {
        int i, j, row, col_offset;
        int[] permuted = new int [64];

        
        
        col_offset = 1;
        for (i = 7; i < 64; i += 8) {
            row = i;
            
            // reset offset for 2nd haft
            if (i == 39)
                col_offset = 0;
            
            for (j = col_offset; j < 64; j += 8) {
                permuted[row--] = input[j];
            }
 
            col_offset += 2;
        }
        
        
        /*
        l = 1;
        for (k = 7; k < 32; k += 8) {
            j = k;
            for (i = l; i < 64; i += 8) {
                permuted[j--] = input[i];
            }
            l += 2;
        }
        
        l = 0;
        for (k = 39; k < 64; k += 8) {
            j = k;
            for (i = l; i < 64; i += 8) {
                permuted[j--] = input[i];
            }
            l += 2;
        }
        */
        
        /*
        j = 7;
        for (i = 1; i < 64; i += 8) {
            permuted[j--] = input[i];
        }
        
        j = 7 + 8;
        for (i = 3; i < 64; i += 8) {
            permuted[j--] = input[i];
        }
        
        j = 7 + 8 + 8;
        for (i = 5; i < 64; i += 8) {
            permuted[j--] = input[i];
        } 
        
        j = 7 + 8 + 8 + 8;
        for (i = 7; i < 64; i += 8) {
            permuted[j--] = input[i];
        }
        
        j = 39;
        for (i = 0; i < 64; i += 8) {
            permuted[j--] = input[i];
        }
        
        j = 39 + 8;
        for (i = 2; i < 64; i += 8) {
            permuted[j--] = input[i];
        }
        
        j = 39 + 8 + 8;
        for (i = 4; i < 64; i += 8) {
            permuted[j--] = input[i];
        }
        
        j = 39 + 8 + 8 + 8;
        for (i = 6; i < 64; i += 8) {
            permuted[j--] = input[i];
        }
        */
        
        return permuted;
    }
    
    
    
    private static int[] final_permutation(int[] input) {
        int i, j, col;
        int[] permuted = new int [64];
        
        i = 0;
        for (col = 57; col < 64; col += 2) {
            for (j = col; j > -1; j -= 8) {
                permuted[j] = input[i++];  
            }
            
            if (col == 63)
                col = 54;
        }
        
        
        /*
        i = 0;
        for (col = 57; col < 64; col += 2) {
            for (j = col; j > -1; j -= 8) {
                permuted[j] = input[i++];
            }
        }
        
        for (col = 56; col < 64; col += 2) {
            for (j = col; j > -1; j -= 8) {
                permuted[j] = input[i++];
            }
        }
        */
        
        /*
        i = 0;
        for (j = 57; j > -1; j -= 8) {
            permuted[j] = input[i++];
        }
        
        for (j = 57 + 2; j > -1; j -= 8) {
            permuted[j] = input[i++];
        }
        
        for (j = 57 + 2 + 2; j > -1; j -= 8) {
            permuted[j] = input[i++];
        }
        
        for (j = 57 + 2 + 2 + 2; j > -1; j -= 8) {
            permuted[j] = input[i++];
        }
        
        for (j = 56; j > -1; j -= 8) {
            permuted[j] = input[i++];
        }
        
        for (j = 56 + 2; j > -1; j -= 8) {
            permuted[j] = input[i++];
        }
        
        for (j = 56 + 2 + 2; j > -1; j -= 8) {
            permuted[j] = input[i++];
        }
        
        for (j = 56 + 2 + 2 + 2; j > -1; j -= 8) {
            permuted[j] = input[i++];
        }
        */
        
        return permuted;
    }
    
    private static boolean validateInput(int[] plaintext, int[] key) {
        if (plaintext.length != 64 || key.length != 64) {
            System.out.println("Inputs are not 64 bits long!");
            return false;
        }
        return true;
    }
}
