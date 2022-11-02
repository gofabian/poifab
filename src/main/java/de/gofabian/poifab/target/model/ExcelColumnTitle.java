package de.gofabian.poifab.target.model;

import java.lang.annotation.*;

/**
 * Map a field to an Excel column by column title.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ExcelColumnTitle {
    String value();
}
