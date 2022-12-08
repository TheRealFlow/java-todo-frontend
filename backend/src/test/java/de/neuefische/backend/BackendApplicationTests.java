package de.neuefische.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class BackendApplicationTests {

    ToDoRepo toDoRepo = mock(ToDoRepo.class);
    ToDoService toDoService = new ToDoService(toDoRepo);

    @Test
    void getAllTodos_shouldReturnAllTodos() {
        ToDo testToDo = new ToDo("1", "Test1", ToDoStatus.OPEN);
        when(toDoRepo.getAllTodos()).thenReturn(Collections.singletonList(testToDo));

        List<ToDo> actual = toDoService.getAllToDos();

        Assertions.assertEquals(actual).containsExactly(testToDo);
    }

}
