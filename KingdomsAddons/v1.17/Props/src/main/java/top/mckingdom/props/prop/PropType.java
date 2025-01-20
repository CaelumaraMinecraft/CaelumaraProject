package top.mckingdom.props.prop;

public abstract class PropType {
    private final String name;

    public PropType(String name) {
        this.name = name;
    }

    public abstract void active(PropActiveContext context);


    public String getName() {
        return this.name;
    }
}
