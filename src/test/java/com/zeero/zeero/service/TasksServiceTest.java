package com.zeero.zeero.service;

import com.zeero.zeero.TestUtil;
import com.zeero.zeero.dto.request.TasksRequest;
import com.zeero.zeero.dto.request.UserDetailRequest;
import com.zeero.zeero.dto.response.TasksResponse;
import com.zeero.zeero.dto.response.UnifiedResponse;
import com.zeero.zeero.model.Tasks;
import com.zeero.zeero.model.Users;
import com.zeero.zeero.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TasksServiceTest {
    @Mock
    private TaskRepository taskRepositoryMock;

    @Mock
    private BaseService baseServiceMock;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private TasksService tasksService;

    @Test
    void createTasks() {
        TasksRequest request = setRequest();
        UserDetailRequest rq = new UserDetailRequest();
        Tasks task = task(request);
        Users users = TestUtil.getUsers(rq);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(users);
        SecurityContextHolder.setContext(securityContext);

        when(taskRepositoryMock.save(any(Tasks.class))).thenReturn(task);

        UnifiedResponse<TasksResponse> result = tasksService.createTasks(request);

        assertNotNull(result);
        verify(taskRepositoryMock, times(1)).save(any(Tasks.class));
    }

    @Test
    void getATask() {
        TasksRequest request = setRequest();
        Tasks task = task(request);
        task.setId(1L);


        when(taskRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(task));

        UnifiedResponse<TasksResponse> result = tasksService.getATask(1L);

        assertNotNull(result);

    }

    @Test
    void updateTask() {
        TasksRequest request = setRequest();
        UserDetailRequest rq = new UserDetailRequest();
        Tasks task = task(request);

        when(taskRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(task));
        when(taskRepositoryMock.save(any(Tasks.class))).thenReturn(task);

        UnifiedResponse<TasksResponse> result = tasksService.updateTask(1L, request);

        assertNotNull(result);
        verify(taskRepositoryMock, times(1)).findById(any(Long.class));
        verify(taskRepositoryMock, times(1)).save(any(Tasks.class));
    }

    @Test
    void getAllTask() {

        Page<Tasks> expectedTaskPage = mock(Page.class);

        when(taskRepositoryMock.findAll(pageable)).thenReturn(expectedTaskPage);

        Page<Tasks> tasksPage = taskRepositoryMock.findAll(pageable);

        UnifiedResponse<List<TasksResponse>> result = tasksService.getAllTask(pageable);

        assertNotNull(result);
    }

    @Test
    void getAllTaskByAUser() {
        Page<Tasks> expectedTaskPage = mock(Page.class);

        when(taskRepositoryMock.findByUserId(1L, pageable)).thenReturn(expectedTaskPage);

        UnifiedResponse<List<TasksResponse>> result = tasksService.getAllTaskByAUser(1L, pageable);

        assertNotNull(result);
    }

    @Test
    void completeTask() {
        TasksRequest request = setRequest();
        Tasks task = task(request);
        task.setCompleted(true);
        task.setId(1L);

        when(taskRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(task));
        when(taskRepositoryMock.save(any(Tasks.class))).thenReturn(task);

        UnifiedResponse<TasksResponse> result = tasksService.completeTask(1L);

        assertNotNull(result);
        assertTrue(result.getData().isCompleted());
    }

    @Test
    void deleteTask() {

    }

    private Tasks task(TasksRequest request){
        Tasks tasks = new Tasks();
        tasks.setTitle(request.getTitle());
        tasks.setDueDate(request.getDueDate());
        return tasks;
    }

    private TasksRequest setRequest(){
        TasksRequest request = new TasksRequest();
        request.setTitle("My title");
        request.setDueDate(LocalDate.parse("2024-09-22"));
        return request;
    }
}