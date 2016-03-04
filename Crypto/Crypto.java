package Crypto;

public class Crypto {
    public static int[] DES(int[] plaintext, int[] key) {
        int[] ciphertext;
        if (validate_input(plaintext, key)) {
            ciphertext = permute_data_initial(plaintext);
            ciphertext = do_rounds(ciphertext, key);
            ciphertext = swap_left_right(ciphertext);
            ciphertext = permute_data_final(ciphertext);
        
            return ciphertext;
        }
        
        return null;
    }
    
    private static int[] swap_left_right(int[] data) {
        int[] new_data = new int[data.length];
        System.arraycopy(data, 32, new_data, 0, 32);
        System.arraycopy(data, 0, new_data, 32, 32);
        
        return new_data;
    }
    
    private static int[] do_rounds(int[] data, int[] key) {
        int[] round_key;
        int[] mangled_data;
        int[] left_half = new int[32];
        int[] right_half = new int[32];
        
        key = permute_key(key);
        
        for (int i = 1; i <= 16; i++) {
            round_key = generate_per_round_key(key, i);
            
            System.arraycopy(data, 0, left_half, 0, 32);
            System.arraycopy(data, 32, right_half, 0, 32);
            
            mangled_data = mangle(right_half, 0, 
                    right_half.length - 1, round_key);
            
            mangled_data = xor(mangled_data, left_half);
         
            System.arraycopy(right_half, 0, data, 0, 32);
            System.arraycopy(mangled_data, 0, data, 32, 32);
        }
        
        return data;
    }
    
    private static int[] mangle(int[] data, int begin, int end, int[] key) {
        int[] expanded_data = new int[data.length + 16];
        int[] expanded = new int[6];
        int[] mangled;
        int i = 0;
        
        for (int j = begin; j <= end; j += 4) {
            expanded = expand(data, j, j + 3);
            for (int k = 0; k < expanded.length; k++) {
                expanded_data[i++] = expanded[k];
            }
        }
        
        expanded_data = xor(expanded_data, key);
      
        mangled = s_box(expanded_data); 
        
        mangled = permute_mangled_final(mangled);
        
        return mangled;
    }
    
    private static int[] expand(int[] data, int begin, int end) {
        int[] expanded = new int[end - begin + 3];
        int j = 1;
        
        if (begin == 0)
            expanded[0] = data[data.length - 1];
        else
            expanded[0] = data[begin - 1];
        
        for (int i = begin; i <= end; i++) {
            expanded[j++] = data[i];
        }
        
        if (end == data.length - 1)
            expanded[expanded.length - 1] = data[0];
        else
            expanded[expanded.length - 1] = data[end + 1];
        
        return expanded;
    }
    
    private static int[] permute_mangled_final(int[] data) {
        int[] new_data = new int[data.length];
        int[] permutation = {
            16, 7, 20, 21, 
            29, 12, 28, 17,
            1, 15, 23, 26, 
            5, 18, 31, 10,
            2, 8, 24, 14,
            32, 27, 3, 9, 
            19, 13, 30, 6,
            22, 11, 4, 25
        };
        
        for (int i = 0; i < new_data.length; i++) {
            new_data[i] = data[permutation[i] - 1];
        }
        
        return new_data;
    }
    
    private static int[] xor(int[] op1, int[] op2) {
        int[] result = new int[op1.length];
        
        for (int i = 0; i < op1.length; i++) {
            if (op1[i] == op2[i])
                result[i] = 0;
            else
                result[i] = 1;
        }
        
        return result;
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
    
    private static boolean validate_input(int[] plaintext, int[] key) {
        if (plaintext.length != 64 || key.length != 64) {
            System.out.println("Inputs are not 64 bits long!");
            return false;
        }
        return true;
    }
    
    private static int[] s_box(int[] data) {
        int[] new_data = new int[32];
        int[][] bit_value = new int[8][];
        int index = 0;
        int[][] data_value = new int[8][6];
        int i, j;
        int data_index = 0;
 
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 6 ; j++) {
                data_value[i][j] = data[data_index++];
            }
        }
        
        bit_value[0] = get_s_bit_value(s1, data_value[0]);
        bit_value[1] = get_s_bit_value(s2, data_value[1]);
        bit_value[2] = get_s_bit_value(s3, data_value[2]);
        bit_value[3] = get_s_bit_value(s4, data_value[3]);
        bit_value[4] = get_s_bit_value(s5, data_value[4]);
        bit_value[5] = get_s_bit_value(s6, data_value[5]);
        bit_value[6] = get_s_bit_value(s7, data_value[6]);
        bit_value[7] = get_s_bit_value(s8, data_value[7]); 
        
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 4; j++) {
                new_data[index++] = bit_value[i][j];
            }
        }
        
        return new_data;
    }
    
    private static int[] get_s_bit_value(int[][] s, int[] data_value) {
        int[] bit_value = new int[4];
        int s_row;
        int s_col;
        int s_value;
        
        s_row = data_value[0] * 2 + data_value[5];
        s_col = data_value[1] * 8 + data_value[2] * 4 +
                data_value[3] * 2 + data_value[4];
        
        s_value = s[s_row][s_col];
        
        for (int i = 3; i >= 0; i--) {     
            bit_value[i] = s_value % 2;
            s_value /= 2;
  
        }
        
        return bit_value;
    }
    
    private static final int[][] s1 = {
        {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7}, 
        {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8}, 
        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0}, 
        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
    };
    
    
    private static final int[][] s2 = {
        {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
        {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
        {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
        {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
    };
    
    private static final int[][] s3 = {
        {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
        {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
        {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
        {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
    };
    
    private static final int[][] s4 = {
        {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
        {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
        {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4,},
        {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
    };
    
    private static final int[][] s5 = {
        {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
        {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
        {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
        {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
    };
            
    private static final int[][] s6 = {
        {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
        {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
        {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
        {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
    };
  
    private static final int[][] s7 = {
        {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
        {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
        {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
        {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
    };
    
    private static final int[][] s8 = {
        {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
        {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
        {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
        {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
    };
}