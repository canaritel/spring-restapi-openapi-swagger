package es.televoip.model.enums;

import es.televoip.model.Person;

public enum PersonSortField {
   PERSON_FIRST_NAME("firstName", Person.class),
   PERSON_LAST_NAME("lasttName", Person.class),
   PERSON_DNI("dni", Person.class),
   PERSON_EMAIL("email", Person.class),
   PERSON_GENDER("gender", Person.class),
   PERSON_DATE_OF_BIRTH("dateOfBirth", Person.class),
   PERSON_IMPORTANT("important", Person.class);

   private final String fieldName;
   private final Class<?> entityClass;

   PersonSortField(String fieldName, Class<?> entityClass) {
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
