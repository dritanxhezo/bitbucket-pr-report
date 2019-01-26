package net.ngeor.bprr;

/**
 * Stores statistics about a user.
 */
public class Statistic {
    private final String username;
    private final int count;

    public Statistic(String username, int count) {
        this.username = username;
        this.count    = count;
    }

    public String getUsername() {
        return username;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Statistic statistic = (Statistic) o;

        if (count != statistic.count) {
            return false;
        }

        return username.equals(statistic.username);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result     = 31 * result + count;
        return result;
    }

    @Override
    public String toString() {
        return "Statistic{"
            + "username='" + username + '\'' + ", count=" + count + '}';
    }
}
