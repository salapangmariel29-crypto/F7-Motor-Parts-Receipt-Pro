import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MotorPartsShop {
    static Scanner sc = new Scanner(System.in);

    // ===== PRODUCTS =====
    static String[] productNames = {
            "Honda Wave 125 Oil Filter",
            "Suzuki Raider 150 Air Filter",
            "Yamaha Mio Spark Plug",
            "Honda Click 125 Brake Pads",
            "Yamaha NMAX Battery",
            "Suzuki Burgman Timing Belt",
            "Yamaha Aerox Radiator Hose",
            "Honda TMX Alternator"
    };

    static double[] productPrices = {
            2550, 1875, 4500, 8999,
            12500, 7850, 3225, 24500
    };

    // ===== CART =====
    static String[] cartNames = new String[20];
    static double[] cartPrices = new double[20];
    static int[] cartQty = new int[20];
    static int cartCount = 0;

    // ===== TRANSACTIONS =====
    static String[] transactions = new String[50];
    static int transCount = 0;

    public static void main(String[] args) {

        String[] users = new String[5];
        String[] passwords = new String[5];
        int userCount = 0;
        String currentUser = "";

        // ===== LOGIN / SIGN-UP =====
        while (true) {
            System.out.println("\n=== ACCOUNT MENU ===");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");

            int accChoice;
            try {
                System.out.print("Choice: ");
                accChoice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input!");
                sc.nextLine();
                continue;
            }
            sc.nextLine();

            if (accChoice == 1) {
                if (userCount == 0) {
                    System.out.println("No account found. Please sign up.");
                    continue;
                }

                boolean loggedIn = false;
                while (!loggedIn) {
                    System.out.print("Username: ");
                    String user = sc.nextLine();

                    System.out.print("Password: ");
                    String pass = sc.nextLine();

                    boolean found = false;
                    for (int i = 0; i < userCount; i++) {
                        if (user.equals(users[i]) && pass.equals(passwords[i])) {
                            currentUser = user;
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        System.out.println("Login Successful!");
                        loggedIn = true;
                    } else {
                        System.out.println("Invalid username or password. Try again.");
                    }
                }

            } else if (accChoice == 2) {
                if (userCount >= users.length) {
                    System.out.println("Max accounts reached.");
                } else {
                    System.out.print("New Username: ");
                    users[userCount] = sc.nextLine();

                    System.out.print("New Password: ");
                    passwords[userCount] = sc.nextLine();

                    userCount++;
                    System.out.println("Account created!");
                }
            } else {
                System.out.println("Invalid input!");
            }

            if (!currentUser.equals("")) break;
        }

        mainMenu(currentUser);
    }

    // ===== MAIN MENU =====
    static void mainMenu(String currentUser) {
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. New Customer");
            System.out.println("2. Transactions");
            System.out.println("3. Exit");

            int choice;
            try {
                System.out.print("Choice: ");
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input!");
                sc.nextLine();
                continue;
            }
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Customer Name: ");
                    String customer = sc.nextLine();
                    customerMenu(customer, currentUser);
                }
                case 2 -> viewTransactions();
                case 3 -> {
                    System.out.println("Thank you!");
                    return;
                }
                default -> System.out.println("Invalid input!");
            }
        }
    }

    // ===== CUSTOMER MENU =====
    static void customerMenu(String customer, String cashier) {
        while (true) {
            System.out.println("\n=== CUSTOMER MENU ===");
            System.out.println("1. Display Products");
            System.out.println("2. View Cart");
            System.out.println("3. Back");

            int choice;
            try {
                System.out.print("Choice: ");
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input!");
                sc.nextLine();
                continue;
            }
            sc.nextLine();

            switch (choice) {
                case 1 -> chooseProducts();
                case 2 -> {showCart(customer, cashier);

                //if transaction happened (cart is cleared), go back to main menu
                if (cartCount == 0) {
                return;
                }
            }
                default -> System.out.println("Invalid input!");
            }
        }
    }

    // ===== CHOOSE PRODUCTS =====
    static void chooseProducts() {
        while (true) {
            System.out.println("\n=== PRODUCTS ===");
            for (int i = 0; i < productNames.length; i++) {
                System.out.println((i + 1) + ". " + productNames[i] +
                        " - PHP " + productPrices[i]);
            }

            int num;
            try {
                System.out.print("Product #: ");
                num = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input!");
                sc.nextLine();
                continue;
            }
            sc.nextLine();

            if (num < 1 || num > productNames.length) {
                System.out.println("Invalid input!");
                continue;
            }

            int qty;
            try {
                System.out.print("Quantity: ");
                qty = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input!");
                sc.nextLine();
                continue;
            }
            sc.nextLine();

            if (qty <= 0) {
                System.out.println("Invalid input!");
                continue;
            }

            if (cartCount >= cartNames.length) {
                System.out.println("Cart is full!");
                return;
            }

            cartNames[cartCount] = productNames[num - 1];
            cartPrices[cartCount] = productPrices[num - 1];
            cartQty[cartCount] = qty;
            cartCount++;

            System.out.print("Add more? (y/n): ");
            char c = sc.next().charAt(0);
            sc.nextLine();

            if (c == 'n' || c == 'N') break;
        }
    }

    // ===== SHOW CART =====
    static void showCart(String customer, String cashier) {
        if (cartCount == 0) {
            System.out.println("Cart is empty.");
            return;
        }

        double total = 0;

        System.out.println("\n===== CART =====");
        System.out.printf("%-5s %-35s %-10s %-12s\n", "No.", "Product", "Qty", "Subtotal");
        System.out.println("---------------------------------------------------------------");

        for (int i = 0; i < cartCount; i++) {
            double sub = cartPrices[i] * cartQty[i];
            total += sub;

            System.out.printf("%-5d %-35s %-10d %-12.2f\n",
                    (i + 1), cartNames[i], cartQty[i], sub);
        }

        System.out.println("---------------------------------------------------------------");
        System.out.printf("TOTAL: %.2f\n", total);

        while (true) {
            System.out.println("\n1. Edit");
            System.out.println("2. Checkout");
            System.out.println("3. Back");

            int choice;
            try {
                System.out.print("Choice: ");
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input!");
                sc.nextLine();
                continue;
            }
            sc.nextLine();

            if (choice == 1) editCart();
            else if (choice == 2) {
                checkout(customer, cashier, total);
                return;
            }
            else if (choice == 3) return;
            else System.out.println("Invalid input!");
        }
    }

