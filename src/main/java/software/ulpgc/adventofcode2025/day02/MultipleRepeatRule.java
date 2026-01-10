package software.ulpgc.adventofcode2025.day02;

import java.util.ArrayList;
import java.util.List;

public class MultipleRepeatRule implements InvalidRule{

    private static final int[] PRIMES = {
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47,
            53, 59, 61, 67, 71, 73, 79, 83, 89, 97
    };

    @Override
    public long evaluate(long id){
        String idstr = String.valueOf(id);
        for (int p : PRIMES){
            if(idstr.length() % p == 0){
                if(isRepeated(idstr, p)) return id;
            }
        }
        return 0;
    }

    private boolean isRepeated(String idstr, int p) {
        int partLen = idstr.length() / p;
        String firstPart = idstr.substring(0, partLen);

        for(int i = 0; i < p; i++){
            int start = i * partLen;
            if(!idstr.substring(start, start + partLen).equals(firstPart)) return false;
        }
        return true;
    }
}
