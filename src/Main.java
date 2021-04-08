import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final ArrayList<String> order = new ArrayList<>();

    public static void main(String[] args) {
        ArrayList<Task> tasks = scanTasks();
        ArrayList<Task> completedTasks = new ArrayList<>();
        RR rr = new RR(2);
        SJF sjf = new SJF();
        int roundCount = 0;
        int taskNumber = 0;

        while (tasks.size() != 0 || rr.isNotEmpty() || sjf.isNotEmpty()) {
            while (tasks.size() != 0 && tasks.get(0).getStartTime() == roundCount) {
                if (tasks.get(0).getPriority() == 1) {
                    sjf.addTask(tasks.get(0), taskNumber++);
                    tasks.remove(0);
                } else if (tasks.get(0).getPriority() == 0) {
                    rr.addTask(tasks.get(0), taskNumber++);
                    tasks.remove(0);
                }
            }
            if (sjf.isNotEmpty()) {
                rr.postpone();
                sjf.run();
                rr.waitForResume();
            } else if (rr.isNotEmpty()) {
                rr.run();
            }
            roundCount++;
        }

        for (String string : order) {
            System.out.print(string);
        }
        System.out.println();

        completedTasks.addAll(sjf.getCompletedTasks());
        completedTasks.addAll(rr.getCompletedTasks());
        completedTasks.sort(new OrderComparator());

        for (Task task : completedTasks) {
            System.out.print(task.getId() + ":" + task.getWaitTime());
            if (completedTasks.indexOf(task) != completedTasks.size() - 1) System.out.print(',');
        }
    }

    public static ArrayList<Task> scanTasks() {
        ArrayList<Task> taskList = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            if (!nextLine.equals(""))
                lines.add(nextLine);
            else break;
        }
        lines.forEach(line -> {
            String[] parameters = line.split(",");
            if (parameters.length != 4) return;
            String id = parameters[0];
            int priority = Integer.parseInt(parameters[1]);
            int startTime = Integer.parseInt(parameters[2]);
            int burstLength = Integer.parseInt(parameters[3]);

            taskList.add(new Task(id, priority, startTime, burstLength));
        });

        taskList.sort(new StartTimeComparator());
        return taskList;
    }

    public static void putInOrder(String taskId) {
        if (order.size() == 0)
            order.add(taskId);
        else if (!order.get(order.size() - 1).equals(taskId))
            order.add(taskId);
    }

    public static class StartTimeComparator implements Comparator<Task> {
        @Override
        public int compare(Task t1, Task t2) {
            return Integer.compare(t1.getStartTime(), t2.getStartTime());
        }
    }

    public static class OrderComparator implements Comparator<Task> {
        @Override
        public int compare(Task t1, Task t2) {
            return Integer.compare(t1.getNumber(), t2.getNumber());
        }
    }
}
