package es.televoip.service;

import es.televoip.model.dto.TaskDto;
import es.televoip.model.enums.TaskStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

   List<TaskDto> getTasksByFilter(String filter);

   Page<TaskDto> getTasksByFilterPageable(String filter, Pageable page);

   List<TaskDto> getTasksByTitleContaining(String title);

   List<TaskDto> getTasksByTaskStatus(TaskStatus status);

   List<TaskDto> getTasksByCompletion(boolean isCompleted);

   TaskDto saveTaskAndCheckCreationDate(TaskDto taskDto);

   List<TaskDto> saveAllTasks(List<TaskDto> taskDtos);

   TaskDto updateTaskDateOfFinished(Long id, LocalDateTime newDateOfFinished);

   TaskDto updateTaskStatus(Long id, TaskStatus taskStatus);

   TaskDto updateTaskIsCompleted(Long id, Boolean isCompleted);

   TaskDto markTaskAsCompleted(Long id);

}
