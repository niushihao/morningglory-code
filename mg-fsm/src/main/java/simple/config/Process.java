package simple.config;

import lombok.Data;
import java.util.List;

/**
 * @author qianniu
 * @date 2020/9/21 7:59 下午
 * @desc
 */
@Data
public class Process {

    private String bizCode;
    private String operation;
    private List<String> nodeList;

    public String toString() {
        return "Process(bizCode=" + this.getBizCode() + ", operation=" + this.getOperation() + ", nodeList=" + this.getNodeList() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Process)) {
            return false;
        } else {
            Process other = (Process)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$bizCode = this.getBizCode();
                Object other$bizCode = other.getBizCode();
                if (this$bizCode == null) {
                    if (other$bizCode != null) {
                        return false;
                    }
                } else if (!this$bizCode.equals(other$bizCode)) {
                    return false;
                }

                Object this$operation = this.getOperation();
                Object other$operation = other.getOperation();
                if (this$operation == null) {
                    if (other$operation != null) {
                        return false;
                    }
                } else if (!this$operation.equals(other$operation)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Process;
    }

    public int hashCode() {
        int PRIME = 1;
        int result = 1;
        Object $bizCode = this.getBizCode();
        result = result * 59 + ($bizCode == null ? 43 : $bizCode.hashCode());
        Object $operation = this.getOperation();
        result = result * 59 + ($operation == null ? 43 : $operation.hashCode());
        return result;
    }
}
