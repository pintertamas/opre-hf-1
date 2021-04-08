import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final ArrayList<String> order = new ArrayList<>();
    static int runTime = 0;

    public static void main(String[] args) {
        ArrayList<Task> tasks = scanTasks();
        ArrayList<Task> completedTasks = new ArrayList<>();
        RR rr = new RR(2);
        SJF sjf = new SJF();

        while (tasks.size() != 0 || rr.isNotEmpty() || sjf.isNotEmpty()) {
            while (tasks.size() != 0 && tasks.get(0).getStartTime() == runTime) {
                if (tasks.get(0).getPriority() == 1) {
                    sjf.addTask(tasks.get(0));
                    tasks.remove(0);
                } else if (tasks.get(0).getPriority() == 0) {
                    rr.addTask(tasks.get(0));
                    tasks.remove(0);
                }
            }

            if (sjf.isNotEmpty()) {
                sjf.run();
                rr.waitForResume();
                if (rr.isNotEmpty() && !rr.isAlreadyPostponed() && !rr.shouldNotPostpone())
                    rr.postpone();
            } else if (rr.isNotEmpty()) {
                rr.run();
                rr.setShouldNotPostpone(false);
            }
            runTime++;
        }

        for (String string : order) {
            System.out.print(string);
        }
        System.out.println();

        completedTasks.addAll(sjf.getCompletedTasks());
        completedTasks.addAll(rr.getCompletedTasks());
        Collections.sort(completedTasks);

        for (Task task : completedTasks) {
            System.out.print(task.getId() + ":" + task.getWaitTime());
            if(completedTasks.indexOf(task) != completedTasks.size() - 1) System.out.print(',');
        }
    }

    public static ArrayList<Task> scanTasks() {
        ArrayList<Task> taskList = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            if (!nextLine.equals(""))
                lines.add(nextLine);
            else break;
        }
        AtomicInteger i = new AtomicInteger();
        lines.forEach(line -> {
            String[] parameters = line.split(",");
            if(parameters.length != 4) return;
            String id = parameters[0];
            int priority = Integer.parseInt(parameters[1]);
            int startTime = Integer.parseInt(parameters[2]);
            int burstLength = Integer.parseInt(parameters[3]);

            taskList.add(new Task(id, priority, startTime, burstLength, i.getAndIncrement()));
        });
        return taskList;
    }

    public static void putInOrder(String taskId) {
        if (order.size() == 0)
            order.add(taskId);
        else if (!order.get(order.size() - 1).equals(taskId))
            order.add(taskId);
    }
}
