package org.iflib;

import java.util.HashMap;

public final class Resources {

    private static final HashMap<String, Resource> resources;

    static {
        resources = new HashMap<String,Resource>();
    }

    public Resource getResource(String id) {
        return resources.get(id);
    }

}
