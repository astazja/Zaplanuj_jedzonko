package pl.coderslab.model;

public class DayName {

    private int id;
    private String name;
    private int display_order;

    public DayName() {
    }

    public DayName(String name, int displayOrder) {
        this.name = name;
        this.display_order = displayOrder;
    }

    @Override
    public String toString() {
        return "DayName{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayOrder=" + display_order +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDisplayOrder() {
        return display_order;
    }

    public void setDisplayOrder(int displayOrder) {
        this.display_order = displayOrder;
    }

}
