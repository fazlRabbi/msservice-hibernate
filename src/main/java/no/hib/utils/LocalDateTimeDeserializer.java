package no.hib.utils;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
    	System.out.println(">>>>>>>>>>>>>>>>> " + arg0.getText());
    	System.out.println(">>>>>>>>>>>>>>>>> " + arg1);
        return LocalDateTime.parse(arg0.getText());
    }
}