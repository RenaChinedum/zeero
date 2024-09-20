package com.zeero.zeero.controller;

import com.zeero.zeero.dto.request.TasksRequest;
import com.zeero.zeero.dto.response.TasksResponse;
import com.zeero.zeero.dto.response.UnifiedResponse;
import com.zeero.zeero.service.TasksService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TasksService tasksService;

    @PostMapping("/create-task")
    public ResponseEntity<UnifiedResponse<TasksResponse>> createTask(@RequestBody TasksRequest request){
        return ResponseEntity.ok(tasksService.createTasks(request));
    }

    @GetMapping("/get-task/{id}")
    public ResponseEntity<UnifiedResponse<TasksResponse>> getATask(@PathVariable("id") Long taskId){
        return ResponseEntity.ok(tasksService.getATask(taskId));
    }
     @GetMapping("/get-all-task")
    public ResponseEntity<UnifiedResponse<List<TasksResponse>>> getAllTasks(Pageable pageable){
        return ResponseEntity.ok(tasksService.getAllTask(pageable));
    }
    @GetMapping("/get-all-user-task")
    public ResponseEntity<UnifiedResponse<List<TasksResponse>>> getAllTaskByAUser(@PathVariable Long userId, Pageable pageable){
        return ResponseEntity.ok(tasksService.getAllTaskByAUser(userId, pageable));
    }



    @PutMapping("/update-task/{id}")
    public ResponseEntity<UnifiedResponse<TasksResponse>> updateTask( @PathVariable Long id, @RequestBody TasksRequest request){
        return ResponseEntity.ok(tasksService.updateTask(id,request));
    }

    @PatchMapping("/complete-task/{id}")
    public ResponseEntity<UnifiedResponse<TasksResponse>> completeTask( @PathVariable Long id){
        return ResponseEntity.ok(tasksService.completeTask(id));
    }

    @DeleteMapping("/delete-task/{id}")
    public ResponseEntity<UnifiedResponse<String>> deleteTask( @PathVariable Long id){
        return ResponseEntity.ok(tasksService.deleteTask(id));
    }
    
}
