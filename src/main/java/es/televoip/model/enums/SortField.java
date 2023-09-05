package es.televoip.model.enums;

import es.televoip.model.Person;
import es.televoip.model.Task;

public enum SortField {
   TASK_TITLE("title", Task.class),
   TASK_DESCRIPTION("description", Task.class),
   TASK_STATUS("taskStatus", Task.class),
   TASK_IS_COMPLETED("isCompleted", Task.class),
   TASK_PRIORITYD("priority", Task.class),
   TASK_DATE_OF_CREATION("dateOfCreation", Task.class),
   TASK_DATE_OF_FINISHED("dateOfFinished", Task.class),
   //
   PERSON_FIRST_NAME("firstName", Person.class),
   PERSON_LAST_NAME("lasttName", Person.class),
   PERSON_DNI("dni", Person.class),
   PERSON_EMAIL("email", Person.class),
   PERSON_GENDER("gender", Person.class),
   PERSON_DATE_OF_BIRTH("dateOfBirth", Person.class),
   PERSON_IMPORTANT("important", Person.class);

   private final String fieldName;
   private final Class<?> entityClass;

   SortField(String fieldName, Class<?> entityClass) {
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
