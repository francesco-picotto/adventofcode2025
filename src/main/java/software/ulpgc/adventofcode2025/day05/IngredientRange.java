package software.ulpgc.adventofcode2025.day05;

public record IngredientRange(long start, long end) implements Comparable<IngredientRange>{

    public IngredientRange{
        if(start>end){throw new IllegalArgumentException("Start cannot be greater than end");}
    }

    public static IngredientRange parse(String input) {
        String[] parts = input.split("-");
        return new IngredientRange(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }

    public boolean contains(long id) {
        return id >= start && id <= end;
    }

    public long size(){
        return end - start + 1;
    }

    @Override
    public int compareTo(IngredientRange o) {
        return Long.compare(start, o.start);
    }


}
