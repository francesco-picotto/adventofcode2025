package software.ulpgc.adventofcode2025.days.day02;
import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.infrastructure.Day02Mapper;


public class Main {
    public static void main(String[] args){
        var input = new InputProvider("src/main/resources/inputs").provide("input_day02.txt", new Day02Mapper());
        System.out.println("Total part one: " + new IdProcessor(new SimpleRepeatRule()).solve(input));
        System.out.println("Total part two: " + new IdProcessor(new MultipleRepeatRule()).solve(input));
    }
}