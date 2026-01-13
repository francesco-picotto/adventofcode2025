package software.ulpgc.adventofcode2025.days.day03;
import software.ulpgc.adventofcode2025.core.InputProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args){

        var input = new InputProvider("src/main/resources/inputs").provide("input_day03.txt", lines -> lines);

        System.out.println("Total 1: " + new BankProcessor(new SimpleBankRule()).solve(input));
        System.out.println("Total 2: " + new BankProcessor(new AdvancedBankRule()).solve(input));


    }
}
