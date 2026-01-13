package software.ulpgc.adventofcode2025.days.day02;

public class SimpleRepeatRule implements IdRule {
    @Override
    public long evaluate(long id) {
        String idstr = String.valueOf(id);
        if(idstr.length() % 2 == 0 && (idstr.substring(0, idstr.length()/2))
                .equals(idstr.substring(idstr.length()/2)))
            return Long.parseLong(idstr);
        else return 0;
    }
}
