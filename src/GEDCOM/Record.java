package GEDCOM;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

public class Record {

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
		switch (propertyType) {
		case alive:
			Property isAlive = new Property();
			isAlive.setValue(!this.properties.containsKey(PropertyType.death));
			return isAlive;
		case age:

			if (this.properties.get(PropertyType.birthday) == null)
				return null;

			LocalDate endDate = this.properties.containsKey(PropertyType.death)
					? (LocalDate) this.properties.get(PropertyType.death).getValue()
					: LocalDate.now();
			Property age = new Property();
			age.setValue(Period.between((LocalDate) this.properties.get(PropertyType.birthday).getValue(), endDate)
					.getYears());

			return age;
		default:
			if (this.properties.containsKey(propertyType)) {
				return this.properties.get(propertyType);
			}
		}

		return null;
	}

}
