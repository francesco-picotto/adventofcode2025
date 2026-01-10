package software.ulpgc.adventofcode2025.day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<JunctionBox> boxes = loadInput("src/main/java/software/ulpgc/adventofcode2025/day08/input_day08.txt");
        if(boxes.isEmpty()) return;

        List<Connection> connections = prepareConnections(boxes);

        long result1 = new BasicCircuitAnalyzer().analyze(boxes, connections);
        long result2 = new AdvanceCircuitAnalyzer().analyze(boxes, connections);

        System.out.println("result1: " + result1);
        System.out.println("result2: " + result2);
    }

    private static List<Connection> prepareConnections(List<JunctionBox> boxes) {
        List<Connection> connections = new ArrayList<>();
        for(int i = 0; i < boxes.size(); i++){
            for(int j = i + 1; j < boxes.size(); j++){
                JunctionBox a = boxes.get(i);
                JunctionBox b = boxes.get(j);
                connections.add(new Connection(a, b, a.distanceTo(b)));
            }
        }
        Collections.sort(connections);
        return connections;
    }

    private static List<JunctionBox> loadInput(String file) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(file));
        List<JunctionBox> boxes = new ArrayList<>();

        for(int i = 0; i < lines.size(); i++){
            String line = lines.get(i).trim();
            if(line.isEmpty()) continue;

            String[] coords = line.split(",");
            boxes.add(new JunctionBox(
                    i,
                    new Point3D(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2]))
            ));

        }
        return boxes;
    }



}
