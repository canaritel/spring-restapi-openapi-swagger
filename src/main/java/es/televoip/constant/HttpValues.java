package es.televoip.constant;

public class HttpValues {

   private HttpValues() {
      // Constructor privado para evitar instanciación
   }

   public static final String VALUE_OK = "{"
          + "\"id\": \"long\","
          + "\"title\": \"string\","
          + "\"description\": \"string\","
          + "\"taskStatus\": \"[ON_TIME , LATE]\","
          + "\"isCompleted\": \"boolean\","
          + "\"priority\": \"int\","
          + "\"taskDateCreation\": \"localdatetime\","
          + "\"taskDateFinished\": \"localdatetime\""
          + "}";

   public static final String VALUE_ERROR_404 = "{"
          + "\"status\": \"error\","
          + "\"message\": \"La tarea no fue encontrada\""
          + "}";

   public static final String VALUE_ERROR_400 = "{"
          + "\"status\": \"error\","
          + "\"message\": \"La solicitud enviada no es válida.\""
          + "}";

   public static final String VALUE_ERROR_422 = "{"
          + "\"status\": \"error\","
          + "\"message\": \"La tarea no cumple las validaciones.\""
          + "}";

}
