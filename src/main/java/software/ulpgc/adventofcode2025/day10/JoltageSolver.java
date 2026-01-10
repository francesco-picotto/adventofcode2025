package software.ulpgc.adventofcode2025.day10;
import java.util.*;

public class JoltageSolver implements MachineSolver {
    private static final long INF = Long.MAX_VALUE;
    private final Map<List<Integer>, Long> memo = new HashMap<>();

    @Override
    public long solve(Machine machine) {
        memo.clear();
        return compute(new ArrayList<>(machine.targetJoltage()), machine.buttons());
    }

    private long compute(List<Integer> target, List<Set<Integer>> buttons) {
        if (isTargetReached(target)) return 0;
        if(isInvalid(target)) return INF;
        if(memo.containsKey(target)) return memo.get(target);

        Long minPresses = INF;
        int numButtons = buttons.size();

        for (int i = 0; i < (1 << numButtons); i++) {
            List<Integer> nextState = applyButtons(target, buttons, i);

            if(canDivideByTwo(nextState)){
                long subResult = compute(halveState(nextState), buttons);

                if(subResult != INF){
                    int currentStepPresses = Integer.bitCount(i);
                    minPresses = Math.min(minPresses, currentStepPresses + 2 * subResult);
                }
            }
        }

        memo.put(target, minPresses);
        return minPresses;


    }


    private boolean isTargetReached(List<Integer> target) {
        return target.stream().allMatch(v -> v == 0);
    }

    private boolean isInvalid(List<Integer> target) {
        return target.stream().anyMatch(v -> v < 0);
    }

    private boolean canDivideByTwo(List<Integer> state) {
        return state.stream().allMatch(v -> v >= 0 && v % 2 == 0);
    }

    private List<Integer> halveState(List<Integer> state) {
        return state.stream().map(v -> v / 2).toList();
    }

    private List<Integer> applyButtons(List<Integer> current, List<Set<Integer>> buttons, int bitmask) {
        List<Integer> next = new ArrayList<>(current);
        for (int b = 0; b < buttons.size(); b++) {
            if (((bitmask >> b) & 1) == 1) {
                for (int idx : buttons.get(b)) {
                    if (idx < next.size()) next.set(idx, next.get(idx) - 1);
                }
            }
        }
        return next;
    }



}
