package simple.exception;

/**
 * @author qianniu
 * @date 2020/9/21 8:20 下午
 * @desc
 */
public class ProcessException extends RuntimeException{

    private String node;

    public ProcessException(String msg) {
        super(msg);
    }

    public ProcessException(String msg, Throwable t) {
        super(msg, t);
    }

    public ProcessException(String node, String msg) {
        super(msg);
        this.node = node;
    }

    public ProcessException(String node, String msg, Throwable t) {
        super(msg, t);
        this.node = node;
    }

    public String getNode() {
        return this.node;
    }
}
