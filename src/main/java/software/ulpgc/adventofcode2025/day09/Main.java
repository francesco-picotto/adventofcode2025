package software.ulpgc.adventofcode2025.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Tile> redTiles = loadInput("src/main/java/software/ulpgc/adventofcode2025/day09/input_day09.txt");

        if(redTiles.isEmpty()) return;

        long result1 = new MaxRectangleAnalyzer().analyze(redTiles);
        long result2 = new LoopRectangleAnalyzer().analyze(redTiles);
        System.out.println("result1: " + result1);
        System.out.println("result2: " + result2);
    }

    private static List<Tile> loadInput(String file) throws IOException {

       return Files.lines(Paths.get(file))
               .map(String::trim)
               .filter(line -> !line.isEmpty())
               .map(line ->{
                   String[] coords = line.split(",");
                   return new Tile(
                           Integer.parseInt(coords[0]),
                           Integer.parseInt(coords[1])
                   );
               })
               .collect(Collectors.toList());
    }
}
