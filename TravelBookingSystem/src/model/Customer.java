package model;

// Class turunan dari User
// Menerapkan INHERITANCE dan POLYMORPHISM
// Author: Habibi Putra Rizqullah (2411531001)
public class Customer extends User {

    private String customerType; // Type customer: REGULAR, VIP
    private int totalBookings; // Total booking yang pernah dilakukan

    public Customer(String userId, String username, String password, String fullName) {
        super(userId, username, password, fullName, "CUSTOMER");
        this.customerType = "REGULAR"; // Default type
        this.totalBookings = 0;
    }

    public Customer(String userId, String username, String password, String fullName, String customerType) {
        super(userId, username, password, fullName, "CUSTOMER");
        this.customerType = customerType;
        this.totalBookings = 0;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public int getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(int totalBookings) {
        this.totalBookings = totalBookings;
    }

    // POLYMORPHISM: override method dari parent class
    @Override
    public String getRole() {
        return "Customer";
    }

    // Method khusus Customer untuk memesan tiket
    public void bookTicket() {
        System.out.println(getFullName() + " is booking a ticket...");
        this.totalBookings++;
    }

    // Method khusus Customer untuk melihat booking sendiri
    public void viewMyBookings() {
        System.out.println(getFullName() + " is viewing bookings...");
    }

    // Upgrade ke VIP jika sudah booking lebih dari 5 kali
    public void checkVIPEligibility() {
        if (totalBookings >= 5 && customerType.equals("REGULAR")) {
            this.customerType = "VIP";
            System.out.println("Congratulations! You are now a VIP customer!");
        }
    }

    @Override
    public String displayInfo() {
        return super.displayInfo() +
               "\nCustomer Type: " + customerType +
               "\nTotal Bookings: " + totalBookings;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "userId='" + getUserId() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", customerType='" + customerType + '\'' +
                ", totalBookings=" + totalBookings +
                '}';
    }
}