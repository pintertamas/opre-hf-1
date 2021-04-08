import java.util.ArrayList;

public class SJF {
    private final ArrayList<Task> taskQueue = new ArrayList<>();
    private final ArrayList<Task> completedTasks = new ArrayList<>();
    private Task currentTask = new Task(0);
    private boolean newTaskStarted;

    public void addTask(Task task) {
        this.taskQueue.add(task);
    }

    private Task findShortest() {
        Task shortest = new Task(Integer.MAX_VALUE);
        //System.out.println(taskQueue.size());
        for (Task task : taskQueue) {
            if (task.getCpuTime() < shortest.getCpuTime())
                shortest = task;
        }
        return shortest;
    }

    public void run() {
        if (currentTask.getCpuTime() == 0) {
            currentTask = findShortest();
            //System.out.println(currentTask.getCpuTime());
            newTaskStarted = true;
        }

        //System.out.println("SJF current task: " + currentTask.getId());

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
