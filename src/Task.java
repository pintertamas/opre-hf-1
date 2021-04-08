public class Task implements Comparable<Task> {
    private final String id;
    private final int priority;
    private final int startTime;
    private int cpuTime;
    private int waitTime;
    private final int number;

    public Task(String id, int priority, int startTime, int cpuTime, int number) {
        this.id = id;
        this.priority = priority;
        this.startTime = startTime;
        this.cpuTime = cpuTime;
        this.waitTime = 0;
        this.number = number;
    }

    public Task(int cpuTime) {
        this.id = "";
        this.priority = -1;
        this.startTime = -1;
        this.cpuTime = cpuTime;
        this.waitTime = 0;
        this.number = -1;
    }

    public void tick() {
        this.waitTime++;
    }

    public void process() {
        this.waitTime--;
        this.cpuTime--;
    }

    public String getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getCpuTime() {
        return cpuTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public int compareTo(Task task) {
        return Integer.compare(this.number, task.number);
    }
}
