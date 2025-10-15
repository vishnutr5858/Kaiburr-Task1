package com.kaiburr.taskmanager;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    // Custom method for searching tasks by name
    List<Task> findByNameContainingIgnoreCase(String name);
}
