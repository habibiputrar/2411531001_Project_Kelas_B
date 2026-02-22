package manager;

import dao.*;
import dao.impl.*;

// SINGLETON PATTERN untuk manage semua DAO dan Manager
// Memastikan hanya ada 1 instance di seluruh aplikasi
// Author: Habibi Putra Rizqullah (2411531001)
public class DatabaseManager {
    
    // SINGLETON: instance tunggal (static)
    private static DatabaseManager instance;
    
    // COMPOSITION: memiliki semua DAO
    private UserDAO userDAO;
    private BusDAO busDAO;
    private ScheduleDAO scheduleDAO;
    private PassengerDAO passengerDAO;
    private BookingDAO bookingDAO;
    
    // COMPOSITION: memiliki Manager classes
    private ScheduleManager scheduleManager;
    private BookingManager bookingManager;
    
    // Private constructor untuk prevent instantiation dari luar
    private DatabaseManager() {
        initializeDAOs();
        initializeManagers();
    }
    
    // SINGLETON: method untuk get instance tunggal (thread-safe)
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
            System.out.println("DatabaseManager instance created (Singleton)");
        }
        return instance;
    }
    
    // Inisialisasi semua DAO dengan dependency injection
    private void initializeDAOs() {
        userDAO = new UserDAOImpl();
        busDAO = new BusDAOImpl();
        passengerDAO = new PassengerDAOImpl();
        
        // ScheduleDAO membutuhkan BusDAO
        scheduleDAO = new ScheduleDAOImpl(busDAO);
        
        // BookingDAO membutuhkan ScheduleDAO, PassengerDAO, dan UserDAO
        bookingDAO = new BookingDAOImpl(scheduleDAO, passengerDAO, userDAO);
        
        System.out.println("All DAOs initialized");
    }
    
    // Inisialisasi semua Manager
    private void initializeManagers() {
        scheduleManager = new ScheduleManager(scheduleDAO, busDAO);
        bookingManager = new BookingManager(bookingDAO, scheduleDAO, passengerDAO);
        
        System.out.println("All Managers initialized");
    }
    
    // Getter methods untuk akses DAO
    
    public UserDAO getUserDAO() {
        return userDAO;
    }
    
    public BusDAO getBusDAO() {
        return busDAO;
    }
    
    public ScheduleDAO getScheduleDAO() {
        return scheduleDAO;
    }
    
    public PassengerDAO getPassengerDAO() {
        return passengerDAO;
    }
    
    public BookingDAO getBookingDAO() {
        return bookingDAO;
    }
    
    public ScheduleManager getScheduleManager() {
        return scheduleManager;
    }
    
    public BookingManager getBookingManager() {
        return bookingManager;
    }
    
    // Test semua DAO connections
    public void testAllConnections() {
        System.out.println("\n=== Testing All DAO Connections ===");
        
        try {
            int userCount = userDAO.findAll().size();
            System.out.println("✓ UserDAO connected - " + userCount + " users found");
            
            int busCount = busDAO.findAll().size();
            System.out.println("✓ BusDAO connected - " + busCount + " buses found");
            
            int scheduleCount = scheduleDAO.findAll().size();
            System.out.println("✓ ScheduleDAO connected - " + scheduleCount + " schedules found");
            
            int passengerCount = passengerDAO.findAll().size();
            System.out.println("✓ PassengerDAO connected - " + passengerCount + " passengers found");
            
            int bookingCount = bookingDAO.findAll().size();
            System.out.println("✓ BookingDAO connected - " + bookingCount + " bookings found");
            
            System.out.println("\n✓ All DAO connections successful!");
            
        } catch (Exception e) {
            System.err.println("✗ Error testing DAO connections: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Prevent cloning of singleton instance
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton instance cannot be cloned!");
    }
}