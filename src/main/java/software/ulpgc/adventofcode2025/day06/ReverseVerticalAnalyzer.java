package software.ulpgc.adventofcode2025.day06;
import java.util.*;

public class ReverseVerticalAnalyzer implements MathWorksheetAnalyzer{
    @Override
    public long analyze(List<String> lines){
        List<CephalopodProblem> problems = new ArrayList<>();
        int width = lines.get(0).length();

        int endCol = width;
        for(int j = width-1; j >= -1; j--){
            if(j == -1 || isColumnEmpty(lines, j)){
                if(endCol > j+1){
                    problems.add(extractVerticalProblem(lines, j + 1, endCol));
                }
                endCol = j;
            }
        }
        return problems.stream().mapToLong(CephalopodProblem::solve).sum();
    }

    private CephalopodProblem extractVerticalProblem(List<String> lines, int start, int end) {
        List<Long> nums = new ArrayList<>();
        char op = ' ';

        for(int j = end - 1; j >= start; j--){
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < lines.size() - 1; i++){
                char c = (j < lines.get(i).length()) ? lines.get(i).charAt(j) : ' ';
                if(c != ' ') sb.append(c);
            }
            if (sb.length() > 0) nums.add(Long.parseLong(sb.toString()));
        }

        String lastLinePart = lines.get(lines.size() - 1).substring(start, end).trim();
        op = lastLinePart.charAt(0);

        return new CephalopodProblem(nums, op);
    }

    private boolean isColumnEmpty(List<String> lines, int col) {
        for(String line: lines){
            if(col < line.length() && line.charAt(col) != ' ') return false;
        }
        return true;
    }


}

