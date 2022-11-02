package de.gofabian.poifab.option;

import java.util.function.Consumer;

/**
 * A range of rows or columns.
 */
public record IndexRange(
        int startIndex,
        int endIndex
) {

    public IndexRange {
        if (startIndex < 0) throw new IllegalArgumentException("startIndex must be >= 0, startIndex=" + startIndex);
        if (endIndex < 0) throw new IllegalArgumentException("endIndex must be >= 0, endIndex=" + endIndex);
        if (startIndex > endIndex)
            throw new IllegalArgumentException("startIndex must be <= endIndex, startIndex=" + startIndex + ", endIndex=" + endIndex);
    }

    public void forEach(Consumer<Integer> action) {
        for (var index = startIndex; index <= endIndex; index++) {
            action.accept(index);
        }
    }

    public boolean contains(int index) {
        return index >= startIndex && index <= endIndex;
    }

}
