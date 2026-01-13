package software.ulpgc.adventofcode2025.days.day03;
import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.days.day03.rule.AdvancedBankRule;
import software.ulpgc.adventofcode2025.days.day03.rule.SimpleBankRule;
import software.ulpgc.adventofcode2025.days.day03.service.BankProcessor;

public class Main {
    /**
     * Entry point of the application that processes bank codes to extract values.
     *
     * Reads the input file containing bank code strings, then processes them using
     * two different extraction rules: SimpleBankRule (extracts 2 digits) and
     * AdvancedBankRule (extracts 12 digits). Each rule identifies specific digits
     * based on finding maximum values within constrained ranges.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args){

        var input = new InputProvider("src/main/resources/inputs").provide("input_day03.txt", lines -> lines);

        System.out.println("Total 1: " + new BankProcessor(new SimpleBankRule()).solve(input));
        System.out.println("Total 2: " + new BankProcessor(new AdvancedBankRule()).solve(input));


    }
}