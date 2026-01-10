package software.ulpgc.adventofcode2025.day06;

import java.util.List;

public class CephalopodProblem {
    private final List<Long> numbers;
    private final char operator;

    public CephalopodProblem(List<Long> numbers, char operator) {
        this.numbers = numbers;
        this.operator = operator;
    }

    public long solve(){
        if(operator == '+'){
            return numbers.stream().mapToLong(Long::longValue).sum();
        }
        else return numbers.stream().reduce(1L, (a,b) -> a*b);
    }

}
