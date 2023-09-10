package es.televoip.model.enums;

public enum SortFieldPerson {
   PERSON_FIRST_NAME("firstName"),
   PERSON_LAST_NAME("lasttName"),
   PERSON_DNI("dni"),
   PERSON_EMAIL("email"),
   PERSON_GENDER("gender"),
   PERSON_DATE_OF_BIRTH("dateOfBirth"),
   PERSON_IMPORTANT("important");

   private final String fieldName;

   SortFieldPerson(String fieldName) {
      this.fieldName = fieldName;
   }

   public String getFieldName() {
      return fieldName;
   }

}
