package xml2code.java.shared.json;

import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;

public class JSONUtil {
	
	public static <T extends IJSONObject> JSONArray getJSONArray(List<T> all) {
		
        JSONArray allJson = new JSONArray();

        Iterator<T> iterator = all.iterator();
        while (iterator.hasNext()){
        	
        	allJson.put(iterator.next().toJSONObject());
        	
        }
        
        return allJson;
		
	}
	
}
