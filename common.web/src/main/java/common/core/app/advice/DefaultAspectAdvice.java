package common.core.app.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.util.StopWatch;

@Aspect
public class DefaultAspectAdvice {
	private final Logger logger = LoggerFactory.getLogger(DefaultAspectAdvice.class);

	@Around("execution(public * com.eascs..*Controller.*(..))||execution(public * com.eascs..*Service.*(..))||execution(public * com.eascs..*Dao.*(..))")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		logger.debug("Before:{}", pjp.toString());
		try {
			return pjp.proceed();
		} finally {
			logger.debug("After:{},elapsedTime={}", pjp.toString(), stopWatch.elapsedTime());
		}
	}

}
