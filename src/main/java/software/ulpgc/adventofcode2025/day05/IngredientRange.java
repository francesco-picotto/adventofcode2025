package software.ulpgc.adventofcode2025.day05;

public class IngredientRange implements Comparable<IngredientRange>{
    private long start;
    private long end;

    public IngredientRange(long start, long end){
        this.start = start;
        this.end = end;
    }

    public boolean contains(long id) {
        return id >= start && id <= end;
    }

    public long size(){
        return end - start + 1;
    }

    public long getStart() {return start;}
    public long getEnd() {return end;}
    public void setEnd(long end) {this.end = end;}

    @Override
    public int compareTo(IngredientRange o) {
        return Long.compare(start, o.start);
    }
}
