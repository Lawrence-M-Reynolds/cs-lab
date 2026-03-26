package Greedy;

/*
PDF file page 349 (book page 336)

Consider a foreman responsible for a number of tasks on the factory floor. Each task
starts at a fixed time and ends at a fixed time. The foreman wants to visit the floor to
check on the tasks. Your job is to help him minimize the number of visits he makes.
In each visit, he can check on all the tasks taking place at the time of the visit. A visit
takes place at a fixed time, and he can only check on tasks taking place at exactly that
time. For example, if there are tasks at times [0,3],[2,6],[3,4],[6,9], then visit times
0, 2,3,6 cover all tasks. A smaller set of visit times that also cover all tasks is 3,6. In
the abstract, you are to solve the following problem.

You are given a set of closed intervals. Design an efficient algorithm for finding a
minimum sized set of numbers that covers all the intervals.
*/

import java.util.*;

class IntervalCoveringProblem {
    public static void main(String... args) {
        Long[][] taskTimesArray = {{0L,3L},{2L, 6L},{3L,4L},{6L,9L}}; // result expected: 3,6 -> 2
//        Long[][] taskTimesArray = {{0L,2L},{1L, 3L},{3L,4L},{1L,7L},{5L,6L},{6L,8L},{5L,7L}}; // result expected: [2, 3, 5, 6] -> 4

        long startTime1 = System.nanoTime();
        System.out.println(new MySolution().solve(taskTimesArray));
        long endTime1 = System.nanoTime();
        System.out.println("Time 1: " + (endTime1 - startTime1));
    }

    /**
     * Solution suggested:
     *
     * long a = -1;
     *
     * Sort all tasks by their end time.
     * If interval start time > a
     *  add the interval end-time to result
     *  a = interval end-time
     * otherwise
     *  skip this interval
     *
     */
    static class MySolution {
        long solve(Long[][] taskTimesArray) {
            Task[] tasks = createTasksArray(taskTimesArray);

            Arrays.sort(tasks, Comparator.comparing(Task::endTime));

            List<Long> visitTimes = new ArrayList<>();
            long lastVisitTime = -1;
            for (Task task : tasks) {
                long taskEndTime = task.endTime();
                if (task.startTime() > lastVisitTime) {
                    visitTimes.add(taskEndTime);
                    lastVisitTime = taskEndTime;
                }
            }

            System.out.println("Times: " + visitTimes);
            return visitTimes.size();
        }


        private Task[] createTasksArray(Long[][] taskTimesArray) {
            List<Task> tasks = new ArrayList<>(taskTimesArray.length);
            for(Long[] taskTimes : taskTimesArray) {
                tasks.add(new Task(taskTimes[0], taskTimes[1]));
            }

            return tasks.toArray(new Task[]{});
        }
    }

    /**
     * This is a very bad solution that doesn't find the optimum.
     */
    static class MySolution1 {
        long solve(Long[][] taskTimesArray) {
            Task[] tasks = createTasksArray(taskTimesArray);
            Task[] tasksSortedByStartTime = Arrays.copyOf(tasks, tasks.length);
            Task[] tasksSortedByEndTime = Arrays.copyOf(tasks, tasks.length);

            Arrays.sort(tasksSortedByStartTime, Comparator.comparing(Task::startTime));
            Arrays.sort(tasksSortedByEndTime, Comparator.comparing(Task::endTime));

            Set<Long> visitingTimes = new HashSet<>();
            long latestStart = Long.MAX_VALUE;
            int latestStartIndex = tasksSortedByEndTime.length - 1;

            long earliestEnd = 0;
            int earliestEndIndex = 0;
            while(true) {
                long prevLatestStart = latestStart;
                long prevEarliestEnd = earliestEnd;

                // For each below, keep checking until valid according to above, return the value and the index
                latestStartIndex = getLatestStartIndex(tasksSortedByStartTime, latestStart, latestStartIndex);
                earliestEndIndex = getEarliestEndIndex(tasksSortedByEndTime, earliestEnd, earliestEndIndex);

                Task latestStartTask = tasksSortedByStartTime[latestStartIndex];
                Task earliestEndTask = tasksSortedByEndTime[earliestEndIndex];
                latestStart = latestStartTask.startTime();
                earliestEnd = earliestEndTask.endTime();

                if(latestStart <= earliestEnd) {
                    /*
                        EARLIEST START IS BEFORE THE LATEST END SO THESE TASKS ARE EITHER THE SAME OR OVERLAPPING.

                        if the same then check if latestStart <= prevEarliestEnd || currentEnd >= prevCurrentStart

                        if they are different then we only add one if neither of them overlap
                        with the preevius currentstart/end
                     */

                    boolean startAndEndTaskSame = latestStartTask.equals(earliestEndTask);
                    if(startAndEndTaskSame && !(latestStart > prevEarliestEnd || earliestEnd < prevLatestStart)) {
                        // They are the same task, and it does not overlap with previous tasks.
                        visitingTimes.add(latestStart);
                    }

                    if(!startAndEndTaskSame) {
                        // They are different tasks. Only add either one if their opposing end doesn't overlap with the
                        // previous values

                        if (latestStart > prevEarliestEnd) {
                            visitingTimes.add(latestStart);
                        }

                        if (earliestEnd < prevLatestStart) {
                            visitingTimes.add(earliestEnd);
                        }
                    }

                    break;
                } else {
                    /*
                    SEPARATE TASKS. THEY ARE NOT OVERLAPPING.
                    So we may add either 1 or two times.

                    But it's possible that the early end task started on or before the end of the previous one that ended.
                    And similarly, it's possible that the late start task ended after the previous start task.
                    */
                    if (latestStart > prevEarliestEnd) {
                        visitingTimes.add(latestStart);
                    }

                    if (earliestEnd < prevLatestStart) {
                        visitingTimes.add(earliestEnd);
                    }
                }

            }

            System.out.println("Result: " + visitingTimes);

            return visitingTimes.size();
        }

        /*
        Recursive:
            simplist case - currentStartIndex = 0, or task.startime <= currentStart
         */
        private int getLatestStartIndex(Task[] tasksSortedByStartTime, long currentStart, int currentStartIndex) {
            if (currentStartIndex == 0 || tasksSortedByStartTime[currentStartIndex].startTime() < currentStart) {
                return currentStartIndex;
            }

            return getLatestStartIndex(tasksSortedByStartTime, currentStart, --currentStartIndex);
        }

        /*
        Recursive:
            simplist case - currentStartIndex = tasksSortedByEndTime.length -1, or task.endime >= currentStart
         */
        private int getEarliestEndIndex(Task[] tasksSortedByEndTime, long currentEnd, int currentEndIndex) {
            if (currentEndIndex == tasksSortedByEndTime.length -1 ||
                    tasksSortedByEndTime[currentEndIndex].endTime() > currentEnd) {
                return currentEndIndex;
            }

            return getEarliestEndIndex(tasksSortedByEndTime, currentEnd, ++currentEndIndex);
        }

        private Task[] createTasksArray(Long[][] taskTimesArray) {
            List<Task> tasks = new ArrayList<>(taskTimesArray.length);
            for(Long[] taskTimes : taskTimesArray) {
                tasks.add(new Task(taskTimes[0], taskTimes[1]));
            }

            return tasks.toArray(new Task[]{});
        }
    }
}

record Task (long startTime, long endTime) {

}