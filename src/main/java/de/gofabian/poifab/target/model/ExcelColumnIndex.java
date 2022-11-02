package de.gofabian.poifab.target.model;

import java.lang.annotation.*;

/**
 * Map a field to an Excel column by column index (0-based).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ExcelColumnIndex {
    int value();
}
