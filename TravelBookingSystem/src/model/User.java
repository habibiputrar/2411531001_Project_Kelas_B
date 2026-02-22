package model;

// User (Abstract Class) - base class untuk Admin dan Customer
// Menerapkan ABSTRACTION dan INHERITANCE
// Author: Habibi Putra Rizqullah (2411531001)
public abstract class User {
    // ENCAPSULATION: atribut private
    private String userId;
    private String username;
    private String password;
    private String fullName;
    private String userType;

    public User(String userId, String username, String password, String fullName, String userType) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.userType = userType;
    }

    // ENCAPSULATION: getter dan setter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    // ABSTRACTION: method abstract harus diimplementasikan oleh subclass
    // POLYMORPHISM: method ini akan di-override oleh Admin dan Customer
    public abstract String getRole();

    // Display info user
    public String displayInfo() {
        return "User ID: " + userId + "\n" +
               "Username: " + username + "\n" +
               "Full Name: " + fullName + "\n" +
               "Role: " + getRole();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}