package software.ulpgc.adventofcode2025.day12;

import software.ulpgc.adventofcode2025.day11.ReactorSolver;

import java.util.Set;
import java.util.stream.Collectors;

public record Shape(Set<Point> points) {
    public Shape rotate(){
        return new Shape(points.stream().map(Point::rotate).collect(Collectors.toSet()));
    }

    public Shape flip(){
        return new Shape(points.stream().map(Point::flip).collect(Collectors.toSet()));
    }
}
