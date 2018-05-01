package DAO.TransferObject;

public class Subject {
    private long id;
    private String name;

    public Subject(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder subject = new StringBuilder(name);
        return subject.toString();
    }


}