// ===== PRE-CHECKOUT =====
    static void preCheckoutMenu(String customer, String cashier) {
        editCart(); // open edit first

        while (true) {
            System.out.println("\n=== PRE-CHECKOUT MENU ===");
            System.out.println("1. Add Product");
            System.out.println("2. Proceed to Checkout");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> chooseProducts();
                case 2 -> {
                    double total = 0;
                    for (int i = 0; i < cartCount; i++)
                        total += cartPrices[i] * cartQty[i];

                    checkout(customer, cashier, total);
                    return;
                }
            }
        }
    }
    // ===== EDIT CART =====
    static void editCart() {
    while (true) {
        System.out.println("\n=== EDIT MENU ===");
        System.out.println("1. Change Quantity");
        System.out.println("2. Remove Item");
        System.out.println("3. Change Product");
        System.out.println("4. Add Product");
        System.out.println("5. Back");

        int choice;
        try {
            System.out.print("Choice: ");
            choice = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input!");
            sc.nextLine();
            continue;
        }
        sc.nextLine();

        switch (choice) {
            case 1 -> { // Change Quantity
                if (cartCount == 0) {
                    System.out.println("Cart is empty.");
                    break;
                }
                int num;
                try {
                    System.out.print("Item #: ");
                    num = sc.nextInt();
                } catch (Exception e) {
                    System.out.println("Invalid input!");
                    sc.nextLine();
                    break;
                }
                sc.nextLine();
                if (num < 1 || num > cartCount) {
                    System.out.println("Invalid input!");
                    break;
                }
                try {
                    System.out.print("New Qty: ");
                    int newQty = sc.nextInt();
                    sc.nextLine();
                    if (newQty <= 0) System.out.println("Invalid input!");
                    else {
                        cartQty[num - 1] = newQty;
                        System.out.println("Quantity updated.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input!");
                    sc.nextLine();
                }
            }
            case 2 -> { // Remove Item
                if (cartCount == 0) {
                    System.out.println("Cart is empty.");
                    break;
                }
                int num;
                try {
                    System.out.print("Item #: ");
                    num = sc.nextInt();
                } catch (Exception e) {
                    System.out.println("Invalid input!");
                    sc.nextLine();
                    break;
                }
                sc.nextLine();
                if (num < 1 || num > cartCount) {
                    System.out.println("Invalid input!");
                    break;
                }
                for (int i = num - 1; i < cartCount - 1; i++) {
                    cartNames[i] = cartNames[i + 1];
                    cartPrices[i] = cartPrices[i + 1];
                    cartQty[i] = cartQty[i + 1];
                }
                cartCount--;
                System.out.println("Item removed.");
            }
            case 3 -> { // Change Product
                if (cartCount == 0) {
                    System.out.println("Cart is empty.");
                    break;
                }
                int num;
                try {
                    System.out.print("Item #: ");
                    num = sc.nextInt();
                } catch (Exception e) {
                    System.out.println("Invalid input!");
                    sc.nextLine();
                    break;
                }
                sc.nextLine();
                if (num < 1 || num > cartCount) {
                    System.out.println("Invalid input!");
                    break;
                }
                while (true) {
                    System.out.println("\n=== PRODUCTS LIST ===");
                    for (int i = 0; i < productNames.length; i++) {
                        System.out.println((i + 1) + ". " + productNames[i] + " - PHP " + productPrices[i]);
                    }
                    int newProd;
                    try {
                        System.out.print("New Product #: ");
                        newProd = sc.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid input!");
                        sc.nextLine();
                        continue;
                    }
                    sc.nextLine();
                    if (newProd < 1 || newProd > productNames.length) {
                        System.out.println("Invalid input!");
                        continue;
                    }
                    cartNames[num - 1] = productNames[newProd - 1];
                    cartPrices[num - 1] = productPrices[newProd - 1];
                    System.out.println("Product changed successfully.");
                    break;
                }
            }
            case 4 -> { // Add Product
                if (cartCount >= cartNames.length) {
                    System.out.println("Cart is full!");
                    break;
                }
                while (true) {
                    System.out.println("\n=== PRODUCTS LIST ===");
                    for (int i = 0; i < productNames.length; i++) {
                        System.out.println((i + 1) + ". " + productNames[i] + " - PHP " + productPrices[i]);
                    }
                    int num;
                    try {
                        System.out.print("Product #: ");
                        num = sc.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid input!");
                        sc.nextLine();
                        continue;
                    }
                    sc.nextLine();
                    if (num < 1 || num > productNames.length) {
                        System.out.println("Invalid input!");
                        continue;
                    }
                    int qty;
                    try {
                        System.out.print("Quantity: ");
                        qty = sc.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid input!");
                        sc.nextLine();
                        continue;
                    }
                    sc.nextLine();
                    if (qty <= 0) {
                        System.out.println("Invalid input!");
                        continue;
                    }
                    cartNames[cartCount] = productNames[num - 1];
                    cartPrices[cartCount] = productPrices[num - 1];
                    cartQty[cartCount] = qty;
                    cartCount++;
                    System.out.println("Product added to cart.");
                    break;
                }
            }
            case 5 -> { return; } // Back
            default -> System.out.println("Invalid input!");
        }
    }
}
    // ===== CHECKOUT =====
    static void checkout(String customer, String cashier, double total) {

      System.out.println("\n===== ORDER SUMMARY =====");

    for (int i = 0; i < cartCount; i++) {
    double sub = cartPrices[i] * cartQty[i];
    total += sub;

    System.out.println("Item      : " + cartNames[i]);
    System.out.println("Quantity  : " + cartQty[i]);
    System.out.println("Subtotal  : PHP " + sub);
    System.out.println("-----------------------------");
}

System.out.println("TOTAL AMOUNT : PHP " + total);
System.out.println("=============================");

double cash;

        while (true) {
            try {
                System.out.print("Cash: ");
                cash = sc.nextDouble();
            } catch (Exception e) {
                System.out.println("Invalid input!");
                sc.nextLine();
                continue;
            }
            sc.nextLine();

            if (cash < total) {
                System.out.println("Insufficient cash!");
            } else break;
        }

        double change = cash - total;

      // ===== FINAL RECEIPT =====
    System.out.println("\n========== RECEIPT ==========");
    System.out.println("Customer : " + customer);
    System.out.println("Cashier  : " + cashier);
    System.out.println("Time     : " + getTime());
    System.out.println("=============================");


    for (int i = 0; i < cartCount; i++) {
    double sub = cartPrices[i] * cartQty[i];
    total += sub;

    System.out.println("Item     : " + cartNames[i]);
    System.out.println("Qty      : " + cartQty[i]);
    System.out.println("Subtotal : PHP " + sub);
    System.out.println("-----------------------------");
}

    System.out.println("TOTAL    : PHP " + total);
    System.out.println("CASH     : PHP " + cash);
    System.out.println("CHANGE   : PHP " + change);
    System.out.println("=============================");
    System.out.println("  Thank you for your purchase!");
    System.out.println("=============================");

        transactions[transCount++] = customer + " | " + cashier + " | " + getTime();
        cartCount = 0;
    }

    // ===== TRANSACTIONS =====
static void viewTransactions() {
    System.out.println("\n========== TRANSACTIONS ==========");

    if (transCount == 0) {
        System.out.println("No transactions available.");
        System.out.println("==================================");
        return;
    }

    System.out.printf("%-5s %-20s %-20s %-20s\n", "No.", "Customer", "Cashier", "Date & Time");
    System.out.println("--------------------------------------------------------------------------");

    for (int i = 0; i < transCount; i++) {
        String[] parts = transactions[i].split(" \\| ");
        System.out.printf("%-5d %-20s %-20s %-20s\n",
                (i + 1),
                parts[0], // Customer
                parts[1], // Cashier
                parts[2]  // Time
        );
    }

    System.out.println("--------------------------------------------------------------------------");
}
    // ===== TIME =====
    static String getTime() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(f);
    }
}