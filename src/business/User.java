package business;

/**
 * A user of the system.
 */
public class User {
    private String name;

    /**
     * @param name the user's name
     *             Create a new user
     */
    public User(String name){
        this.name = name;
    }

    /**
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Reset the user's name
     */
    public void reset() {
        this.name = null;
    }
}
