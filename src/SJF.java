import java.util.ArrayList;

public class SJF {
    private final ArrayList<Task> taskQueue = new ArrayList<>();
    private final ArrayList<Task> completedTasks = new ArrayList<>();
    private Task currentTask = new Task(0);
    private boolean newTaskStarted;

    public void addTask(Task task, int number) {
        task.setNumber(number);
        this.taskQueue.add(task);
    }

    private Task findShortest() {
        Task shortest = new Task(Integer.MAX_VALUE);
        for (Task task : taskQueue) {
            if (task.getCpuTime() < shortest.getCpuTime()) {
                shortest = task;
            }
        }
        return shortest;
    }

    public void run() {
        if (currentTask.getCpuTime() == 0) {
            currentTask = findShortest();
            newTaskStarted = true;
        }

        Main.putInOrder(currentTask.getId());

        for (Task task : taskQueue) {
            task.tick();
        }
        currentTask.process();

        if (currentTask.getCpuTime() == 0) {
            taskQueue.remove(currentTask);
            completedTasks.add(currentTask);
        }
    }

    public boolean isNotEmpty() {
        return !taskQueue.isEmpty();
    }

    public ArrayList<Task> getCompletedTasks() {
        return completedTasks;
    }

    public boolean newTaskStarted() {
        return newTaskStarted;
    }
}
