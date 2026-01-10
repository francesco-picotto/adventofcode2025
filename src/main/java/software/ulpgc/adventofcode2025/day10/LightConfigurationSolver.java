package software.ulpgc.adventofcode2025.day10;
import java.util.*;

public class LightConfigurationSolver implements MachineSolver {
    @Override
    public long solve(Machine machine) {
        record State(List<Boolean> lights, int presses){}

        Set<List<Boolean>> visited = new HashSet<>();

        Queue<State> queue = new ArrayDeque<>();

        List<Boolean> initial = Collections.nCopies(machine.targetLights().size(), false);
        queue.add(new State(initial, 0));
        visited.add(initial);

        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.lights.equals(machine.targetLights())) return current.presses;

            for(Set<Integer> button : machine.buttons()) {
                List<Boolean> nextState = toggle(current.lights, button);
                if (visited.add(nextState)) {
                    queue.add(new State(nextState, current.presses + 1));
                }
            }
        }
        return 0;
    }

    private List<Boolean> toggle(List<Boolean> current, Set<Integer> buttonIndices) {
        List<Boolean> next = new ArrayList<>(current);
        for (int idx : buttonIndices) {
            if (idx < next.size()) next.set(idx, !next.get(idx));
        }
        return next;
    }


}
