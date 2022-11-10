package de.gofabian.poifab.target.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.gofabian.poifab.core.FieldParser;
import de.gofabian.poifab.option.ParseOptions;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Parses Excel rows to instances of a model class.
 */
public class ModelParser {
    private final ObjectMapper objectMapper;

    public ModelParser() {
        objectMapper = new ObjectMapper();
    }

    public ModelParser(ObjectMapper objectMapper) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    public <T> List<T> parse(Class<T> modelClass, ParseOptions options) {
        var fieldToParserMap = getFieldParsers(modelClass, options.fieldParsers());
        var listOfFieldToValueMap = options.tableParser().parse(fieldToParserMap, options);

        var listOfFieldNameToValueMap = new ArrayList<Map<String, Object>>();
        for (var fieldToValueMap : listOfFieldToValueMap) {
            var fieldNameToValueMap = new HashMap<String, Object>();
            fieldToValueMap.forEach((field, value) -> fieldNameToValueMap.put(field.getName(), value));
            listOfFieldNameToValueMap.add(fieldNameToValueMap);
        }

        var jacksonType = objectMapper.getTypeFactory().constructCollectionType(List.class, modelClass);
        return objectMapper.convertValue(listOfFieldNameToValueMap, jacksonType);
    }

    private Map<Field, FieldParser> getFieldParsers(Class<?> modelClass, List<FieldParser> allFieldParsers) {
        var fieldToParserMap = new LinkedHashMap<Field, FieldParser>();
        for (var field : modelClass.getDeclaredFields()) {
            var notFound = true;
            for (var parser : allFieldParsers) {
                if (parser.supports(field)) {
                    fieldToParserMap.put(field, parser);
                    notFound = false;
                    break;
                }
            }
            if (notFound) {
                throw new IllegalArgumentException("Field is not supported by any field parser: " + field);
            }
        }
        return fieldToParserMap;
    }

}
