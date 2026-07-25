package org.iflib;

public class Resource {

    private String id, displayName;
    private double amount;

    public Resource(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }


    /**
     *
     * @return The amount of this resource the user has
     */
    public double getAmount() {
        return amount;
    }

    /**
     *
     * @return A string representing the amount of this resource the user has with 2 decimal precision
     */
    public String getValue() {
        return String.format("%.2f", amount);
    }

    /**
     *
     * @return The id of this resource
     */
    public String getId() { return id; }

    /**
     *
     * @return The display name of this resource
     */
    public String toString() { return displayName; }

}
