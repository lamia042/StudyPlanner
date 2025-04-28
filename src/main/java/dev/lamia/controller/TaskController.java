package dev.lamia.controller;

import dev.lamia.model.Task;
import dev.lamia.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Endpoint to create a task
    @PostMapping("/create")
    public ResponseEntity<Object> createTask(@Valid @RequestBody Task task) {
        try {
            // Create the task through the service
            Task createdTask = taskService.createTask(task);

            // Return the created task with a success response
            return ResponseEntity.ok(createdTask);
        } catch (Exception e) {
            // Return a more detailed error message
            return ResponseEntity.status(500).body(new ResponseMessage("Error creating task: " + e.getMessage()));
        }
    }

    // Endpoint to get all tasks
    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTasks() {
        try {
            List<Task> tasks = taskService.getAllTasks();
            if (tasks.isEmpty()) {
                return ResponseEntity.noContent().build();  // No content found
            }
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);  // Internal server error
        }
    }

    // Endpoint to get a task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getTaskById(@PathVariable Long id) {
        try {
            Task task = taskService.getTaskById(id);
            if (task == null) {
                return ResponseEntity.status(404).body(new ResponseMessage("Task not found with ID: " + id));
            }
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseMessage("Error fetching task: " + e.getMessage()));
        }
    }

    // Endpoint to update a task
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable Long id, @Valid @RequestBody Task taskDetails) {
        try {
            Task updatedTask = taskService.updateTask(id, taskDetails);
            if (updatedTask == null) {
                return ResponseEntity.status(404).body(new ResponseMessage("Task not found with ID: " + id));
            }
            return ResponseEntity.ok(updatedTask);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseMessage("Error updating task: " + e.getMessage()));
        }
    }

    // Endpoint to delete a task
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok(new ResponseMessage("Task deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseMessage("Error deleting task: " + e.getMessage()));
        }
    }

    // Endpoint to get tasks by subject
    @GetMapping("/subject/{subject}")
    public ResponseEntity<List<Task>> getTasksBySubject(@PathVariable String subject) {
        try {
            List<Task> tasks = taskService.getTasksBySubject(subject);
            if (tasks.isEmpty()) {
                return ResponseEntity.noContent().build();  // No tasks found for this subject
            }
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);  // Internal server error
        }
    }

    // Endpoint to get tasks by priority
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable String priority) {
        try {
            List<Task> tasks = taskService.getTasksByPriority(priority);
            if (tasks.isEmpty()) {
                return ResponseEntity.noContent().build();  // No tasks found for this priority
            }
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);  // Internal server error
        }
    }

    // Response message helper class for consistent JSON response structure
    public static class ResponseMessage {
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
