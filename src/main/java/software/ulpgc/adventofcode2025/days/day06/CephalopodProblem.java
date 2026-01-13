package software.ulpgc.adventofcode2025.days.day06;

import java.util.List;

public record CephalopodProblem (List<Long> numers, Operator operator){
    public long solve(){
        return operator.apply(numers);
    }

}
