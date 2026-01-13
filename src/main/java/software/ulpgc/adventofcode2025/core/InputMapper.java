package software.ulpgc.adventofcode2025.core;

import java.util.List;

public interface InputMapper<T> {
    T map(List<String> lines);
}