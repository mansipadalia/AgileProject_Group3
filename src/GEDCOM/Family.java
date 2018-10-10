package GEDCOM;

import java.util.HashMap;
import java.util.Map;

public class Family {
	private Map<PropertyType, Property> properties = new HashMap<>();

	public Map<PropertyType, Property> getProperties() {
		return properties;
	}

	public void setProperties(Map<PropertyType, Property> properties) {
		this.properties = properties;
	}

	public void setProperty(PropertyType type, Property property) {
		this.properties.put(type, property);	
	}

	public Property getProperty(PropertyType propertyType) {
		if (this.properties.containsKey(propertyType)) {
			return this.properties.get(propertyType);
		}

		return null;
	}

}
