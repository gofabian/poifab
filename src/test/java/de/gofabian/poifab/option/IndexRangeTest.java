package de.gofabian.poifab.option;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IndexRangeTest {

    @Test
    public void negativeStartIndexIsNotAllowed() {
        assertThrows(IllegalArgumentException.class, () ->
                new IndexRange(-1, 1)
        );
    }

    @Test
    public void negativeEndIndexIsNotAllowed() {
        assertThrows(IllegalArgumentException.class, () ->
                new IndexRange(0, -1)
        );
    }

    @Test
    public void startIndexMustNotBeAfterEndIndex() {
        assertThrows(IllegalArgumentException.class, () ->
                new IndexRange(10, 9)
        );
    }

    @Test
    public void forEach() {
        var indexes = new ArrayList<Integer>();

        new IndexRange(3, 5).forEach(indexes::add);

        assertEquals(List.of(3, 4, 5), indexes);
    }

    @Test
    public void contains() {
        var indexRange = new IndexRange(3, 3);

        assertTrue(indexRange.contains(3));
        assertFalse(indexRange.contains(2));
        assertFalse(indexRange.contains(4));
    }

}
