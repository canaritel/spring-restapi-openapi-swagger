package es.televoip.model.enums;

public enum Gender {
   MALE("Male"),
   FEMALE("Female"),
   UNDEFINED("Undefined");

   private final String label;

   private Gender(String label) {
      this.label = label;
   }

   public String getLabel() {
      return label;
   }

}
