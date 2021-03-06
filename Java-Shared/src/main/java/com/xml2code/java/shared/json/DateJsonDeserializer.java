package com.xml2code.java.shared.json;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class DateJsonDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
			return formatter.parse(parser.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return null;
		
	}

}
