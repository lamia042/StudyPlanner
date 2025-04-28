package dev.lamia.service;

import dev.lamia.model.Task;
import dev.lamia.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Create a new task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Fetch all tasks, sorted by date and priority
    public List<Task> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();

        // Sort by date first, then by priority
        tasks.sort((task1, task2) -> {
            // Compare by date first
            int dateComparison = task1.getDate().compareTo(task2.getDate());
            if (dateComparison != 0) {
                return dateComparison; // Sort by date if dates are different
            }

            // If dates are the same, then compare by priority
            return comparePriority(task1.getPriority(), task2.getPriority());
        });

        return tasks;
    }

    // Fetch tasks by subject
    public List<Task> getTasksBySubject(String subject) {
        return taskRepository.findBySubject(subject);
    }

    // Fetch tasks by priority
    public List<Task> getTasksByPriority(String priority) {
        return taskRepository.findByPriority(priority);
    }

    // Fetch a task by its ID
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    // Update a task
    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            task.setSubject(taskDetails.getSubject());
            task.setTask(taskDetails.getTask());
            task.setDescription(taskDetails.getDescription());
            task.setDate(taskDetails.getDate());
            task.setPriority(taskDetails.getPriority());
            return taskRepository.save(task);
        }
        return null;
    }

    // Delete a task
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // Method to compare priority as high > medium > low
    private int comparePriority(String priority1, String priority2) {
        // Define a mapping from priority to numerical value for easy comparison
        int priorityValue1 = getPriorityValue(priority1);
        int priorityValue2 = getPriorityValue(priority2);

        return Integer.compare(priorityValue2, priorityValue1); // High > Medium > Low
    }

    // Helper method to get numerical value for priority
    private int getPriorityValue(String priority) {
        switch (priority.toLowerCase()) {
            case "high":
                return 3;
            case "medium":
                return 2;
            case "low":
                return 1;
            default:
                return 0; // Default case if priority is invalid
        }
    }
}
