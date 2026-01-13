package software.ulpgc.adventofcode2025.Utils;

public class GridUtils {
    public static char[][] copy(char[][] original) {
        if (original == null) return null;
        char[][] copy = new char[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }
}