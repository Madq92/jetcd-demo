package cc.mikaka.jetcd.commons;

import lombok.Data;

@Data
public class Result<T> {
    public static final int SUCCESS_CODE = 0;
    public static final String SUCCESS_MSG = "ok";

    private int code;
    private String msg;
    private T payload;
    private long time = System.currentTimeMillis();

    private Result(int code, String msg, T payload) {
        this.code = code;
        this.msg = msg;
        this.payload = payload;
    }

    public static <T> Result<T> ok() {
        return new Result(SUCCESS_CODE, SUCCESS_MSG, null);
    }

    public static <T> Result<T> ok(T obj) {
        return new Result(SUCCESS_CODE, SUCCESS_MSG, obj);
    }
}
