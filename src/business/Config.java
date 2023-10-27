package business;

public final class Config {
    /**
     * User of the database.
     */
    private String db_user;
    /**
     * Password of the database.
     */
    private String db_password;
    /**
     * IP of the database.
     */
    private String db_ip;
    /**
     * Port of the database.
     */
    private int db_port;
    /**
     * Name of the database.
     */
    private String db_name;
    /**
     * Time in seconds to wait between each shot.
     */
    private int time;


    /**
     * @return The user of the database.
     */
    public String getDBUser() {
        return db_user;
    }

    /**
     * @return The password of the database.
     */
    public String getPassword() {
        return db_password;
    }

    /**
     * @return The IP of the database.
     */
    public String getIp() {
        return db_ip;
    }

    /**
     * @return The port of the database.
     */
    public int getDb_port() {
        return db_port;
    }

    /**
     * @return The name of the database.
     */
    public String getDb_name() {
        return db_name;
    }

    /**
     * @return The time in milliseconds to wait between each shot.
     */
    public int getTime() {
        return time;
    }
}

