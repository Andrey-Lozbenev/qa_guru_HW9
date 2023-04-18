package dzr.hanom.data;

public enum Gadgets {
    TABLET("Планшет"),
    SMARTPHONE("Смартфон");
    private final String desc;

    Gadgets(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }


}
