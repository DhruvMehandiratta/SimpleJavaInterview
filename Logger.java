import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Logger Rate Limiter — Question
 *
 * Design a logger system that receives a stream of messages along with timestamps.
 * Each message should be printed only if it was not printed in the last 10 seconds.
 *
 * ----------------------------------------------------
 *  Input / Behavior
 * timestamp is in seconds (increasing order)
 * Each call represents a new incoming message
 *
 * Return:
 * true → print message
 * false → ignore
 *
 * ----------------------------------------------------
 *  Example
 * Logger logger = new Logger();
 *
 * logger.shouldPrintMessage(1, "foo")  → true
 * logger.shouldPrintMessage(2, "bar")  → true
 * logger.shouldPrintMessage(3, "foo")  → false  (last "foo" was at 1, < 10 sec)
 * logger.shouldPrintMessage(11, "foo") → true   (11 - 1 >= 10)
 *
 *
 *
 *----------------------------------------------------
 *
 public class Logger {
 public Logger() { }

 public boolean shouldPrintMessage(int timestamp, String message) {
 // returns true if message should be printed
 // returns false otherwise
 }
 }
 */

public class Logger {
    public Logger() { }

    Map<String,Integer> messageRecord = new HashMap<>();
    public boolean shouldPrintMessage(int timestamp, String message) {
        if(messageRecord.containsKey(message)) {
            int timeDifference = timestamp - messageRecord.get(message);
            if (timeDifference >= 10) {
                messageRecord.put(message, timestamp);
                return true;
            } else {
                return false;
            }
        } else {
            messageRecord.put(message, timestamp);
            return true;
        }
        // returns true if message should be printed
        // returns false otherwise
    }


    /**
     * Follow-up
     * Limit each user to 10 messages per minute
     * Input:
     *
     * (userId, timestamp, message)
     *
     * boolean shouldAllowMessage(int timestamp, String userId, String message)
     *
     *  Rules
     * If user sent < 10 messages in last 60 sec → allow (return true)
     *
     * Else → reject (return false)
     *
     * Timestamp is increasing
     *
     * You still don’t care about message content now — just per user
     *
     *
     *
     **/
    Map<String, Queue<Integer>> recordMap = new HashMap<>();
    public boolean shouldAllowMessage(int timestamp, String userId, String message) {
        recordMap.putIfAbsent(userId, new LinkedList<>());

        Queue<Integer> queue = recordMap.get(userId);

        while(!queue.isEmpty() && timestamp - queue.peek() >= 60) {
            queue.poll();
        }

        if (queue.size() >= 10) {
            return false;
        } else {
            queue.offer(timestamp);
            return true;
        }
    }
}


