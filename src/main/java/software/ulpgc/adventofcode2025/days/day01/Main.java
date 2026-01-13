package software.ulpgc.adventofcode2025.days.day01;
import software.ulpgc.adventofcode2025.core.InputProvider;


public class Main {
    public static void main(String[] args){
        var input = new InputProvider("src/main/resources/inputs").provide("input_day01.txt", lines -> lines);
        System.out.println("Part one result: " + new PasswordProcessor(new BasicStrategy()).solve(input));
        System.out.println("Part two result: " + new PasswordProcessor(new AdvancedStrategy()).solve(input));
    }

}
