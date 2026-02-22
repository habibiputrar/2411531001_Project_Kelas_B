package model;

// Class turunan dari User
// Menerapkan INHERITANCE dan POLYMORPHISM
// Author: Habibi Putra Rizqullah (2411531001)
public class Admin extends User {

    private String adminLevel; // Level akses admin: SUPER, STANDARD

    public Admin(String userId, String username, String password, String fullName) {
        super(userId, username, password, fullName, "ADMIN");
        this.adminLevel = "STANDARD"; // Default level
    }

    public Admin(String userId, String username, String password, String fullName, String adminLevel) {
        super(userId, username, password, fullName, "ADMIN");
        this.adminLevel = adminLevel;
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

    // POLYMORPHISM: override method dari parent class
    @Override
    public String getRole() {
        return "Administrator";
    }

    // Method khusus Admin untuk mengelola jadwal
    public void manageSchedule() {
        System.out.println(getFullName() + " is managing schedules...");
    }

    // Method khusus Admin untuk melihat semua booking
    public void viewAllBookings() {
        System.out.println(getFullName() + " is viewing all bookings...");
    }

    @Override
    public String displayInfo() {
        return super.displayInfo() + "\nAdmin Level: " + adminLevel;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "userId='" + getUserId() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", adminLevel='" + adminLevel + '\'' +
                '}';
    }
}