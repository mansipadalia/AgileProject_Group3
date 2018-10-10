package GEDCOM;

public class Property {

	private Object value;
	private int lineNumber;
	
	public Property(Object value, int lineNumber) {
		this.value = value;
		this.lineNumber = lineNumber;
	}
	
	public Property() {
	}

	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}	
}
