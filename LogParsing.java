import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Log Parsing / Event Processing (VERY IMPORTANT)
 * 🧩 Problem
 * You get logs like:
 *
 * ["user1 login 10",
 *  "user2 login 15",
 *  "user1 logout 20",
 *  "user2 logout 25"]
 * 👉 Return total session time per user:
 *
 * user1 → 10
 * user2 → 10
 *
 * Concepts:
 * parsing strings
 *
 * map of user → last login
 *
 * accumulation
 */

public class LogParsing {
    Map<String, Integer> loginMap;
    Map<String, Integer> sessionTime;

    public LogParsing() {
        loginMap = new HashMap<>();
        sessionTime = new HashMap<>();
    }

    public int getSessionTime(String userId) {
        return sessionTime.getOrDefault(userId, 0);
    }

    public void parse(List<String> input) {
        for (String command : input) {
            String[] parts = command.split(" ");
            String userId = parts[0];
            String operation = parts[1];
            int timestamp = Integer.parseInt(parts[2]);

            switch (operation) {
                case "login":
                    loginMap.put(userId, timestamp);
                    break;
                case "logout":
                    if(!loginMap.containsKey(userId)) break;
                    sessionTime.put(userId, sessionTime.getOrDefault(userId,0) + timestamp - loginMap.get(userId));
                    loginMap.remove(userId);
                    break;
            }
        }

        for (String userId : sessionTime.keySet()) {
            printSessionTime(userId);
        }
    }

    private Map<String, Integer> printSessionTime(String userId) {
        return Map.of(userId, sessionTime.get(userId));
    }
}
