package de.neuefische.backend;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ToDoRepo {

    private final Map<String, ToDo> todos = new HashMap<>();

    public List<ToDo> getAllTodos() {
        return new ArrayList<>(todos.values());
    }

    public ToDo getTodoById(String id) {
        return todos.get(id);
    }

    public ToDo postTodo(ToDo toPost) {
        todos.put(toPost.id, toPost);
        return toPost;
    }

    public ToDo putToDo(ToDo todo) {
        todos.put(todo.id, todo);
        return todo;
    }

    public void deleteToDo(String id) {
        todos.remove(id);
    }
}
