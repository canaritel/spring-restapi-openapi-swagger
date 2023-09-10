package es.televoip.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// Creamos un 'aspecto' que captura el tiempo de ejecución de los métodos anotados
@Aspect
@Component
public class ExecutionTimeAspect {

   private static final Logger logger = LoggerFactory.getLogger(ExecutionTimeAspect.class);

   @Around("@annotation(LogExecutionTime)")
   public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
      long startTime = System.currentTimeMillis();

      Object result = joinPoint.proceed();

      long endTime = System.currentTimeMillis();
      long executionTime = endTime - startTime;

      logger.info("{} executed in {} ms", joinPoint.getSignature(), executionTime);

      return result;
   }

}
