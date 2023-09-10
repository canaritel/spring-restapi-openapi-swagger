package es.televoip.model.enums;

public enum SortFieldTask {
   TASK_TITLE("title"),
   TASK_DESCRIPTION("description"),
   TASK_STATUS("taskStatus"),
   TASK_IS_COMPLETED("isCompleted"),
   TASK_PRIORITYD("priority"),
   TASK_DATE_OF_CREATION("dateOfCreation"),
   TASK_DATE_OF_FINISHED("dateOfFinished");

   private final String fieldName;

   SortFieldTask(String fieldName) {
      this.fieldName = fieldName;
   }

   public String getFieldName() {
      return fieldName;
   }

}
