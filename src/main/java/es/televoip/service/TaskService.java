package es.televoip.service;

import es.televoip.model.dto.TaskDto;
import es.televoip.model.enums.SortFieldTask;
import es.televoip.model.enums.TaskStatus;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface TaskService {

   TaskDto getTaskById(Long id);

   List<TaskDto> getAllTasks();

   Page<TaskDto> getTasksPaged(Pageable page);

   List<TaskDto> getTasksSorted(SortFieldTask sortBy, Sort.Direction sortOrder);

   Page<TaskDto> getTasksSortedAndPaged(SortFieldTask sortBy, Sort.Direction sortOrder, Pageable pageable);

   List<TaskDto> getTasksByFilter(String filter);

   Page<TaskDto> getTasksByFilterPageable(String filter, Pageable page);

   List<TaskDto> getTasksByTitleContaining(String title);

   List<TaskDto> getTasksByTaskStatus(TaskStatus status);

   List<TaskDto> getTasksByCompletion(boolean isCompleted);

   TaskDto saveTask(TaskDto taskDto);

   List<TaskDto> saveAllTasks(List<TaskDto> taskDtos);

   TaskDto updateTask(Long id, @Valid TaskDto taskDto);

   TaskDto updateTaskDateOfFinished(Long id, LocalDateTime newDateOfFinished);

   TaskDto updateTaskStatus(Long id, TaskStatus taskStatus);

   TaskDto updateTaskIsCompleted(Long id, Boolean isCompleted);

   TaskDto markTaskAsCompleted(Long id);

   void deleteTaskById(Long id);

}
