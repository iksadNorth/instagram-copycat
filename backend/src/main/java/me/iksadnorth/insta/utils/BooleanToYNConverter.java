package me.iksadnorth.insta.utils;

import javax.persistence.AttributeConverter;

public class BooleanToYNConverter implements AttributeConverter<Boolean, String> {

    public static final String True = "1";
    public static final String False = "0";

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? True : False;
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return True.equalsIgnoreCase(dbData);
    }
}
