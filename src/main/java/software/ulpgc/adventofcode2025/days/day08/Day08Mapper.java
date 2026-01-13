package software.ulpgc.adventofcode2025.days.day08;

import software.ulpgc.adventofcode2025.core.InputMapper;
import software.ulpgc.adventofcode2025.days.day08.domain.Connection;
import software.ulpgc.adventofcode2025.days.day08.domain.Day08Data;
import software.ulpgc.adventofcode2025.days.day08.domain.JunctionBox;
import software.ulpgc.adventofcode2025.days.day08.domain.Point3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Input mapper for Day 8 that parses 3D coordinate data and constructs a network
 * of junction boxes with pre-computed connections.
 *
 * This mapper creates a complete graph where each junction box is connected to
 * every other box, with connections sorted by distance. This is useful for
 * minimum spanning tree algorithms or network optimization problems.
 */
public class Day08Mapper implements InputMapper<Day08Data> {

    /**
     * Maps input lines of 3D coordinates into a Day08Data object containing
     * junction boxes and their interconnections.
     *
     * The mapper performs two main tasks:
     * 1. Parses each line as a 3D coordinate to create junction boxes
     * 2. Generates all pairwise connections between boxes, sorted by distance
     *
     * @param lines the raw input lines, each containing comma-separated x,y,z coordinates
     * @return a Day08Data object containing all junction boxes and sorted connections
     */
    @Override
    public Day08Data map(List<String> lines) {
        List<JunctionBox> boxes = parseBoxes(lines);
        List<Connection> connections = prepareConnections(boxes);
        return new Day08Data(boxes, connections);
    }

    /**
     * Parses input lines into a list of JunctionBox objects.
     * Each line should contain three comma-separated values representing x, y, z coordinates.
     * Empty lines are skipped, and each box is assigned a sequential ID based on its line number.
     *
     * Example input line: "1.5, 2.3, 4.7"
     * Result: JunctionBox with id=lineNumber and position=(1.5, 2.3, 4.7)
     *
     * @param lines the raw input lines containing coordinate data
     * @return a list of JunctionBox objects, one per valid input line
     */
    private List<JunctionBox> parseBoxes(List<String> lines) {
        List<JunctionBox> boxes = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;

            // Split comma-separated coordinates
            String[] coords = line.split(",");

            // Create junction box with sequential ID and 3D position
            boxes.add(new JunctionBox(
                    i,
                    new Point3D(
                            Double.parseDouble(coords[0]),
                            Double.parseDouble(coords[1]),
                            Double.parseDouble(coords[2])
                    )
            ));
        }
        return boxes;
    }

    /**
     * Generates all possible connections between junction boxes in a complete graph.
     *
     * For n junction boxes, this creates n*(n-1)/2 connections, where each connection
     * represents a bidirectional link between two boxes with its Euclidean distance.
     * The resulting connections are sorted by distance in ascending order, which is
     * useful for greedy algorithms like Kruskal's or Prim's for minimum spanning trees.
     *
     * Algorithm:
     * 1. For each pair of boxes (i, j) where i < j:
     *    - Create a Connection with both boxes and their distance
     * 2. Sort all connections by distance
     *
     * @param boxes the list of junction boxes to connect
     * @return a sorted list of all pairwise connections, ordered by distance (ascending)
     */
    private List<Connection> prepareConnections(List<JunctionBox> boxes) {
        List<Connection> connections = new ArrayList<>();

        // Generate all unique pairs (avoiding duplicates and self-connections)
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = i + 1; j < boxes.size(); j++) {
                JunctionBox a = boxes.get(i);
                JunctionBox b = boxes.get(j);

                // Create connection with calculated distance
                connections.add(new Connection(a, b, a.distanceTo(b)));
            }
        }

        // Sort connections by distance (Connection must implement Comparable)
        Collections.sort(connections);
        return connections;
    }
}