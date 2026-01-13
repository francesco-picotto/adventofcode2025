package software.ulpgc.adventofcode2025.days.day12.domain;

import java.util.List;

/**
 * Represents a puzzle region request specifying the target dimensions and required shapes.
 * This immutable record defines a packing problem: fitting a specified collection of shapes
 * into a rectangular region of given width and height.
 *
 * @param w the width of the target region
 * @param h the height of the target region
 * @param counts a list where each element indicates how many instances of the corresponding
 *               shape (by index) must be packed into the region
 */
public record RegionRequest(int w, int h, List<Integer> counts) {
}