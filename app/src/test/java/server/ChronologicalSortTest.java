package server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class ChronologicalSortTest {
    @Test
    public void testChronologicalSorter() {
        ChronologicalSorter cs = new ChronologicalSorter();
        ArrayList<Recipe> input = new ArrayList<Recipe>();
        assertEquals(0, cs.getModifiedList(input).size());

        Recipe r0 = new Recipe ("r0", null, null, null, "2020-12-03T10:15:30+01:00[Europe/Paris]");
        Recipe r1 = new Recipe ("r1", null, null, null, "2020-11-03T10:15:30+01:00[Europe/Paris]");
        Recipe r2 = new Recipe ("r2", null, null, null, "2007-12-03T10:15:30+01:00[Europe/Paris]");
        Recipe r3 = new Recipe ("r3", null, null, null, "2007-12-03T10:10:30+01:00[Europe/Paris]");
        Recipe r4 = new Recipe ("r4", null, null, null, "2006-12-03T10:15:30+01:00[Europe/Paris]");

        input.add(r0);
        
        assertEquals(r0, cs.getModifiedList(input).get(0));

        input.add(r1);
        input.add(r2);
        input.add(r3);
        input.add(r4);

        List<Recipe> result = cs.getModifiedList(input);
        assertEquals(r0, cs.getModifiedList(input).get(0));
        assertEquals(r1, cs.getModifiedList(input).get(1));
        assertEquals(r2, cs.getModifiedList(input).get(2));
        assertEquals(r3, cs.getModifiedList(input).get(3));
        assertEquals(r4, cs.getModifiedList(input).get(4));

    }

    @Test
    public void testReverseChronologicalSorter() {
        ReverseChronologicalSorter rcs = new ReverseChronologicalSorter();
        ArrayList<Recipe> input = new ArrayList<Recipe>();
        assertEquals(0, rcs.getModifiedList(input).size());

        Recipe r0 = new Recipe ("r0", null, null, null, "2020-12-03T10:15:30+01:00[Europe/Paris]");
        Recipe r1 = new Recipe ("r1", null, null, null, "2020-11-03T10:15:30+01:00[Europe/Paris]");
        Recipe r2 = new Recipe ("r2", null, null, null, "2007-12-03T10:15:30+01:00[Europe/Paris]");
        Recipe r3 = new Recipe ("r3", null, null, null, "2007-12-03T10:10:30+01:00[Europe/Paris]");
        Recipe r4 = new Recipe ("r4", null, null, null, "2006-12-03T10:15:30+01:00[Europe/Paris]");
        
        input.add(r0);
        
        assertEquals(r0, rcs.getModifiedList(input).get(0));

        input.add(r1);
        input.add(r2);
        input.add(r3);
        input.add(r4);

        List<Recipe> result = rcs.getModifiedList(input);
        assertEquals(r0, rcs.getModifiedList(input).get(4));
        assertEquals(r1, rcs.getModifiedList(input).get(3));
        assertEquals(r2, rcs.getModifiedList(input).get(2));
        assertEquals(r3, rcs.getModifiedList(input).get(1));
        assertEquals(r4, rcs.getModifiedList(input).get(0));
    }
}
