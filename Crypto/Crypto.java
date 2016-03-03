package Crypto;

public class Crypto {
    public static int[] DES(int[] plaintext, int[] key) {
        int[] round_key;
        if (validateInput(plaintext, key)) {
            
            key = permute_key(key);
            for (int i = 1; i <= 16; i++) {
            
                round_key = generate_per_round_key(key, i);
                
                for (int j = 0; j < round_key.length; j++) {
                    if ((j % 6) == 0)
                        System.out.print(" ");

                    System.out.print(round_key[j]);
                }
                
                System.out.println();
            }
        }
        
        return key;
    }

    private static int[] generate_per_round_key(int[] key, int round) {
        int[] new_key = key;
 
        if (round == 1 || round == 2 || round == 9 || round == 16) {
            new_key = shift_left(new_key, 0, new_key.length / 2 - 1, 1);
            new_key = shift_left(new_key, new_key.length / 2, 
                    new_key.length - 1, 1);
        } else {
            new_key = shift_left(new_key, 0, new_key.length / 2 - 1, 2);
            new_key = shift_left(new_key, new_key.length / 2, 
                    new_key.length - 1, 2);
        }
            
        new_key = permute_key_per_round(new_key);
 
        return new_key;
    }
    
    private static int[] permute_key(int[] key) {
        key = remove_parity_bits(key);
        int[] new_key = new int[key.length];
        int start = 50;
        int j = 0;
        int i = start;
        
        while (j < 28) {
            new_key[j++] = key[i - 1];
            i -= 7;
            if (i < 0)
                i = ++start;
        }

        start = 56;
        i = start;
        while(j < 56) {
            new_key[j++] = key[i - 1];
            i -= 7;
            if (i <= 0)
                i = --start;
            if (j == 52)
                i = 25;
        }
        
        return new_key;
    }
    
    private static int[] remove_parity_bits(int[] key) {
        int[] new_key = new int[key.length - 8];
        
        int j = 0;
        for (int i = 0; i < key.length; i++) {
            if (((i + 1) % 8) != 0)
                new_key[j++] = key[i];
        }
        
        return new_key;
    }
    
    private static int[] permute_key_per_round(int[] key) {
        int[] new_key = new int[key.length - 8];
        int[] permutation = {
            14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32};                    
        
        for (int i = 0; i < new_key.length; i++) {
            new_key[i] = key[permutation[i] - 1];
        }

        return new_key;
    }

    private static int[] shift_left(int[] key, int begin, int end, int bits_to_shift) {
        int first_bit;
        
        for (int i = 0; i < bits_to_shift; i++) {
            first_bit = key[begin];
            for (int j = begin + 1; j <= end; j++) {
                key[j - 1] = key[j];
            }
            key[end] = first_bit;
        }
        
        return key;
    }
    
    
    private static int[] permute_data_initial(int[] input) {
        int i, j, row, col_offset;
        int[] permuted = new int [input.length];   
        
        col_offset = 1;
        for (i = 7; i < input.length; i += 8) {
            row = i;
            
            // reset offset for 2nd haft
            if (i == 39)
                col_offset = 0;
            
            for (j = col_offset; j < input.length; j += 8) {
                permuted[row--] = input[j];
            }
 
            col_offset += 2;
        }
        
        return permuted;
    }
    
    private static int[] permute_data_final(int[] input) {
        int i, j, col;
        int[] permuted = new int [input.length];
        
        i = 0;
        for (col = 57; col < input.length; col += 2) {
            for (j = col; j > -1; j -= 8) {
                permuted[j] = input[i++];  
            }
            
            if (col == input.length - 1)
                col = 54;
        }
        
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