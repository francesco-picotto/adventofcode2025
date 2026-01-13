package software.ulpgc.adventofcode2025.days.day12.service;

import software.ulpgc.adventofcode2025.days.day12.domain.PuzzleData;
import software.ulpgc.adventofcode2025.days.day12.domain.Shape;
import software.ulpgc.adventofcode2025.days.day12.strategy.FittingStrategy;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * Service class that processes packing puzzles using a configurable fitting strategy.
 *
 * This processor evaluates multiple region requests in parallel, determining which regions
 * can successfully accommodate their required shapes. It employs caching to optimize
 * performance by avoiding redundant computation of shape variations.
 *
 * The processor follows the Strategy pattern, delegating the actual fitting logic to
 * a FittingStrategy implementation, allowing different solving algorithms to be used.
 */
public class PackingProcessor {
    private final FittingStrategy strategy;

    /**
     * Thread-safe cache for storing pre-computed shape variations.
     * Using ConcurrentHashMap ensures thread safety when processing regions in parallel,
     * preventing race conditions during concurrent cache access.
     */
    private final Map<Shape, Set<Shape>> variationsCache = new ConcurrentHashMap<>();

    /**
     * Constructs a PackingProcessor with a specific fitting strategy.
     *
     * @param strategy the FittingStrategy implementation to use for solving packing problems
     */
    public PackingProcessor(FittingStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Solves the packing puzzle by determining how many regions can successfully fit
     * their required shapes.
     *
     * This method processes all region requests in parallel for improved performance,
     * filtering out regions that cannot accommodate their shapes due to:
     * - Insufficient area (total shape points exceed region area)
     * - Geometric constraints (shapes cannot be arranged to fit)
     *
     * The algorithm performs these steps for each region:
     * 1. Flatten the shape requirements based on counts
     * 2. Generate (or retrieve cached) variations for each required shape
     * 3. Validate basic feasibility (area check)
     * 4. Use the fitting strategy to determine if shapes can be arranged
     *
     * Thread safety: Uses parallel streams and ConcurrentHashMap to safely process
     * multiple regions concurrently.
     *
     * @param data the puzzle data containing shapes and region requests
     * @return the count of regions where all required shapes can successfully fit
     */
    public long solve(PuzzleData data) {
        return data.regions().parallelStream()
                .filter(region -> {
                    // Convert shape counts into an actual list of shapes to place
                    List<Shape> required = flattenShapes(data.shapes(), region.counts());

                    // Generate or retrieve cached variations for each required shape
                    // computeIfAbsent on ConcurrentHashMap is thread-safe
                    List<Set<Shape>> variationsList = required.stream()
                            .map(s -> variationsCache.computeIfAbsent(s, Shape::getVariations))
                            .toList();

                    // Quick feasibility check: total points must not exceed region area
                    int totalPoints = required.stream()
                            .mapToInt(s -> s.points().size())
                            .sum();
                    if (totalPoints > (region.w() * region.h())) {
                        return false; // Impossible to fit - not enough space
                    }

                    // Use the fitting strategy to determine if shapes can be arranged
                    return strategy.canFit(region.w(), region.h(), variationsList);
                })
                .count();
    }

    /**
     * Expands a shape specification with counts into a flat list of individual shapes.
     *
     * This method converts compact representation (e.g., "3 of shape A, 2 of shape B")
     * into an explicit list (e.g., [A, A, A, B, B]) for easier processing by
     * fitting algorithms.
     *
     * Example:
     * - shapes: [ShapeA, ShapeB, ShapeC]
     * - counts: [2, 0, 3]
     * - result: [ShapeA, ShapeA, ShapeC, ShapeC, ShapeC]
     *
     * @param shapes the master list of unique shape templates
     * @param counts a list indicating how many instances of each shape are required
     * @return a flattened list where each shape appears as many times as specified
     *         in its corresponding count
     */
    private List<Shape> flattenShapes(List<Shape> shapes, List<Integer> counts) {
        return IntStream.range(0, counts.size())
                .boxed()
                .flatMap(i -> Collections.nCopies(counts.get(i), shapes.get(i)).stream())
                .toList();
    }
}