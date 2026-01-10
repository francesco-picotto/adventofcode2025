package software.ulpgc.adventofcode2025.day02;

import java.util.ArrayList;
import java.util.List;

public class MultipleRepeatRule implements IdRule{

    private static final int[] PRIMES = {
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47,
            53, 59, 61, 67, 71, 73, 79, 83, 89, 97
    };

    @Override
    public long evaluate(long id){
        String idStr = String.valueOf(id);
        int length = idStr.length();
        for (int p : PRIMES){
            if(length % p == 0){
                if(isRepeated(idStr, p)) return id;
            }
        }
        return 0;
    }

    private boolean isRepeated(String idStr, int p) {
        int partLen = idStr.length() / p;
        String firstPart = idStr.substring(0, partLen);

        for(int i = 0; i < p; i++){
            int start = i * partLen;
            if(!idStr.startsWith(firstPart, i*partLen)) return false;
        }
        return true;
    }
}
