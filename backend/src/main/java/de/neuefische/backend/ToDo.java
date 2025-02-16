package de.neuefische.backend;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ToDo {
    public String id;
    public String description;
    public String status;

    public ToDo newId(String id) {
        return new ToDo(id, description, status);
    }
}




