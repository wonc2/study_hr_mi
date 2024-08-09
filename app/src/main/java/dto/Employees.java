package dto;

public class Employees {
    private String id;
    private String Name;

    public Employees(String id, String name) {
        this.id = id;
        this.Name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return Name;
    }
}
