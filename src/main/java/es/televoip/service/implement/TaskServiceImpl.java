package es.televoip.service.implement;

import es.televoip.model.Task;
import es.televoip.model.dto.TaskDto;
import es.televoip.model.enums.SortField;
import es.televoip.model.enums.TaskStatus;
import es.televoip.model.mapper.TaskMapper;
import es.televoip.repository.TaskRepository;
import es.televoip.service.AbstractService;
import es.televoip.service.TaskService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import es.televoip.model.mapper.BaseMapper;

@Validated
@Slf4j // nos permite enviar texto a la consola mediante "log"
@Service
public class TaskServiceImpl extends AbstractService<Task, Long, TaskDto> implements TaskService {

   public TaskServiceImpl(TaskRepository repository, TaskMapper mapper) {
      super(repository, (BaseMapper<Task, TaskDto>) mapper);
   }

   @Override
   public List<TaskDto> getAllTasksSorted(SortField sortBy, Sort.Direction sortOrder) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }

   @Override
   public Page<TaskDto> getAllTasksSortedAndPaginated(SortField sortBy, Sort.Direction sortOrder, int page, int size) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }

   @Override
   public List<TaskDto> getAllTasksByTitleContaining(String title) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }

   @Override
   public List<TaskDto> getAllByTaskStatus(TaskStatus status) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }

   @Override
   public List<TaskDto> getAllTasksByCompletionStatus(boolean isCompleted) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }

   @Override
   public List<TaskDto> createAllTasks(List<TaskDto> taskDtos) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }

   @Override
   public TaskDto updateTaskDateOfFinished(Long id, LocalDateTime newDateOfFinished) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }

   @Override
   public TaskDto updateTaskStatus(Long id, TaskStatus taskStatus) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }

   @Override
   public TaskDto updateTaskIsCompleted(Long id, Boolean isCompleted) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }

   @Override
   public TaskDto updateTaskToCompleted(Long id) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }

}
