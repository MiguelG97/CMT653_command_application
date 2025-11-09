package entities;

import java.util.UUID;

public abstract class BaseClass {
    private final UUID id;
    private String name;
    public BaseClass() {
        this.id = UUID.randomUUID();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }
}
