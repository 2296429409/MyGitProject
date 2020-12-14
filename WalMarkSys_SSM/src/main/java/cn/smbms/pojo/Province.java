package cn.smbms.pojo;

public class Province {
    private int pid;
    private String pname;
    private String pcode;

    public Province() {
    }

    public Province(int pid, String pname, String pcode) {
        this.pid = pid;
        this.pname = pname;
        this.pcode = pcode;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    @Override
    public String toString() {
        return "Province{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", pcode='" + pcode + '\'' +
                '}';
    }
}
