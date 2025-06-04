/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cashregsystem;
//miko
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CashRegSystem{
    static Scanner input = new Scanner(System.in);

    static ArrayList<String> usernames = new ArrayList<>();
    static ArrayList<String> passwords = new ArrayList<>();

    static ArrayList<String> items = new ArrayList<>();
    static ArrayList<String> prices = new ArrayList<>();

    static ArrayList<String> cartItems = new ArrayList<>();
    static ArrayList<String> cartPrices = new ArrayList<>();
    static ArrayList<Integer> cartQty = new ArrayList<>();

    public static void main(String[] args) {
        items.add("Lakers Jersey");
        prices.add("1500");
        items.add("Lakers Cap");
        prices.add("750");
        items.add("Lakers Hoodie");
        prices.add("1800");
        items.add("Lakers Socks");
        prices.add("300");
        items.add("Lakers Water Bottle");
        prices.add("500");

        while (true) {
            System.out.println("\n============================");
            System.out.println("     CASH REGISTER MENU     ");
            System.out.println("============================");
            System.out.println("1. Sign Up");
            System.out.println("2. Log In");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            String choice = input.nextLine();

            if (choice.equals("1")) {
                signUp();
            } else if (choice.equals("2")) {
                String currentUser = logIn();
                if (currentUser != null) {
                    orderMenu(currentUser);
                }
            } else if (choice.equals("3")) {
                break;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    static void signUp() {
        System.out.print("\nEnter new username: ");
        String user = input.nextLine();
        if (usernames.contains(user)) {
            System.out.println("Username already exists.");
            return;
        }
        System.out.print("Enter new password: ");
        String pass = input.nextLine();
        usernames.add(user);
        passwords.add(pass);
        System.out.println("Account created.");
    }

    static String logIn() {
        System.out.print("\nEnter username: ");
        String user = input.nextLine();
        System.out.print("Enter password: ");
        String pass = input.nextLine();
        for (int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i).equals(user) && passwords.get(i).equals(pass)) {
                System.out.println("Welcome " + user);
                return user;
            }
        }
        System.out.println("Incorrect login.");
        return null;
    }

    static void orderMenu(String user) {
        cartItems.clear();
        cartPrices.clear();
        cartQty.clear();

        while (true) {
            System.out.println("\n========================");
            System.out.println("        ORDER MENU      ");
            System.out.println("========================");
            System.out.println("1. Add Item");
            System.out.println("2. Update Quantity");
            System.out.println("3. Remove Item");
            System.out.println("4. View Cart");
            System.out.println("5. Checkout");
            System.out.println("6. Logout");
            System.out.print("Choose: ");
            String option = input.nextLine();

            if (option.equals("1")) {
                addItem();
            } else if (option.equals("2")) {
                updateQuantity();
            } else if (option.equals("3")) {
                removeItem();
            } else if (option.equals("4")) {
                showCart();
            } else if (option.equals("5")) {
                checkout(user);
            } else if (option.equals("6")) {
                break;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    static void showItems() {
        System.out.println("\nLakers Merchandise:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i) + " - " + prices.get(i));
        }
    }

    static void addItem() {
        showItems();
        try {
            System.out.print("Enter item number: ");
            int num = Integer.parseInt(input.nextLine());
            System.out.print("Enter quantity: ");
            int qty = Integer.parseInt(input.nextLine());

            if (num < 1 || num > items.size() || qty <= 0) {
                System.out.println("Invalid input.");
                return;
            }

            cartItems.add(items.get(num - 1));
            cartPrices.add(prices.get(num - 1));
            cartQty.add(qty);
            System.out.println("Added to cart.");
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    static void updateQuantity() {
        showCart();
        try {
            System.out.print("Enter item number to update: ");
            int index = Integer.parseInt(input.nextLine()) - 1;
            System.out.print("Enter new quantity: ");
            int qty = Integer.parseInt(input.nextLine());

            if (index < 0 || index >= cartItems.size() || qty <= 0) {
                System.out.println("Invalid input.");
                return;
            }

            cartQty.set(index, qty);
            System.out.println("Quantity updated.");
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    static void removeItem() {
        showCart();
        try {
            System.out.print("Enter item number to remove: ");
            int index = Integer.parseInt(input.nextLine()) - 1;

            if (index < 0 || index >= cartItems.size()) {
                System.out.println("Invalid input.");
                return;
            }

            cartItems.remove(index);
            cartPrices.remove(index);
            cartQty.remove(index);
            System.out.println("Item removed.");
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    static void showCart() {
        if (cartItems.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("\nYour Cart:");
        for (int i = 0; i < cartItems.size(); i++) {
            System.out.println((i + 1) + ". " + cartItems.get(i) + " - " + cartQty.get(i) + " x " + cartPrices.get(i));
        }
    }

    static void checkout(String user) {
        if (cartItems.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        double total = 0;
        String transaction = "-----------------------------\n";
        transaction += "User: " + user + "\n";
        transaction += "Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n";

        for (int i = 0; i < cartItems.size(); i++) {
            String item = cartItems.get(i);
            int qty = cartQty.get(i);
            double price = Double.parseDouble(cartPrices.get(i));
            double subtotal = qty * price;
            total += subtotal;
            transaction += item + " - " + qty + " x " + price + " = " + subtotal + "\n";
        }

        transaction += "Total: " + total + "\n";
        transaction += "-----------------------------\n\n";

        System.out.println(transaction);
        saveToFile(transaction);

        cartItems.clear();
        cartPrices.clear();
        cartQty.clear();
    }

    static void saveToFile(String data) {
        try {
            FileWriter fw = new FileWriter("transactions.txt", true);
            fw.write(data);
            fw.close();
            System.out.println("Transaction saved.");
        } catch (IOException e) {
            System.out.println("Failed to save transaction.");
        }
    }
}
