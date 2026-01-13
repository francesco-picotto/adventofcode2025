package software.ulpgc.adventofcode2025.days.day05.domain;

/**
 * Represents a range of ingredient IDs with inclusive start and end boundaries.
 *
 * This record implements Comparable to allow ranges to be sorted by their start
 * position, which is essential for the range merging algorithm used in capacity
 * estimation. The record provides methods to parse ranges from strings, check
 * containment, and calculate size.
 *
 * @param start The starting ID of the range (inclusive)
 * @param end The ending ID of the range (inclusive)
 */
public record IngredientRange(long start, long end) implements Comparable<IngredientRange>{

    /**
     * Compact constructor that validates the range parameters.
     *
     * Ensures that the start value is not greater than the end value,
     * which would represent an invalid range. This validation is performed
     * automatically when the record is constructed.
     *
     * @throws IllegalArgumentException if start is greater than end
     */
    public IngredientRange{
        if(start>end){throw new IllegalArgumentException("Start cannot be greater than end");}
    }

    /**
     * Factory method that parses a range string into an IngredientRange object.
     *
     * Parses strings in the format "start-end" (e.g., "100-200") where both
     * start and end are long integers. The hyphen is used as the delimiter.
     *
     * @param input A string representing the range in format "start-end"
     * @return An IngredientRange object with the parsed start and end values
     * @throws NumberFormatException if either part cannot be parsed as a long
     * @throws ArrayIndexOutOfBoundsException if the string doesn't contain exactly one hyphen
     */
    public static IngredientRange parse(String input) {
        String[] parts = input.split("-");
        return new IngredientRange(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }

    /**
     * Checks if a given ID falls within this range.
     *
     * An ID is considered to be contained in the range if it is greater than
     * or equal to the start and less than or equal to the end (inclusive on
     * both boundaries).
     *
     * @param id The ingredient ID to check
     * @return true if the ID is within the range (inclusive), false otherwise
     */
    public boolean contains(long id) {
        return id >= start && id <= end;
    }

    /**
     * Calculates the number of IDs contained in this range.
     *
     * The size includes both the start and end values, so a range from 100 to 200
     * has a size of 101 (not 100). The formula is: end - start + 1.
     *
     * @return The number of IDs in this range (always at least 1 for valid ranges)
     */
    public long size(){
        return end - start + 1;
    }

    /**
     * Compares this range to another based on their start positions.
     *
     * This comparison method allows IngredientRange objects to be sorted,
     * which is essential for the range merging algorithm. Ranges are ordered
     * by their start values in ascending order.
     *
     * @param o The other IngredientRange to compare to
     * @return A negative integer, zero, or positive integer as this range's start
     *         is less than, equal to, or greater than the other range's start
     */
    @Override
    public int compareTo(IngredientRange o) {
        return Long.compare(start, o.start);
    }


}