import java.util.Deque;
import java.util.*;

/**
 At Stripe, we process and track money movement between users.

 You are asked to build a simplified transaction system.


 📥 Input
 You are given a list of strings representing operations:

 [
 "DEPOSIT user1 100",
 "DEPOSIT user2 50",
 "TRANSFER user1 user2 30",
 "WITHDRAW user2 70",
 "BALANCE user1",
 "BALANCE user2"
 ]

 📌 Supported Operations
 1. DEPOSIT <user> <amount>
 Add amount to the user’s balance

 If user does not exist, create it

 2. WITHDRAW <user> <amount>
 Subtract amount from user’s balance

 If balance is insufficient → ignore the operation

 3. TRANSFER <fromUser> <toUser> <amount>
 Transfer money from one user to another

 If fromUser has insufficient balance → ignore the operation

 If toUser does not exist → create it

 4. BALANCE <user>
 Return the current balance of the user

 If user does not exist → return 0

 📤 Output
 Return a list of integers for each BALANCE operation in order.

 Example:
 Input:

 [
 "DEPOSIT user1 100",
 "DEPOSIT user2 50",
 "TRANSFER user1 user2 30",
 "WITHDRAW user2 70",
 "BALANCE user1",
 "BALANCE user2"
 ]
 Output:

 [70, 10]

 ⚠️ Requirements
 Do not crash on invalid operations

 Ignore operations that cannot be completed

 Focus on:

 correctness

 clean code

 readability

 Performance is not the priority
 */

public class Transaction {

    Map<String, Integer> ledger;
    //NEW
    Map<String, Deque<String>> history;

    public Transaction() {
        ledger = new HashMap<>();
        history = new HashMap<>();
    }

    // NEW
    private void addHistory(String user, String record) {
        history.putIfAbsent(user, new LinkedList<>());
        Deque<String> dq = history.get(user);

        dq.addFirst(record);

        if (dq.size() > 5) {
            dq.removeLast();
        }
    }

    public void deposit(String userId, int amount) {
        ledger.put(userId, ledger.getOrDefault(userId, 0) + amount);
        // NEW
        addHistory(userId, "DEPOSIT" + amount);
    }

    public void withdraw(String userId, int amount) {
        ledger.putIfAbsent(userId, 0);
        if (ledger.get(userId) >= amount) {
            ledger.put(userId, ledger.get(userId) - amount);
            // NEW
            addHistory(userId, "WITHDRAW" + amount);
        }
    }

    public void transfer(String originUserId, String destinationUserId, int amount) {
        ledger.putIfAbsent(originUserId, 0);
        ledger.putIfAbsent(destinationUserId, 0);
        if (ledger.get(originUserId) >= amount) {
            ledger.put(originUserId, ledger.get(originUserId) - amount);
            ledger.put(destinationUserId, ledger.get(destinationUserId) + amount);

            // NEW
            addHistory(originUserId, "TRANSFER TO "+ destinationUserId + " " + amount);
            addHistory(destinationUserId, "TRANSFER FROM " + originUserId + " " + amount);
        }
    }

    public int balance(String userId) {
        return ledger.getOrDefault(userId, 0);
    }

    // NEW
    public List<String> getHistory(String userId) {
        return new ArrayList<> (history.getOrDefault(userId, new LinkedList<>()));
    }

    public List<Object> process(List<String> input) {
        List<Object> results = new ArrayList<>();
        for (String instruction : input) {
            String[] operands = instruction.split(" ");
            String operator = operands[0];
            switch (operator) {
                case "DEPOSIT":
                    deposit(operands[1], Integer.parseInt(operands[2]));
                    break;
                case "WITHDRAW":
                    withdraw(operands[1], Integer.parseInt(operands[2]));
                    break;
                case "TRANSFER":
                    transfer(operands[1], operands[2], Integer.parseInt(operands[3]));
                    break;
                case "BALANCE":
                    results.add(balance(operands[1]));
                    break;
                case "HISTORY":
                    results.add(getHistory(operands[1]));
                    break;
            }
        }
        return results;
    }

    public static void main(String[] args) {
        List<String> input = Arrays.asList(
                "DEPOSIT user1 100",
                "DEPOSIT user2 50",
                "TRANSFER user1 user2 30",
                "WITHDRAW user2 70",
                "BALANCE user1",
                "BALANCE user2"
        );
        Transaction t = new Transaction();
        System.out.println(t.process(input));
    }
}


/**
 Harder Version: Transaction System + History
 🧩 Problem
 Extend your transaction system to support transaction history.

 📌 New Requirement
 Add a new operation:

 5. HISTORY <user>
 Return the last 5 transactions for that user (most recent first).

 📥 Example Input
 [
 "DEPOSIT user1 100",
 "TRANSFER user1 user2 30",
 "WITHDRAW user1 20",
 "DEPOSIT user1 50",
 "TRANSFER user2 user1 10",
 "HISTORY user1"
 ]
 📤 Expected Output
 [
 [
 "TRANSFER from user2 to user1 10",
 "DEPOSIT 50",
 "WITHDRAW 20",
 "TRANSFER from user1 to user2 30",
 "DEPOSIT 100"
 ]
 ]
 **/


// adding new lines in the existing code with a comment //NEW