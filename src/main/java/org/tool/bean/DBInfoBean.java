package org.tool.bean;

public class DBInfoBean {
    // 服务器地址
    private String server;
    // 端口
    private String port;
    // 数据库名
    private String dataBase;
    // 用户名
    private String userId;
    // 密码
    private String password;

    public DBInfoBean() {

    }

    public DBInfoBean(String type, String server, String port, String dataBase, String userId, String password) {
        this.server = server;
        this.port = port;
        this.dataBase = dataBase;
        this.userId = userId;
        this.password = password;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "DBInfoBean{" +
                "server='" + server + '\'' +
                ", port='" + port + '\'' +
                ", dataBase='" + dataBase + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
