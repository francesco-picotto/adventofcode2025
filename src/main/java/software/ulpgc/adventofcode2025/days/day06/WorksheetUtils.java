package software.ulpgc.adventofcode2025.days.day06;

import java.util.List;

public class WorksheetUtils {
    public static boolean isColumnEmpty(List<String> lines, int col) {
        return lines.stream()
                .allMatch(line -> col >= line.length() || line.charAt(col) == ' ');
    }

    public static String getColumnPart(List<String> lines, int col) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            if (col < line.length() && line.charAt(col) != ' ') {
                sb.append(line.charAt(col));
            }
        }
        return sb.toString();
    }
}