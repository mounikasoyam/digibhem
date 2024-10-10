import java.util.Scanner;

// ATM class to handle all the ATM functionalities
class ATM {
    private double balance;  // stores the current balance
    private String transactionHistory;  // stores the transaction history
    private String userID;  // user ID for the account
    private String userPIN;  // user PIN for authentication

    // Constructor to initialize the ATM with userID and userPIN
    public ATM(String userID, String userPIN) {
        this.balance = 0.0;  // initial balance is set to zero
        this.transactionHistory = "";  // transaction history starts empty
        this.userID = userID;
        this.userPIN = userPIN;
    }

    // Method to validate user login by checking userID and userPIN
    public boolean validateUser(String userID, String userPIN) {
        return this.userID.equals(userID) && this.userPIN.equals(userPIN);
    }

    // Method to deposit a given amount into the account
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;  // add the amount to the balance
            transactionHistory += "Deposited: " + amount + "\n";  // update the transaction history
            System.out.println("Deposit successful. Current balance: " + balance);
        } else {
            System.out.println("Invalid amount.");  // error message for invalid deposit amounts
        }
    }

    // Method to withdraw a given amount from the account
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;  // subtract the amount from the balance
            transactionHistory += "Withdrew: " + amount + "\n";  // update the transaction history
            System.out.println("Withdrawal successful. Current balance: " + balance);
        } else {
            System.out.println("Invalid amount or insufficient balance.");  // error message for invalid withdrawals
        }
    }

    // Method to transfer money from this account to another ATM account
    public void transfer(double amount, ATM recipient) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;  // subtract the amount from the current user's balance
            recipient.deposit(amount);  // deposit the amount into the recipient's account
            transactionHistory += "Transferred: " + amount + " to " + recipient.userID + "\n";  // update the transaction history
            System.out.println("Transfer successful. Current balance: " + balance);
        } else {
            System.out.println("Invalid amount or insufficient balance.");  // error message for invalid transfers
        }
    }

    // Method to display the transaction history of the user
    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");  // if no transactions, display a message
        } else {
            System.out.println("Transaction History:");
            System.out.println(transactionHistory);  // display the transaction history
        }
    }

    // Method to get the current balance (if needed in other functionalities)
    public double getBalance() {
        return balance;
    }
}

// Main class for the ATM interface, where user interaction occurs
public class ATMInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Scanner object to take input from the user

        // Creating two ATM users for transfer functionality
        ATM user1 = new ATM("user123", "pin123");  // user1 is created with a specific ID and PIN
        ATM user2 = new ATM("user456", "pin456");  // user2 is created for transfer purposes

        System.out.println("Welcome to the ATM System");
        System.out.print("Enter User ID: ");
        String inputUserID = scanner.nextLine();  // take user ID input from the user

        System.out.print("Enter PIN: ");
        String inputUserPIN = scanner.nextLine();  // take user PIN input

        // Validate user ID and PIN for authentication
        if (user1.validateUser(inputUserID, inputUserPIN)) {
            ATM currentUser = user1;  // set the current user to user1 (valid user)
            boolean quit = false;  // flag to handle when the user chooses to quit

            // Loop to continuously show the menu until the user quits
            while (!quit) {
                System.out.println("\nATM Menu:");
                System.out.println("1. Transaction History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();  // get the user's menu choice

                // Switch case to handle user-selected operations
                switch (choice) {
                    case 1:
                        currentUser.showTransactionHistory();  // show transaction history
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();  // input withdrawal amount
                        currentUser.withdraw(withdrawAmount);  // perform withdrawal
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();  // input deposit amount
                        currentUser.deposit(depositAmount);  // perform deposit
                        break;
                    case 4:
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();  // input transfer amount
                        currentUser.transfer(transferAmount, user2);  // transfer money to user2
                        break;
                    case 5:
                        quit = true;  // exit the loop to quit the application
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");  // handle invalid choices
                }
            }
        } else {
            System.out.println("Invalid User ID or PIN.");  // error message for invalid login
        }

        scanner.close();  // close the scanner to avoid resource leaks
    }
}
