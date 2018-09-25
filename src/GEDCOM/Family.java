package GEDCOM;

import java.util.GregorianCalendar;

public class Family {

    private String id;
    private GregorianCalendar married;
    private GregorianCalendar divorced;
    private String husbandID;
    private String husbandName;
    private String wifeId;
    private String wifeName;
    private String children;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public GregorianCalendar getMarried() {
        return married;
    }
    public void setMarried(GregorianCalendar married) {
        this.married = married;
    }

    public GregorianCalendar getDivorced() {
        return divorced;
    }
    public void setDivorced(GregorianCalendar divorced) {
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

    public String getChildren() {
        return children;
    }
    public void setChildren(String children) {
        this.children = children;
    }

}
