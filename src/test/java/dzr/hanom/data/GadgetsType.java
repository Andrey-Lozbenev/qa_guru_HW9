package dzr.hanom.data;

public enum GadgetsType {
    ANDROID("Android"),
    IPAD("iPad");
    private final String desc;

    GadgetsType(String desc) {

        this.desc = desc;
    }

    public String getDesc() {

        return desc;
    }
}
