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

	public boolean recordEquals(Record record) {
		boolean idEqual = ((this.getProperty(PropertyType.id) != null && record.getProperty(PropertyType.id) != null)
				? (this.getProperty(PropertyType.id).getValue().equals(record.getProperty(PropertyType.id).getValue()))
				: (this.getProperty(PropertyType.id) == null && record.getProperty(PropertyType.id) == null) ? true
						: false);
		boolean nameEqual = ((this.getProperty(PropertyType.name) != null
				&& record.getProperty(PropertyType.name) != null)
						? (this.getProperty(PropertyType.name).getValue()
								.equals(record.getProperty(PropertyType.name).getValue()))
						: (this.getProperty(PropertyType.name) == null && record.getProperty(PropertyType.name) == null)
								? true
								: false);
		boolean genderEqual = ((this.getProperty(PropertyType.gender) != null
				&& record.getProperty(PropertyType.gender) != null)
						? (this.getProperty(PropertyType.gender).getValue()
								.equals(record.getProperty(PropertyType.gender).getValue()))
						: (this.getProperty(PropertyType.gender) == null
								&& record.getProperty(PropertyType.gender) == null) ? true : false);
		boolean birthdayEqual = ((this.getProperty(PropertyType.birthday) != null
				&& record.getProperty(PropertyType.birthday) != null)
						? (this.getProperty(PropertyType.birthday).getValue() == null
								&& record.getProperty(PropertyType.birthday).getValue() == null)
										? true
										: (this.getProperty(PropertyType.birthday).getValue()
												.equals(record.getProperty(PropertyType.birthday).getValue()))
						: (this.getProperty(PropertyType.birthday) == null
								&& record.getProperty(PropertyType.birthday) == null) ? true : false);
		boolean deathEqual = ((this.getProperty(PropertyType.death) != null
				&& record.getProperty(PropertyType.death) != null)
						? (this.getProperty(PropertyType.death).getValue() == null
								&& record.getProperty(PropertyType.death).getValue() == null)
										? true
										: (this.getProperty(PropertyType.death).getValue()
												.equals(record.getProperty(PropertyType.death).getValue()))
						: (this.getProperty(PropertyType.death) == null
								&& record.getProperty(PropertyType.death) == null) ? true : false);
		boolean childEqual = ((this.getProperty(PropertyType.child) != null
				&& record.getProperty(PropertyType.child) != null)
						? (this.getProperty(PropertyType.child).getValue()
								.equals(record.getProperty(PropertyType.child).getValue()))
						: (this.getProperty(PropertyType.child) == null
								&& record.getProperty(PropertyType.child) == null) ? true : false);
		boolean spouseEqual = ((this.getProperty(PropertyType.spouse) != null
				&& record.getProperty(PropertyType.spouse) != null)
						? (this.getProperty(PropertyType.spouse).getValue()
								.equals(record.getProperty(PropertyType.spouse).getValue()))
						: (this.getProperty(PropertyType.spouse) == null
								&& record.getProperty(PropertyType.spouse) == null) ? true : false);
		boolean husbandIdEqual = ((this.getProperty(PropertyType.husbandID) != null
				&& record.getProperty(PropertyType.husbandID) != null)
						? (this.getProperty(PropertyType.husbandID).getValue()
								.equals(record.getProperty(PropertyType.husbandID).getValue()))
						: (this.getProperty(PropertyType.husbandID) == null
								&& record.getProperty(PropertyType.husbandID) == null) ? true : false);
		boolean husbandNameEqual = ((this.getProperty(PropertyType.husbandName) != null
				&& record.getProperty(PropertyType.husbandName) != null)
						? (this.getProperty(PropertyType.husbandName).getValue()
								.equals(record.getProperty(PropertyType.husbandName).getValue()))
						: (this.getProperty(PropertyType.husbandName) == null
								&& record.getProperty(PropertyType.husbandName) == null) ? true : false);
		boolean wifeIdEqual = ((this.getProperty(PropertyType.wifeID) != null
				&& record.getProperty(PropertyType.wifeID) != null)
						? (this.getProperty(PropertyType.wifeID).getValue()
								.equals(record.getProperty(PropertyType.wifeID).getValue()))
						: (this.getProperty(PropertyType.wifeID) == null
								&& record.getProperty(PropertyType.wifeID) == null) ? true : false);
		boolean wifeNameEqual = ((this.getProperty(PropertyType.wifeName) != null
				&& record.getProperty(PropertyType.wifeName) != null)
						? (this.getProperty(PropertyType.wifeName).getValue()
								.equals(record.getProperty(PropertyType.wifeName).getValue()))
						: (this.getProperty(PropertyType.wifeName) == null
								&& record.getProperty(PropertyType.wifeName) == null) ? true : false);
		boolean marriageEqual = ((this.getProperty(PropertyType.married) != null
				&& record.getProperty(PropertyType.married) != null)
						? (this.getProperty(PropertyType.married).getValue() == null
								&& record.getProperty(PropertyType.married).getValue() == null)
										? true
										: (this.getProperty(PropertyType.married).getValue()
												.equals(record.getProperty(PropertyType.married).getValue()))
						: (this.getProperty(PropertyType.married) == null
								&& record.getProperty(PropertyType.married) == null) ? true : false);
		boolean divorceEqual = ((this.getProperty(PropertyType.divorced) != null
				&& record.getProperty(PropertyType.divorced) != null)
						? (this.getProperty(PropertyType.divorced).getValue() == null
								&& record.getProperty(PropertyType.divorced).getValue() == null)
										? true
										: (this.getProperty(PropertyType.divorced).getValue()
												.equals(record.getProperty(PropertyType.divorced).getValue()))
						: (this.getProperty(PropertyType.divorced) == null
								&& record.getProperty(PropertyType.divorced) == null) ? true : false);
		return (idEqual && nameEqual && genderEqual && birthdayEqual && deathEqual && childEqual && spouseEqual
				&& husbandIdEqual && husbandNameEqual && wifeIdEqual && wifeNameEqual && marriageEqual && divorceEqual);
	}
}
