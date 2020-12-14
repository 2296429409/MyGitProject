package cn.smbms.pojo;

public class MessageInfo {

    // 数据
    private Object data;

    // 是否成功
    private Boolean success;

    // 状态码
    private Integer status;

    // 说明
    private String result;

    public MessageInfo() {
    }

    public MessageInfo(Object data, Boolean success, Integer status, String result) {
        this.data = data;
        this.success = success;
        this.status = status;
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MessageInfo{" +
                "data=" + data +
                ", success=" + success +
                ", status=" + status +
                ", result='" + result + '\'' +
                '}';
    }
}
