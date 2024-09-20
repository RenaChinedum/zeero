package com.zeero.zeero.service;

import com.zeero.zeero.dto.request.TasksRequest;
import com.zeero.zeero.dto.response.TasksResponse;
import com.zeero.zeero.dto.response.UnifiedResponse;
import com.zeero.zeero.exceptions.ErrorStatus;
import com.zeero.zeero.exceptions.TodoAppException;
import com.zeero.zeero.model.Tasks;
import com.zeero.zeero.model.Users;
import com.zeero.zeero.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.zeero.zeero.enums.Priority.NON_URGENT;
@Slf4j
@Service
@AllArgsConstructor
public class TasksService extends BaseService {
    private final TaskRepository taskRepository;

    public UnifiedResponse<TasksResponse> createTasks(TasksRequest request) {
        Users users = loggedInUser();
        Tasks task = setTasks(request);
        task.setUserId(users.getId());
        task.setPriority(request.getPriority() == null ? NON_URGENT : request.getPriority());
        taskRepository.save(task);
        return new UnifiedResponse<>(new TasksResponse(task));
    }

    @Cacheable(value = "taskCache", key = "#taskId")
    public UnifiedResponse<TasksResponse> getATask(Long taskId) {
        Tasks tasks = getTaskById(taskId);
        return new UnifiedResponse<>(new TasksResponse(tasks));
    }

    public UnifiedResponse<TasksResponse> updateTask(Long taskId, TasksRequest request) {
        Users users = loggedInUser();
        Tasks task = getTaskById(taskId);
        if (Objects.equals(task.getUserId(), users.getId())) {
            if (request.getDueDate() != null) {
                validateDueDate(request);
            }
            task.setTitle(request.getTitle() != null ? request.getTitle() : task.getTitle());
            task.setDescription(request.getDescription() != null ? request.getDescription() : task.getDescription());
            task.setDueDate(request.getDueDate() != null ? request.getDueDate() : task.getDueDate());
            task.setPriority(request.getPriority() != null ? request.getPriority() : task.getPriority());
            taskRepository.save(task);
            return new UnifiedResponse<>(new TasksResponse(task));
        }
        throw new TodoAppException(ErrorStatus.UNAUTHORIZED_ERROR, ErrorStatus.UNAUTHORIZED_ERROR.getErrorMessage());
    }

    @Cacheable(value = "taskCache")
    public UnifiedResponse<List<TasksResponse>> getAllTask(Pageable pageable) {
        Page<Tasks> tasksPage = taskRepository.findAll(pageable);
        List<Tasks> tasks = tasksPage.getContent();
        log.info(" =====>Tasks size +++>: {}", tasks.size());
        List<TasksResponse> tasksResponses = new ArrayList<>();
        for (Tasks task : tasks) {
            TasksResponse tasksResponse = new TasksResponse(task);
            tasksResponses.add(tasksResponse);
        }
        return new UnifiedResponse<>(tasksResponses);
    }

    @Cacheable(value = "taskCache")
    public UnifiedResponse<List<TasksResponse>> getAllTaskByAUser(Long userId, Pageable pageable) {
        Page<Tasks> tasksPage = taskRepository.findByUserId(userId, pageable);
        List<Tasks> tasks = tasksPage.getContent();
        List<TasksResponse> tasksResponses = new ArrayList<>();
        for (Tasks task : tasks) {
            TasksResponse tasksResponse = new TasksResponse(task);
            tasksResponses.add(tasksResponse);
        }
        return new UnifiedResponse<>(tasksResponses);
    }


    public UnifiedResponse<TasksResponse> completeTask(Long taskId) {
        Tasks task = getTaskById(taskId);
        task.setCompleted(true);
        taskRepository.save(task);
        return new UnifiedResponse<>(new TasksResponse(task));
    }

    public UnifiedResponse<String> deleteTask(Long taskId) {
        Tasks task = getTaskById(taskId);
        taskRepository.delete(task);
        return new UnifiedResponse<>("Task deleted successfully");
    }


    private Tasks setTasks(TasksRequest request) {
        Tasks tasks = new Tasks();
        validateTitle(request);
        validateDueDate(request);
        log.info(" =====>Due date: {}", request.getDueDate());
        tasks.setTitle(request.getTitle());
        tasks.setDescription(request.getDescription());
        tasks.setDueDate(request.getDueDate());
        return tasks;
    }

    private Tasks getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(() ->
                new TodoAppException(ErrorStatus.RESOURCE_NOT_FOUND_ERROR, "Task not found"));

    }

    private void validateDueDate(TasksRequest request) {
        if (request.getDueDate() != null && request.getDueDate().isBefore(LocalDate.now())) {
            throw new TodoAppException(ErrorStatus.VALIDATION_ERROR, "Due date can not be in the past");
        }
    }

    private void validateTitle(TasksRequest request) {
        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new TodoAppException(ErrorStatus.VALIDATION_ERROR, "Title must not be empty");
        }
    }

}

