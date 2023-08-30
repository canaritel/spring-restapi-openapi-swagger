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
   public static final String TASK_NOT_FOUND = "Tarea no encontrada con ID: ";
   public static final String TASK_IS_NULL = "La lista de tareas a crear es 'null'.";
   public static final String TASK_DATE_FAIL = "La fecha de finalización debe ser igual o posterior a la fecha de creación.";

}
