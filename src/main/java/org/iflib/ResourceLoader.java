package org.iflib;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public final class ResourceLoader {

    private ResourceLoader() {
        // Utility class; do not instantiate.
    }

    public static ObservableList<Resource> load(String resourcePath) {
        InputStream inputStream =
                ResourceLoader.class.getResourceAsStream(resourcePath);

        if (inputStream == null) {
            throw new IllegalArgumentException(
                    "Could not find resource file: " + resourcePath
            );
        }

        ObservableList<Resource> resources =
                FXCollections.observableArrayList();

        Set<String> usedIds = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        inputStream,
                        StandardCharsets.UTF_8
                )
        )) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                String trimmedLine = line.trim();

                if (trimmedLine.isEmpty()
                        || trimmedLine.startsWith("#")) {
                    continue;
                }

                String[] parts = trimmedLine.split("\\|", 2);

                if (parts.length != 2) {
                    throw new IllegalArgumentException(
                            "Invalid resource definition on line "
                                    + lineNumber
                                    + ": "
                                    + line
                    );
                }

                String id = parts[0].trim();
                String displayName = parts[1].trim();

                if (id.isEmpty() || displayName.isEmpty()) {
                    throw new IllegalArgumentException(
                            "Resource id and display name cannot be empty "
                                    + "on line "
                                    + lineNumber
                    );
                }

                if (!usedIds.add(id)) {
                    throw new IllegalArgumentException(
                            "Duplicate resource id on line "
                                    + lineNumber
                                    + ": "
                                    + id
                    );
                }

                resources.add(
                        new Resource(id, displayName)
                );
            }
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Failed to read resource file: " + resourcePath,
                    exception
            );
        }

        return resources;
    }
}