package tk.yubarimelon.mongo.exception;

import tk.yubarimelon.mongo.response.Response;

public class BasicException extends RuntimeException {
    public BasicException() {
        super(Response.MSG_ERROR);
    }

    public BasicException(String message) {
        super(message);
    }
}
