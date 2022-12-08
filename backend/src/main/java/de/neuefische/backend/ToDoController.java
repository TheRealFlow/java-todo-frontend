package de.neuefische.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;

    @GetMapping
    List<ToDo> getAllTodos() {
        return toDoService.getAllToDos();
    }

    @GetMapping("{id}")
    ToDo getTodoById(@PathVariable String id) {
        return toDoService.getTodoById(id);
    }

    @PostMapping
    ToDo postTodo(@RequestBody ToDo todo) {
        return toDoService.postTodo(todo);
    }

    @PutMapping(path = {"{id}/update", "{id}"})
    ToDo putToDo(@PathVariable String id, @RequestBody ToDo todo) {
        if (!todo.id.equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The ID doesn't match with the request ID");
        }
        return ToDoService.putToDo(todo);
    }

    @DeleteMapping("{id}")
    void deleteToDo(@PathVariable String id) {
        toDoService.deleteToDo(id);
    }
}
