package cn.likole.bookmanager.bean;

/**
 * Created by likole on 3/23/18.
 */

public class UserBean {
    private int userId;
    private String userUserName;
    private String userPassword;
    private String userName;

    @Override
    public String toString() {
        return "UserBean{" +
                "userId=" + userId +
                ", userUsername='" + userUserName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public int getUserId() {

        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserUsername() {
        return userUserName;
    }

    public void setUserUsername(String userUsername) {
        this.userUserName = userUsername;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
