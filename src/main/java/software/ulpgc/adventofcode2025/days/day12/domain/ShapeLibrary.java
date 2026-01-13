package software.ulpgc.adventofcode2025.days.day12.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility class for managing shape variations in puzzle solving.
 * This record stores pre-computed variations of shapes to optimize packing algorithms
 * by avoiding redundant transformation calculations.
 *
 * @param allVariations a list where each element is a set of all variations for a corresponding shape
 */
public record ShapeLibrary(List<Set<Shape>> allVariations) {

    /**
     * Generates all unique variations of a base shape through rotations and flips.
     * This method creates all possible orientations by applying four 90-degree rotations
     * and flipping after each rotation, collecting all unique normalized results.
     *
     * The algorithm produces up to 8 distinct variations (4 rotations × 2 flip states),
     * though shapes with symmetry may yield fewer unique variations. Each variation
     * is normalized and can be directly used for puzzle placement.
     *
     * Example: An L-shaped piece will generate 8 unique orientations that can be
     * tried when fitting the piece into a puzzle region.
     *
     * @param base the original shape to generate variations from
     * @return a Set containing all unique variations of the base shape,
     *         including rotated and flipped versions
     */
    public static Set<Shape> generateVariation(Shape base) {
        Set<Shape> variants = new HashSet<>();
        Shape current = base;

        // Generate 4 rotations with flips after each
        for (int i = 0; i < 4; i++) {
            current = current.rotate();
            variants.add(current);
            variants.add(current.flip());
        }
        return variants;
    }
}