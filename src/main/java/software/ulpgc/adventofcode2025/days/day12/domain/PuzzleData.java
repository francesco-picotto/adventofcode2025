package software.ulpgc.adventofcode2025.days.day12.domain;

import java.util.List;

/**
 * Represents the complete puzzle data containing shape definitions and region requests.
 * This immutable record encapsulates all the information needed to solve a packing puzzle,
 * including the shape library and the target regions to fill.
 *
 * @param shapes a list of unique shape templates available for packing
 * @param regions a list of region requests, each specifying dimensions and which shapes
 *                (and how many of each) must be packed into that region
 */
public record PuzzleData(List<Shape> shapes, List<RegionRequest> regions) {
}