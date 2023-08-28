package es.televoip.factory;

import es.televoip.constant.TaskConstant;
import es.televoip.model.dto.TaskDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TaskDtoDataFactory {

   public static TaskDto createSampleTaskWithId(Long id, String title, String description, int priority) {
      final TaskDto taskDto = TaskDto.builder()
             .id(id)
             .title(title)
             .description(description)
             .priority(priority)
             .build();
      return taskDto;
   }

   public static TaskDto createSampleTask1Default() {
      return createSampleTaskWithId(
             TaskConstant.TEST_TASK_1_ID,
             TaskConstant.TEST_TASK_1_TITLE,
             TaskConstant.TEST_TASK_1_DESCRIPTION,
             TaskConstant.TEST_TASK_1_PRIORITY);
   }

   public static TaskDto createSampleTask2Default() {
      return createSampleTaskWithId(
             TaskConstant.TEST_TASK_2_ID,
             TaskConstant.TEST_TASK_2_TITLE,
             TaskConstant.TEST_TASK_2_DESCRIPTION,
             TaskConstant.TEST_TASK_2_PRIORITY);
   }

   public static TaskDto createSampleTask3Default() {
      return createSampleTaskWithId(
             TaskConstant.TEST_TASK_3_ID,
             TaskConstant.TEST_TASK_3_TITLE,
             TaskConstant.TEST_TASK_3_DESCRIPTION,
             TaskConstant.TEST_TASK_3_PRIORITY);
   }

   public static TaskDto createSampleTask4Default() {
      return createSampleTaskWithId(
             TaskConstant.TEST_TASK_4_ID,
             TaskConstant.TEST_TASK_4_TITLE,
             TaskConstant.TEST_TASK_4_DESCRIPTION,
             TaskConstant.TEST_TASK_4_PRIORITY);
   }

   public static TaskDto createSampleTask5Default() {
      return createSampleTaskWithId(
             TaskConstant.TEST_TASK_5_ID,
             TaskConstant.TEST_TASK_5_TITLE,
             TaskConstant.TEST_TASK_5_DESCRIPTION,
             TaskConstant.TEST_TASK_5_PRIORITY);
   }

   public static Map<Long, TaskDto> create3SampleTaskMap() {
      final Map<Long, TaskDto> map = new HashMap<>();
      map.put(TaskConstant.TEST_TASK_1_ID, TaskDtoDataFactory.createSampleTask1Default());
      map.put(TaskConstant.TEST_TASK_2_ID, TaskDtoDataFactory.createSampleTask2Default());
      map.put(TaskConstant.TEST_TASK_3_ID, TaskDtoDataFactory.createSampleTask3Default());
      return map;
   }

   public static List<TaskDto> create3SampleTaskList() {
      final List<TaskDto> list = new ArrayList<>();
      list.add(TaskDtoDataFactory.createSampleTask1Default());
      list.add(TaskDtoDataFactory.createSampleTask2Default());
      list.add(TaskDtoDataFactory.createSampleTask3Default());
      return list;
   }

   public static Map<Long, TaskDto> create5SampleTaskMap() {
      final Map<Long, TaskDto> map = new HashMap<>();
      map.put(TaskConstant.TEST_TASK_1_ID, TaskDtoDataFactory.createSampleTask1Default());
      map.put(TaskConstant.TEST_TASK_2_ID, TaskDtoDataFactory.createSampleTask2Default());
      map.put(TaskConstant.TEST_TASK_3_ID, TaskDtoDataFactory.createSampleTask3Default());
       map.put(TaskConstant.TEST_TASK_4_ID, TaskDtoDataFactory.createSampleTask3Default());
        map.put(TaskConstant.TEST_TASK_5_ID, TaskDtoDataFactory.createSampleTask3Default());
      return map;
   }

   public static List<TaskDto> create5SampleTaskList() {
      final List<TaskDto> list = new ArrayList<>();
      list.add(TaskDtoDataFactory.createSampleTask1Default());
      list.add(TaskDtoDataFactory.createSampleTask2Default());
      list.add(TaskDtoDataFactory.createSampleTask3Default());
      list.add(TaskDtoDataFactory.createSampleTask4Default());
      list.add(TaskDtoDataFactory.createSampleTask5Default());
      return list;
   }

}
