package common.core.web.exception;

import com.alibaba.fastjson.JSON;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.web.api.ExceptionBaseApiResponse;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @description: Feign默认异常解析处理
 * @author: jia.chen
 * @create: 2018-12-14 20:08
 */
@Configuration
public class FeignExceptionErrorDecoder implements ErrorDecoder {
    private final Logger logger = LoggerFactory.getLogger(FeignExceptionErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        //处理response
        ExceptionBaseApiResponse exceptionBaseApiResponse = null;
        String body = null;

        try {
            if (response == null || response.body() == null) {
                return new RuntimeException("the Fegin response body is null,plz try again");
            }

            body = Util.toString(response.body().asReader());
            logger.debug("feign response body is {}", body);

            //解析返回异常数据
            exceptionBaseApiResponse = JSON.parseObject(body.getBytes("UTF-8"), ExceptionBaseApiResponse.class);
            Class<Exception> exceptionClass = (Class<Exception>) Class.forName(exceptionBaseApiResponse.getClassName());
            Exception exception = JSON.parseObject(exceptionBaseApiResponse.getExceptionStr().getBytes("UTF-8"), exceptionClass);
            return exception;
        } catch (IOException e) {
            logger.error("feign response body is {}", body);
            logger.error("Feign IOException", e);
            throw new RuntimeException("IOexception,plz check it", e);
        } catch (ClassNotFoundException e) {
            logger.error("feign response body is {}", body);
            //当claaspath不存在目标异常，直接转换成 runtimeException
            logger.error("the exception class " + exceptionBaseApiResponse.getClassName() + ", " + exceptionBaseApiResponse.getMessage() + " is not exist , plz check the classpath", e);
            String exceptionMsg = "the exception class " + exceptionBaseApiResponse.getClassName() + " is not exist,and cast it into [RuntimeException]," +
                    "the original exception msg is : " + exceptionBaseApiResponse.getMessage();
            throw new RuntimeException(exceptionMsg);
        } catch (Exception e) {
            logger.error("feign response body is {}", body);
            throw new RuntimeException("Feign errorDecoder is error：" + e.getMessage() + ", the response body is " + body, e);
        }
    }
}
