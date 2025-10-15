package com.kaiburr.taskmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks") // all routes start with /tasks
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    //  1. GET all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    //  2. GET a specific task by ID
    @GetMapping("/{id}")
    public Optional<Task> getTaskById(@PathVariable String id) {
        return taskRepository.findById(id);
    }

    //  3. PUT (create) a new task
    @PutMapping
    public Task createTask(@RequestBody Task task) {
        // Basic validation (avoid unsafe commands)
        if (task.getCommand().toLowerCase().contains("rm") || task.getCommand().toLowerCase().contains("shutdown")) {
            throw new IllegalArgumentException("Unsafe command detected!");
        }
        return taskRepository.save(task);
    }

    //  4. DELETE a task by ID
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable String id) {
        taskRepository.deleteById(id);
        return "Task deleted with ID: " + id;
    }

    //  5. FIND tasks by name
    @GetMapping("/find")
    public List<Task> findTasksByName(@RequestParam String name) {
        return taskRepository.findByNameContainingIgnoreCase(name);
    }

    // 6. Execute the command for a task
    @PutMapping("/{id}/execute")
    public String executeTaskCommand(@PathVariable String id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();

            try {
                // Record start time
                Date startTime = new Date();

                // Execute the shell command
                String[] command = {"cmd.exe", "/c", task.getCommand()};
                Process process = Runtime.getRuntime().exec(command);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                process.waitFor();

                // Record end time
                Date endTime = new Date();

                // Save execution details
                TaskExecution execution = new TaskExecution(startTime, endTime, output.toString());
                task.getTaskExecutions().add(execution);
                taskRepository.save(task);

                return "Command executed successfully:\n" + output;
            } catch (Exception e) {
                return "Error while executing command: " + e.getMessage();
            }

        } else {
            return "Task not found!";
        }
    }
}
