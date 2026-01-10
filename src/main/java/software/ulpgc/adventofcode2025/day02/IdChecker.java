package software.ulpgc.adventofcode2025.day02;

import java.util.ArrayList;
import java.util.List;

public class IdChecker {


    public long IdValidation(long id, boolean advanceMethod){
        String idstr = String.valueOf(id);
        if(!advanceMethod){
            if(idstr.length() % 2 == 0 && (idstr.substring(0, idstr.length()/2))
                    .equals(idstr.substring(idstr.length()/2)))
                return Long.parseLong(idstr);
        }
        int len = idstr.length();
        int[] primes = {
                2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47,
                53, 59, 61, 67, 71, 73, 79, 83, 89, 97
        };

        for(int p : primes){
            if(len % p ==0 ){
                List<String> substrings = splitIntoParts(idstr, p);
                if(substrings.stream().allMatch(substrings.get(0)::equals))
                    return Long.parseLong(idstr);
            }
        }
        return 0;
    }

    /* This method returns List of len/p substrings, all in equal p length */
    private List<String> splitIntoParts(String idstr, int p) {
        List<String> parts = new ArrayList<>();
        if(idstr.length() % p != 0){
            throw new IllegalArgumentException("wrong length");
        }
        int partLen = idstr.length() / p;
        for(int i = 0; i < p; i++){
            int start = i * partLen;
            parts.add(idstr.substring(i * partLen, start + partLen));
            //System.out.println("part: " + parts.get(i));
        }
        return parts;
    }
}
