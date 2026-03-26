package Greedy;

    /*
    PDF file 347/334

We consider the problem of assigning tasks to workers. Each worker must be assigned
exactly two tasks. Each task takes a fixed amount of time. Tasks are independent, i.e.,
there are no constraints of the form "Task 4 cannot start before Task 3 is completed."
Any task can be assigned to any worker.
We want to assign tasks to workers so as to minimize how long it takes before all
tasks are completed. For example, if there are 6 tasks whose durations are 5, 2, 1, 6,4,4
hours, then an optimum assignment is to give the first two tasks (i.e., the tasks with
duration 5 and 2) to one worker, the next two (1 and 6) to another worker, and the
last two tasks (4 and 4) to the last worker. For this assignment, all tasks will finish
after max(5 + 2, 1 + 6,4 + 4) = 8 hours.
Design an algorithm that takes as input a set of tasks and returns an optimum assignment.

    */

import java.util.*;

public class WorkerTasks {
    public static void main(String... args) {
        long startTime1 = System.nanoTime();
        System.out.println(new MySlowSolution().solution(Set.of(
                new Task("Task 1", 5),
                new Task("Task 2", 2),
                new Task("Task 3", 1),
                new Task("Task 4", 6),
                new Task("Task 5", 4),
                new Task("Task 6", 4)
        )));
        long endTime1 = System.nanoTime();
        System.out.println("Time 1: " + (endTime1 - startTime1));

        long startTime2 = System.nanoTime();
        System.out.println(new BookSolution().solution(Set.of(
                new Task("Task 1", 5),
                new Task("Task 2", 2),
                new Task("Task 3", 1),
                new Task("Task 4", 6),
                new Task("Task 5", 4),
                new Task("Task 6", 4)
        )));
        long endTime2 = System.nanoTime();
        System.out.println("Time 2: " + (endTime2 - startTime2));

        // expected: max(5 + 2, 1 + 6,4 + 4)
    }

    record Task(String taskName, int time) {

    }

    record Worker(Task task1, Task task2) {

    }
}

/*
This is the solution from the book. Much quicker as it only sorts once - O(NlogN) And then
Iterates once - O(N). To in total O(NlogN) + O(N). But the first is dominant so the result is:
O(NlogN)

 */
class BookSolution {
    List<WorkerTasks.Worker> solution(Set<WorkerTasks.Task> tasks) {
        var tasksList = new ArrayList<>(tasks);
        Collections.sort(tasksList, Comparator.comparingInt(WorkerTasks.Task::time));
        var workers = new ArrayList<WorkerTasks.Worker>(3);

        for (int i = 0, j = tasksList.size() - 1; i < j; i++, j--) {
            workers.add(new WorkerTasks.Worker(tasksList.get(i), tasksList.get(j)));
        }

        return workers;
    }
}

/*
3 workers in total
We want to have roughly equal total task length assigned to all of them.

Not greedy because giving any tasks to one of them reduces the options to give to the other workers

> Longest task should be paired with the shortest

Note: This is not as efficient as suggested in the book. This is O(N^2) as it's searching through the
collection multiple times.
 */
class MySlowSolution {
    List<WorkerTasks.Worker> solution(Set<WorkerTasks.Task> tasks) {
        var tasksMutable = new HashSet<WorkerTasks.Task>(tasks);
        var workers = new ArrayList<WorkerTasks.Worker>(3);

        workers.add(createWorkerWithShortestAndLongestTask(tasksMutable));
        workers.add(createWorkerWithShortestAndLongestTask(tasksMutable));
        workers.add(createWorkerWithShortestAndLongestTask(tasksMutable));

        return workers;
    }

    private WorkerTasks.Worker createWorkerWithShortestAndLongestTask(Set<WorkerTasks.Task> tasks) {
        WorkerTasks.Task maxTask = null;
        WorkerTasks.Task minTask = null;

        for(WorkerTasks.Task task : tasks) {
            //minTask = Objects.isNull(minTask) ? task :
            if (Objects.isNull(minTask)) {
                maxTask = minTask = task;
            } else {
                if (task.time() < minTask.time()) {
                    minTask = task;
                } else if (task.time() > maxTask.time()) {
                    maxTask = task;
                }

            }
        }

        tasks.remove(minTask);
        tasks.remove(maxTask);

        return new WorkerTasks.Worker(minTask, maxTask);

    }
}