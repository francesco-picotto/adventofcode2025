package software.ulpgc.adventofcode2025.day06;
import java.util.*;

public class ReverseVerticalAnalyzer extends AbstractWorksheetAnalyzer {
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
            String columnDigits = getColumnPart(lines.subList(0, lines.size() - 1), j);
            if(!columnDigits.isEmpty()){
                nums.add(Long.parseLong(columnDigits));
            }
        }
        char opChar = lines.get(lines.size() - 1).substring(start, end).trim().charAt(0);
        return new CephalopodProblem(nums, Operator.fromChar(opChar));
    }

}

