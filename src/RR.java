import java.util.ArrayDeque;
import java.util.ArrayList;

public class RR {
    public int timeSlice;
    private final ArrayDeque<Task> taskQueue;
    private final ArrayList<Task> completedTasks;
    private int runTime;
    private boolean alreadyPostponed;
    private boolean shouldNotPostpone;

    public RR(int timeSlice) {
        this.timeSlice = timeSlice;
        this.taskQueue = new ArrayDeque<>();
        this.completedTasks = new ArrayList<>();
        this.runTime = 0;
        this.alreadyPostponed = false;
        this.shouldNotPostpone = true;
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
        alreadyPostponed = false;
        for (Task task : taskQueue) {
            task.tick();
        }

        String currentTaskId = taskQueue.getFirst().getId();
        taskQueue.getFirst().process();
        runTime++;

        if (taskQueue.getFirst().getCpuTime() == 0) {
            completedTasks.add(taskQueue.pop());
            runTime = 0;
        } else if (runTime == timeSlice) {
            postpone();
        }

        if (taskQueue.isEmpty())
            shouldNotPostpone = true;
        Main.putInOrder(currentTaskId);
    }

    public ArrayList<Task> getCompletedTasks() {
        return completedTasks;
    }

    public boolean isAlreadyPostponed() {
        return alreadyPostponed;
    }

    public boolean shouldNotPostpone() {
        return shouldNotPostpone && runTime == 0;
    }

    public void setShouldNotPostpone(boolean shouldNotPostpone) {
        this.shouldNotPostpone = shouldNotPostpone;
    }
}
