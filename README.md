# Poifab

[![Build](https://github.com/gofabian/poifab/actions/workflows/build.yml/badge.svg)](https://github.com/gofabian/poifab/actions/workflows/build.yml)
![Build](https://img.shields.io/badge/Java-17%2B-lightgrey)

Java library that reads an Excel file into objects. With focus on extensibility.

## ModelParser

Use the `ModelParser` to parse parts of an Excel file `sample.xlsx` into a list of `Model` instances: 

```java
// (1)
try (var workbook = WorkbookFactory.create(new File("sample.xlsx"))) {
    // (2)
    var options = new ParseOptionsBuilder()
        .sheet(workbook.getSheetAt(0))
        .titleRow(0)
        .dataRows(1, 2)
        .allColumns()
        .build();
    // (3)
    List<Model> result = new ModelParser().parse(Model.class, options);
}

// (4)
class Model {
    @ExcelColumnIndex(0)
    String name;
    @ExcelColumnTitle("Age")
    int age;
}
```

- (1) Use Apache POI to create a workbook.
- (2) Define which part of the Excel file should be read.
- (3) Call `ModelParser`.
- (4) Define the target data class.

sample.xlsx:

| Name | Age |
|------|-----|
| Foo  | 13  |
| Bar  | 37  |

## Map columns

```java
class Model {
    // select column by title
    @ExcelColumnTitle("Name")
    String name;
    
    // select column by index (0-based, e.g. 0=A)
    @ExcelColumnIndex(2)
    String hobby;
}
```

sample.xlsx:

| Name | Age | Hobby   |
|------|-----|---------|
| Foo  | 13  | Fishing |

## Options

```java
new ParseOptionsBuilder()
    .sheet(sheet)   // Excel worksheet
    ...
    .build();
```

### Title rows

```java
new ParseOptionsBuilder()
    .noTitleRows()    // no title rows (default)
    .titleRow(0)      // one row
    .titleRows(0, 1)  // row range
    ...
```

### Data rows

```java
new ParseOptionsBuilder()
    .allDataRows()      // all rows (default)
    .dataRows(10, 100)  // row range
    ...
```

### Columns

```java
new ParseOptionsBuilder()
    .allColumns()       // all columns (default)
    .columns(1, 10)     // column range
    ...
```

### Value Formatter

```java
new ParseOptionsBuilder()
    .rawData()          // get data as is (default)
    .formattedData()    // formatted
    ...
```

### Extend Poifab

```java
new ParseOptionsBuilder()
    .addCellParser(cellParser)
    .addFieldParser(fieldParser)
    .tableParser(tableParser)
    ...
```

## License

[MIT License](LICENSE.txt)
