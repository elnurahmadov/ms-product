package az.ingress.ms_product.exception;

public interface ExceptionConstants {

    String UNEXPECTED_EXCEPTION_CODE = "UNEXPECTED_EXCEPTION";
    String UNEXPECTED_EXCEPTION_MESSAGE = "An unexpected error occurred";

    String VALIDATION_ERROR_CODE = "VALIDATION_ERROR";

    String CLIENT_ERROR_CODE = "CLIENT_ERROR";
    String CLIENT_ERROR_MESSAGE = "Client error occurred";

    String PRODUCT_NOT_FOUND_CODE = "PRODUCT_NOT_FOUND";
    String PRODUCT_NOT_FOUND_MESSAGE = "Product with id:%s not found";
}
