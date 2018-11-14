package GEDCOM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import GEDCOM.Error.ErrorType;
import GEDCOM.Error.RecordType;

public class US_Consistency {

	// US26
	@SuppressWarnings("unchecked")
	public static List<Error> correspondingEntries(Parser p) {
		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();

		for (Record i : p.getFamilyList()) {

			String familyID = (String) i.getProperty(PropertyType.id).getValue();

			String husbandID = i.getProperty(PropertyType.husbandID) != null
					? (String) i.getProperty(PropertyType.husbandID).getValue()
					: null;
			Predicate<Record> byHId = x -> x.getProperty(PropertyType.id).getValue().equals(husbandID);
			List<Record> resH = p.getIndividualList().stream().filter(byHId).collect(Collectors.<Record>toList());
			List<String> spouseHID = husbandID != null ? (resH.get(0).getProperty(PropertyType.spouse) != null
					? (List<String>) resH.get(0).getProperty(PropertyType.spouse).getValue()
					: null) : null;

			if (familyID != null && spouseHID != null && !spouseHID.contains(familyID)) {
				error = new Error();
				error.setErrorType(ErrorType.ERROR);
				error.setRecordType(RecordType.FAMILY);
				error.setUserStoryNumber("US26");
				error.setLineNumber(i.getProperty(PropertyType.husbandID).getLineNumber());
				error.setId(familyID);
				error.setMessage("Inconsistent Husband Entry for Husband ID - " + husbandID + ", Spouse for - ("
						+ (spouseHID == null ? "" : String.join(",", spouseHID)) + ")");
				errors.add(error);
			}

			String wifeID = i.getProperty(PropertyType.wifeID) != null
					? (String) i.getProperty(PropertyType.wifeID).getValue()
					: null;
			Predicate<Record> byWId = x -> x.getProperty(PropertyType.id).getValue().equals(wifeID);
			List<Record> resW = p.getIndividualList().stream().filter(byWId).collect(Collectors.<Record>toList());
			List<String> spouseWID = wifeID != null ? (resW.get(0).getProperty(PropertyType.spouse) != null
					? (List<String>) resW.get(0).getProperty(PropertyType.spouse).getValue()
					: null) : null;

			if (familyID != null && spouseWID != null && !spouseWID.contains(familyID)) {
				error = new Error();
				error.setErrorType(ErrorType.ERROR);
				error.setRecordType(RecordType.FAMILY);
				error.setUserStoryNumber("US26");
				error.setLineNumber(wifeID == null ? -1 : i.getProperty(PropertyType.wifeID).getLineNumber());
				error.setId(familyID);
				error.setMessage("Inconsistent Wife Entry for Wife ID - " + wifeID + ", Spouse for - ("
						+ (spouseWID == null ? "" : String.join(",", spouseWID)) + ")");
				errors.add(error);
			}

			if (i.getProperty(PropertyType.children) != null) {
				Object[] ChildrenIdList = ((List<String>) i.getProperty(PropertyType.children).getValue()).toArray();
				String[] ChildrenIds = Arrays.copyOf(ChildrenIdList, ChildrenIdList.length, String[].class);

				if (ChildrenIds.length > 0) {
					for (int j = 0; j < ChildrenIds.length; j++) {
						String childID = ChildrenIds[j];
						Predicate<Record> byChId = x -> x.getProperty(PropertyType.id).getValue().equals(childID);
						List<Record> resCh = p.getIndividualList().stream().filter(byChId)
								.collect(Collectors.<Record>toList());
						String childChID = childID != null ? (resCh.get(0).getProperty(PropertyType.child) != null
								? (String) resCh.get(0).getProperty(PropertyType.child).getValue()
								: null) : null;

						if ((familyID != null && childChID != null && !childChID.equals(familyID))
								|| (familyID != null && childChID == null) || (familyID == null && childChID != null)) {
							error = new Error();
							error.setErrorType(ErrorType.ERROR);
							error.setRecordType(RecordType.FAMILY);
							error.setUserStoryNumber("US26");
							error.setLineNumber(i.getProperty(PropertyType.children).getLineNumber());
							error.setId(familyID);
							error.setMessage("Inconsistent Child Entry for Child ID - " + childID + ", Child for - ("
									+ (childChID == null ? "" : childChID) + ")");
							errors.add(error);
						}
					}
				}
			}
		}

		for (Record i : p.getIndividualList()) {

			String individualID = (String) i.getProperty(PropertyType.id).getValue();

			String childID = i.getProperty(PropertyType.child) != null
					? (String) i.getProperty(PropertyType.child).getValue()
					: null;
			Predicate<Record> byChId = x -> x.getProperty(PropertyType.id).getValue().equals(childID);
			List<Record> resCh = p.getFamilyList().stream().filter(byChId).collect(Collectors.<Record>toList());
			List<String> childrenID = childID != null ? (resCh.get(0).getProperty(PropertyType.children) != null
					? (List<String>) resCh.get(0).getProperty(PropertyType.children).getValue()
					: null) : null;

			if (individualID != null && childrenID != null && !childrenID.contains(individualID)) {
				error = new Error();
				error.setErrorType(ErrorType.ERROR);
				error.setRecordType(RecordType.INDIVIDUAL);
				error.setUserStoryNumber("US26");
				error.setLineNumber(i.getProperty(PropertyType.child).getLineNumber());
				error.setId(individualID);
				error.setMessage("Inconsistent Children Entry for Child ID - " + childID + ", Children are - ("
						+ (childrenID == null ? "" : childrenID) + ")");
				errors.add(error);
			}

			if (i.getProperty(PropertyType.spouse) != null) {
				Object[] SpouseIdList = ((List<String>) i.getProperty(PropertyType.spouse).getValue()).toArray();
				String[] SpouseIds = Arrays.copyOf(SpouseIdList, SpouseIdList.length, String[].class);

				if (SpouseIds.length > 0) {
					for (int j = 0; j < SpouseIds.length; j++) {
						String spouseID = SpouseIds[j];
						Predicate<Record> bySId = x -> x.getProperty(PropertyType.id).getValue().equals(spouseID);
						List<Record> resS = p.getFamilyList().stream().filter(bySId)
								.collect(Collectors.<Record>toList());
						String husbandID = resS.size() == 0 ? null
								: resS.get(0).getProperty(PropertyType.husbandID) != null
										? (String) resS.get(0).getProperty(PropertyType.husbandID).getValue()
										: null;
						String wifeID = resS.size() == 0 ? null
								: resS.get(0).getProperty(PropertyType.wifeID) != null
										? (String) resS.get(0).getProperty(PropertyType.wifeID).getValue()
										: null;

						if (!(spouseID != null && husbandID != null && wifeID != null
								&& (husbandID.equals(individualID) || wifeID.equals(individualID)))
								|| (spouseID != null && husbandID != null && wifeID == null
										&& !husbandID.equals(individualID))
								|| (spouseID != null && husbandID == null && wifeID != null
										&& !wifeID.equals(individualID))) {
							error = new Error();
							error.setErrorType(ErrorType.ERROR);
							error.setRecordType(RecordType.INDIVIDUAL);
							error.setUserStoryNumber("US26");
							error.setLineNumber(i.getProperty(PropertyType.spouse).getLineNumber());
							error.setId(individualID);
							error.setMessage("Inconsistent Spouse Entry for Family ID - " + spouseID
									+ ", Spouse are - (" + (husbandID == null ? "" : husbandID) + ","
									+ (wifeID == null ? "" : wifeID) + ")");
							errors.add(error);
						}
					}
				}
			}
		}

		return errors;
	}
}
