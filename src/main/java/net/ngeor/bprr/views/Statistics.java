package net.ngeor.bprr.views;

public class Statistics {
    private String name;
    private int created;
    private int reviewed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public int getReviewed() {
        return reviewed;
    }

    public void setReviewed(int reviewed) {
        this.reviewed = reviewed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Statistics that = (Statistics) o;

        if (created != that.created) return false;
        if (reviewed != that.reviewed) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + created;
        result = 31 * result + reviewed;
        return result;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "name='" + name + '\'' +
                ", created=" + created +
                ", reviewed=" + reviewed +
                '}';
    }
}
