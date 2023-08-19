package es.televoip.model.enums;

public enum TaskSortField {
   TITLE("title"),
   DESCRIPTION("description"),
   IS_COMPLETED("isCompleted"),
   TASK_STATUS("taskStatus"),
   DATE_OF_CREATION("dateOfCreation"),
   DATE_OF_FINISHED("dateOfFinished");

   private final String fieldName;

   TaskSortField(String fieldName) {
      this.fieldName = fieldName;
   }

   public String getFieldName() {
      return fieldName;
   }

}
