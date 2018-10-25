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

	public boolean individualEquals(Record record) {

		boolean idEqual = ((this.getProperty(PropertyType.id) != null && record.getProperty(PropertyType.id) != null)
				? (this.getProperty(PropertyType.id).equals(record.getProperty(PropertyType.id)))
				: (this.getProperty(PropertyType.id) == null && record.getProperty(PropertyType.id) == null) ? true
						: false);

		boolean nameEqual = ((this.getProperty(PropertyType.name) != null
				&& record.getProperty(PropertyType.name) != null)
						? (this.getProperty(PropertyType.name).equals(record.getProperty(PropertyType.name)))
						: (this.getProperty(PropertyType.name) == null && record.getProperty(PropertyType.name) == null) ? true
								: false);

		boolean genderEqual = ((this.getProperty(PropertyType.gender) != null
				&& record.getProperty(PropertyType.gender) != null)
						? (this.getProperty(PropertyType.gender).equals(record.getProperty(PropertyType.gender)))
						: (this.getProperty(PropertyType.gender) == null && record.getProperty(PropertyType.gender) == null) ? true
								: false);

		boolean birthdayEqual = ((this.getProperty(PropertyType.birthday) != null
				&& record.getProperty(PropertyType.birthday) != null)
						? (this.getProperty(PropertyType.birthday).equals(record.getProperty(PropertyType.birthday)))
						: (this.getProperty(PropertyType.birthday) == null && record.getProperty(PropertyType.birthday) == null) ? true
								: false);

		boolean deathEqual = ((this.getProperty(PropertyType.death) != null
				&& record.getProperty(PropertyType.death) != null)
						? (this.getProperty(PropertyType.death).equals(record.getProperty(PropertyType.death)))
						: (this.getProperty(PropertyType.death) == null && record.getProperty(PropertyType.death) == null) ? true
								: false);

		boolean childEqual = ((this.getProperty(PropertyType.child) != null
				&& record.getProperty(PropertyType.child) != null)
						? (this.getProperty(PropertyType.child).equals(record.getProperty(PropertyType.child)))
						: (this.getProperty(PropertyType.child) == null && record.getProperty(PropertyType.child) == null) ? true
								: false);

		boolean spouseEqual = ((this.getProperty(PropertyType.spouse) != null
				&& record.getProperty(PropertyType.spouse) != null)
						? (this.getProperty(PropertyType.spouse).equals(record.getProperty(PropertyType.spouse)))
						: (this.getProperty(PropertyType.spouse) == null && record.getProperty(PropertyType.spouse) == null) ? true
								: false);

		return (idEqual
				&& nameEqual
				&& genderEqual
				&& birthdayEqual
				&& deathEqual
				&& childEqual
				&& spouseEqual);
	}
}
