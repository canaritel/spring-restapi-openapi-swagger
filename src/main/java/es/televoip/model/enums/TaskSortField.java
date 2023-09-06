package es.televoip.model.enums;

import es.televoip.model.Task;

public enum TaskSortField {
   TASK_TITLE("title", Task.class),
   TASK_DESCRIPTION("description", Task.class),
   TASK_STATUS("taskStatus", Task.class),
   TASK_IS_COMPLETED("isCompleted", Task.class),
   TASK_PRIORITYD("priority", Task.class),
   TASK_DATE_OF_CREATION("dateOfCreation", Task.class),
   TASK_DATE_OF_FINISHED("dateOfFinished", Task.class);

   private final String fieldName;
   private final Class<?> entityClass;

   TaskSortField(String fieldName, Class<?> entityClass) {
      this.fieldName = fieldName;
      this.entityClass = entityClass;
   }

   public String getFieldName() {
      return fieldName;
   }

   public Class<?> getEntityClass() {
      return entityClass;
   }

}
