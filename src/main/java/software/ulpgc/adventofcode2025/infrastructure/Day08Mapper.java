package software.ulpgc.adventofcode2025.infrastructure;

import software.ulpgc.adventofcode2025.core.InputMapper;
import software.ulpgc.adventofcode2025.days.day08.Connection;
import software.ulpgc.adventofcode2025.days.day08.Day08Data;
import software.ulpgc.adventofcode2025.days.day08.JunctionBox;
import software.ulpgc.adventofcode2025.days.day08.Point3D;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day08Mapper implements InputMapper<Day08Data> {
    @Override
    public Day08Data map(List<String> lines) {
        List<JunctionBox> boxes = parseBoxes(lines);
        List<Connection> connections = prepareConnections(boxes);
        return new Day08Data(boxes, connections);
    }

    private List<JunctionBox> parseBoxes(List<String> lines) {
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

    private List<Connection> prepareConnections(List<JunctionBox> boxes) {
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
}