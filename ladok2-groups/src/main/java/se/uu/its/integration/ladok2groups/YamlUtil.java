package se.uu.its.integration.ladok2groups;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class YamlUtil {
	
    static public Map< String, String> loadSql(ClassLoader cl, String resourceName) {
    	Map< String, Object> mo = load(cl, resourceName);
    	Map< String, String> m = new HashMap<String, String>();
    	for (String k : mo.keySet()) {
    		Object o = mo.get(k);
    		if (o instanceof String) {
    			m.put(k, (String) o);
    		} else {
    			throw new RuntimeException("Not SQL string: " + o);
    		}
		}
    	return m;
    }
    
    static public Map< String, Object> load(ClassLoader cl, String resourceName) {
        Yaml yaml = new Yaml();
        InputStream is = cl.getResourceAsStream(resourceName);
		@SuppressWarnings("unchecked")
		Map< String, Object> m = (Map< String, Object>) yaml.load(is);
		return m;
    }
}
