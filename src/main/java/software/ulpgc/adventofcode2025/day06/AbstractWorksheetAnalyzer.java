package software.ulpgc.adventofcode2025.day06;
import java.util.List;

public abstract class AbstractWorksheetAnalyzer implements MathWorksheetAnalyzer{
    protected boolean isColumnEmpty(List<String> lines, int col) {
        return lines.stream()
                .allMatch(line -> col >= line.length() || line.charAt(col) == ' ');
    }

    protected String getColumnPart(List<String> lines, int col) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            if (col < line.length() && line.charAt(col) != ' ') {
                sb.append(line.charAt(col));
            }
        }
        return sb.toString();
    }
}
