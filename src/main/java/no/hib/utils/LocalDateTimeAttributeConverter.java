package no.hib.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import java.sql.Timestamp;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalTime, Timestamp> {
	
    @Override
    public Timestamp convertToDatabaseColumn(LocalTime locDateTime) {
    	LocalDateTime loc = LocalDateTime.of(2018, 1, 1, locDateTime.getHour(), locDateTime.getMinute(), locDateTime.getSecond());
    	return (locDateTime == null ? null : Timestamp.valueOf(loc));
    }

    @Override
    public LocalTime convertToEntityAttribute(Timestamp sqlTimestamp) {
    	
    	if(sqlTimestamp == null) return null;
    	LocalDateTime loc = sqlTimestamp.toLocalDateTime();
    	return LocalTime.of(loc.getHour(), loc.getMinute(), loc.getSecond());
    }
}