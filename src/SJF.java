import java.util.ArrayList;

public class SJF {
    private final ArrayList<Task> taskQueue = new ArrayList<>();
    private final ArrayList<Task> completedTasks = new ArrayList<>();
    private Task currentTask = new Task("", -1, -1, 0, -1);

    public void addTask(Task task) {
        this.taskQueue.add(task);
    }

    private Task findShortest() {
        Task shortest = new Task("", -1, -1, Integer.MAX_VALUE, -1);
        for (Task task : taskQueue) {
            if (task.getCpuTime() < shortest.getCpuTime())
                shortest = task;
        }
        return shortest;
    }

    public void run() {
        if (currentTask.getCpuTime() == 0)
            currentTask = findShortest();

        System.out.println("SJF current task: " + currentTask.getId());

        for (Task task : taskQueue) {
            task.tick();
        }

        String currentTaskId = currentTask.getId();
        currentTask.process();

        if (currentTask.getCpuTime() == 0) {
            taskQueue.remove(currentTask);
            completedTasks.add(currentTask);
        }
        Main.putInOrder(currentTaskId);
    }

    public boolean isNotEmpty() {
        return !taskQueue.isEmpty();
    }

    public ArrayList<Task> getCompletedTasks() {
        return completedTasks;
    }
}
