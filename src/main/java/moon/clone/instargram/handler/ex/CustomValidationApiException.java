package moon.clone.instargram.handler.ex;

import java.util.Map;

public class CustomValidationApiException extends RuntimeException {

    private Map<String, String> errorMap;

    public CustomValidationApiException(String message) {
        super(message);
    }

    public CustomValidationApiException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
