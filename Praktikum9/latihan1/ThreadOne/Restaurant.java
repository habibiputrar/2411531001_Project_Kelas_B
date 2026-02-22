package Praktikum9.latihan1.ThreadOne;

public class Restaurant {
    public static void main(String[] args) {
        Cooking t1 = new Cooking("Pasta");
        Cooking t2 = new Cooking("Salad");
        Cooking t3 = new Cooking("Dessert");
        Cooking t4 = new Cooking("Rice");
        
        t1.run();
        t2.run();
        t3.run();
        t4.run();
    }
}