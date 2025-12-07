package mapping;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodExecutionAspect {

    @Around("execution(* app..*(..))")
    public Object aroundAppMethods(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature sig = (MethodSignature) pjp.getSignature();
        String className = sig.getDeclaringType().getSimpleName();
        String methodName = sig.getName();
        long start = System.nanoTime();
        try {
            Object result = pjp.proceed();
            return result;
        } finally {
            long durationNanos = System.nanoTime() - start;
            ExecutionTracker.logMethod(className, methodName);
            ExecutionTracker.logDuration(className + "." + methodName, durationNanos);
            System.out.println("[Aspect] Executed Test Case:" + className + "." + methodName + " in " + durationNanos + " ns");
        }
    }
}
