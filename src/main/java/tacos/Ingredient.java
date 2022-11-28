package tacos;

import javax.persistence.*;

@Entity
public class Ingredient {
    public Ingredient() {
        this.id = "";
        this.name = "";
        this.type = null;
    }

    public Ingredient(String id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    @Id
    private String id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Transient
    private boolean checked = false;

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", checked=" + checked +
                '}';
    }
}
