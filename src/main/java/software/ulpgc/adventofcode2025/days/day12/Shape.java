package software.ulpgc.adventofcode2025.days.day12;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record Shape(Set<Point> points) {

    public Shape normalize() {
        int minX = points.stream().mapToInt(Point::x).min().orElse(0);
        int minY = points.stream().mapToInt(Point::y).min().orElse(0);
        return new Shape(points.stream()
                .map(p -> p.translate(-minX, -minY))
                .collect(Collectors.toSet()));
    }

    public Set<Shape> getVariations() {
        Set<Shape> variants = new HashSet<>();
        Shape current = this;
        for (int i = 0; i < 4; i++) {
            current = current.rotate().normalize();
            variants.add(current);
            variants.add(current.flip().normalize());
        }
        return variants;
    }

    public Shape rotate(){
        return new Shape(points.stream().map(Point::rotate).collect(Collectors.toSet()));
    }

    public Shape flip(){
        return new Shape(points.stream().map(Point::flip).collect(Collectors.toSet()));
    }
}
