package GEDCOM;

public class Error {

	public enum ErrorType {
		ERROR, ANOMALY
	}

	public enum RecordType {
		INDIVIDUAL, FAMILY
	}

	private ErrorType errorType;
	private RecordType recordType;
	private String userStoryNumber;
	private int lineNumber;
	private String id;
	private String message;

	public ErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

	public RecordType getRecordType() {
		return recordType;
	}

	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}

	public String getUserStoryNumber() {
		return userStoryNumber;
	}

	public void setUserStoryNumber(String userStoryNumber) {
		this.userStoryNumber = userStoryNumber;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return String.format(errorType + ": " + recordType + ": " + userStoryNumber + ": " + lineNumber + ": " + id
				+ ": " + message);
	}

}
