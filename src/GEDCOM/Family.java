package GEDCOM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Family {

    private String id;
    private LocalDate married;
    private LocalDate divorced;
    private String husbandID;
    private String husbandName;
    private String wifeId;
    private String wifeName;
    private List<String> children = new ArrayList<String>();

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getMarried() {
        return married;
    }
    public void setMarried(LocalDate married) {
        this.married = married;
    }

    public LocalDate getDivorced() {
        return divorced;
    }
    public void setDivorced(LocalDate divorced) {
        this.divorced = divorced;
    }

    public String getHusbandID() {
        return husbandID;
    }
    public void setHusbandID(String husbandID) {
        this.husbandID = husbandID;
    }

    public String getHusbandName() {
        return husbandName;
    }
    public void setHusbandName(String husbandName) {
        this.husbandName = husbandName;
    }

    public String getWifeId() {
        return wifeId;
    }
    public void setWifeId(String wifeId) {
        this.wifeId = wifeId;
    }

    public String getWifeName() {
        return wifeName;
    }
    public void setWifeName(String wifeName) {
        this.wifeName = wifeName;
    }
	public List<String> getChildren() {
		return children;
	}
	public void setChildren(List<String> children) {
		this.children = children;
	}
	
}
