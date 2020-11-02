package tk.yubarimelon.mongo.response;

public class Response<T> {
    public static final int CODE_OK = 0;
    public static final int C0DE_ERROR = 1;
    public static final int CODE_NOT_FOUND = 404;

    public static final String MSG_OK = "OK";
    public static final String MSG_ERROR = "ERROR";
    public static final String MSG_CREATE = "CREATE";
    public static final String MSG_UPDATE = "UPDATE";
    public static final String MSG_DELETE = "DELETE";

    private int code = CODE_OK;
    private String message;
    private T data;

    public Response(T data) {
        this.data = data;
    }

    public Response(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
