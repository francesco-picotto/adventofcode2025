package software.ulpgc.adventofcode2025.day10;
import java.util.*;

public class JoltageSolver implements MachineSolver {
    private final Map<List<Integer>, Long> memo = new HashMap<>();
    @Override
    public long solve(Machine machine) {
        memo.clear();
        return compute(new ArrayList<>(machine.targetJoltage()), machine.buttons());
    }

    private long compute(List<Integer> target, List<Set<Integer>> buttons) {
        if (target.stream().allMatch(v -> v == 0)) return 0;
        if(target.stream().anyMatch(v -> v < 0)) return Long.MAX_VALUE;
        if(memo.containsKey(target)) return memo.get(target);

        Long minPresses = Long.MAX_VALUE;
        int numButtons = buttons.size();

        for (int i = 0; i < (1 << numButtons); i++) {
            List<Integer> nextTarget = new ArrayList<>(target);
            int currentStepPresses = 0;

            for(int b=0; b < numButtons; b++){
                if((( i >> b) & 1) == 1){
                    currentStepPresses++;
                    for(int idx : buttons.get(b)){
                        if(idx < nextTarget.size())
                            nextTarget.set(idx, nextTarget.get(idx) - 1);
                    }
                }
            }
            if (nextTarget.stream().allMatch(v -> v >= 0 && v % 2 == 0)){
                List<Integer> halved = nextTarget.stream().map(v -> v / 2).toList();
                long subResult = compute(halved, buttons);
                if(subResult != Long.MAX_VALUE){
                    minPresses = Math.min(minPresses, currentStepPresses + 2 * subResult);
                }

            }
        }

        memo.put(target, minPresses);
        return minPresses;


    }
}
