package software.ulpgc.adventofcode2025.days.day12.strategy;

import software.ulpgc.adventofcode2025.days.day12.domain.Shape;

import java.util.List;
import java.util.Set;

/**
 * Strategy interface for determining if shapes can fit into a rectangular region.
 * Implementations of this interface provide different algorithms for solving the
 * packing puzzle problem, where multiple shapes must be arranged to completely
 * or optimally fill a given area.
 */
public interface FittingStrategy {

    /**
     * Determines whether a collection of shapes (with their variations) can fit
     * into a rectangular region of specified dimensions.
     *
     * The variations parameter allows each shape to be placed in multiple orientations
     * (rotations and flips), giving the algorithm flexibility in arranging the pieces.
     *
     * @param width the width of the target region
     * @param height the height of the target region
     * @param variations a list where each element is a set of all variations for one shape
     *                   that needs to be placed in the region
     * @return true if all shapes can be successfully arranged within the region bounds,
     *         false otherwise
     */
    boolean canFit(int width, int height, List<Set<Shape>> variations);
}