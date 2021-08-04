package moon.clone.instargram.handler;

import moon.clone.instargram.handler.ex.CustomApiException;
import moon.clone.instargram.handler.ex.CustomValidationApiException;
import moon.clone.instargram.handler.ex.CustomValidationException;
import moon.clone.instargram.util.Script;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController //데이터 return
@ControllerAdvice //모든 예외 처리 담당
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {
        if(e.getErrorMap() == null) {
            return Script.back(e.getMessage());
        }else {
            return Script.back(e.getErrorMap().toString());
        }
    }

    @ExceptionHandler(CustomValidationApiException.class)
    public String validationApiException(CustomValidationApiException e) {
        return e.getMessage();
    }

    @ExceptionHandler(CustomApiException.class)
    public String apiException(CustomApiException e) {
        return e.getMessage();
    }
}
