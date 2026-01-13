package software.ulpgc.adventofcode2025.days.day09;
import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.infrastructure.Day09Mapper;


public class Main {
    public static void main(String[] args){

        var input = new InputProvider("src/main/resources/inputs").provide("input_day09.txt", new Day09Mapper());

        System.out.println("result1: " + new GridProcessor(new MaxRectangleAnalyzer()).solve(input));
        System.out.println("result2: " + new GridProcessor(new LoopRectangleAnalyzer()).solve(input));
    }
}
