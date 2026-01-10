package software.ulpgc.adventofcode2025.day03;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<String> banks = loadInput("src/main/java/software/ulpgc/adventofcode2025/day03/input_day03.txt");

        long total1 = solve(banks, new SimpleBankRule());
        long total2 = solve(banks, new AdvancedBankRule());

        System.out.println("Total 1: " + total1);
        System.out.println("Total 2: " + total2);


    }

    private static long solve(List<String> banks, BankRule rule) {
        return banks.stream()
                .filter(bank -> !bank.isEmpty())
                .mapToLong(rule::evaluate)
                .sum();

    }

    private static List<String> loadInput(String file) throws IOException {
        return Files.readAllLines(Paths.get(file));
    }
}
