package application.models;

public enum Priority {
    NORMAL("NORMAL"),
    CITO("CITO"),
    STATIM("STATIM");

    private final String id;

    Priority(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    public String getId() {
        return id;
    }

}