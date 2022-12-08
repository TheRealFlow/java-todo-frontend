package de.neuefische.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ToDoService {

    private static final ToDoRepo toDoRepo = new ToDoRepo();

    List<ToDo> getAllToDos() {
        return toDoRepo.getAllTodos();
    }

    public ToDo getTodoById(String id) {
        return toDoRepo.getTodoById(id);
    }

    public ToDo postTodo(ToDo todo) {
        String id = UUID.randomUUID().toString();
        ToDo todoToSave = todo.newId(id);
        return toDoRepo.postTodo(todoToSave);
    }

    public static ToDo putToDo(ToDo todo) {
        return toDoRepo.putToDo(todo);
    }

    public void deleteToDo(String id) {
        toDoRepo.deleteToDo(id);
    }
}
