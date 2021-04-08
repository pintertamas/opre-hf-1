import java.util.ArrayDeque;
import java.util.ArrayList;

public class RR {
    public int timeSlice;
    private final ArrayDeque<Task> taskQueue;
    private final ArrayList<Task> completedTasks;
    private int runTime;
    private boolean isActive;

    public RR(int timeSlice) {
        this.timeSlice = timeSlice;
        this.taskQueue = new ArrayDeque<>();
        this.completedTasks = new ArrayList<>();
        this.runTime = 0;
        this.isActive = false;
    }

    public void addTask(Task newTask) {
        this.taskQueue.add(newTask);
    }

    public void postpone() {
        this.taskQueue.add(this.taskQueue.pop());
        alreadyPostponed = true;
        runTime = 0;
    }

    public void waitForResume() {
        for (Task task : taskQueue)
            task.tick();
    }

    public boolean isNotEmpty() {
        return !taskQueue.isEmpty();
    }

    public void run() {
        if (runTime == timeSlice) {
            postpone();
        }
        this.isActive = true;

        for (Task task : taskQueue) {
            task.tick();
        }

        //System.out.println("RR current task: " + taskQueue.getFirst().getId());

        Main.putInOrder(taskQueue.getFirst().getId());

        taskQueue.getFirst().process();
        runTime++;

        if (taskQueue.getFirst().getCpuTime() == 0) {
            completedTasks.add(taskQueue.pop());
            runTime = 0;
        }
    }

    public ArrayList<Task> getCompletedTasks() {
        return completedTasks;
    }
}
