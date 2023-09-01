package es.televoip.constant;

public final class TaskConstant {

   private TaskConstant() {
      // Constructor privado para evitar instanciación
   }

   // valores para test y carga de datos
   public static final Long TEST_TASK_1_ID = 1L;
   public static final String TEST_TASK_1_TITLE = "title1";
   public static final String TEST_TASK_1_DESCRIPTION = "description1";
   public static final int TEST_TASK_1_PRIORITY = 1;

   public static final Long TEST_TASK_2_ID = 2L;
   public static final String TEST_TASK_2_TITLE = "title1";
   public static final String TEST_TASK_2_DESCRIPTION = "description2";
   public static final int TEST_TASK_2_PRIORITY = 2;

   public static final Long TEST_TASK_3_ID = 3L;
   public static final String TEST_TASK_3_TITLE = "title3";
   public static final String TEST_TASK_3_DESCRIPTION = "description3";
   public static final int TEST_TASK_3_PRIORITY = 3;

   public static final Long TEST_TASK_4_ID = 4L;
   public static final String TEST_TASK_4_TITLE = "title4";
   public static final String TEST_TASK_4_DESCRIPTION = "description4";
   public static final int TEST_TASK_4_PRIORITY = 4;

   public static final Long TEST_TASK_5_ID = 5L;
   public static final String TEST_TASK_5_TITLE = "title5";
   public static final String TEST_TASK_5_DESCRIPTION = "description5";
   public static final int TEST_TASK_5_PRIORITY = 5;

   // Mensajes en la capa Service
   public static final String TASK_ID_NOT_FOUND = "Tarea no encontrada con ID: ";
   public static final String TASK_IS_NULL = "La lista de tareas a crear es 'null'.";
   public static final String TASK_DATE_FAIL = "La fecha de finalización debe ser igual o posterior a la fecha de creación.";

   // Mensajes en la capa Controller
   public static final String TASK_NOT_FOUND = "Tarea no encontrada";
   public static final String TASK_ID_SPECIFIC_NOT_FOUND = "La tarea con el ID especificado no se encuentra.";
   public static final String TASK_PARAM_NOT_VALID = "Revise si los parámetros requeridos están ausentes o no son válidos.";
   public static final String TASK_RECORD_CHECK_VALID = "Revise si los campos cumplen con las validaciones.";

   public static final String TASK_UPDATED = "Actualización de la tarea";
   public static final String TASK_UPDATED_SUCCESS = "Tarea actualizada exitosamente.";
   public static final String TASK_IS_COMPLETED = "Indica si la tarea está completada.";
   public static final String TASK_COMPLETED = "Tarea completada";
   public static final String TASK_STATE_UPDATED_SUCCESS = "Estado de la tarea actualizada exitosamente.";
   public static final String TASK_STATE_UPDATED = "Actualización del estado de la tarea";
   public static final String TASK_UPDATED_TO_COMPLETED = "Tarea actualizada exitosamente a completada.";
   public static final String TASK_UPDATED_COMPLETED = "Actualización de tarea a completada.";
   public static final String TASK_INVALID_REQUEST = "Solicitud incorrecta.";
   public static final String TASK_REQUEST_NOT_PROCESSED = "No se pudo procesar la solicitud.";
   public static final String TASK_DATE_UPDATED_SUCCESS = "Fecha de finalización actualizada exitosamente.";
   public static final String TASK_DATE_UPDATED = "Actualización de fecha de finalización";

   static final String STATUS = "\"status\": \"error\",";
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
          + STATUS //"\"status\": \"error\","
          + "\"message\": \"La tarea no fue encontrada\""
          + "}";

   public static final String VALUE_ERROR_400 = "{"
          + STATUS //"\"status\": \"error\","
          + "\"message\": \"La solicitud enviada no es válida.\""
          + "}";

   public static final String VALUE_ERROR_422 = "{"
          + STATUS //"\"status\": \"error\","
          + "\"message\": \"La tarea no cumple las validaciones.\""
          + "}";

}
