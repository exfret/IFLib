package org.iflib;

import java.util.HashMap;

public final class Resources {

    private static final HashMap<String, Resource> resources;

    static {
        resources = new HashMap<>();

        resources.put("TEST", new Resource("TEST", "test"));
    }

    /**
     * Adds a resource to the resource map
     * @param resource The resource to add
     */
    public static void addResource(Resource resource) {
        resources.put(resource.getId(), resource);
    }

    /**
     *
     * @return A list of Resource objects for every initialized resource
     */
    public static Resource[] getResources() {
        Object[] objs = resources.values().toArray();
        Resource[] res = new Resource[objs.length];

        for (int i = 0;i < objs.length;i ++) res[i] = (Resource) objs[i];

        return res;
    }

    /**
     *
     * @param id The id of the resource you want
     * @return The resource object associated with that id
     */
    public static Resource getResource(String id) {
        return resources.get(id);
    }

}
