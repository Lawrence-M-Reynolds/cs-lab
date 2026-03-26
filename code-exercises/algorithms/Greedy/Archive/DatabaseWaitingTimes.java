package Greedy;

    /*
A database hasto respond to a set of client SQL queries. The service time required for
each query is known in advance. For this application, the queries must be processed
by the database one at a time, but can be done in any order. The time a query waits
before its turn comesis called its waiting time.

Given service timesfor a set of queries, compute a schedule for processing the queries
that minimizes the total waiting time. Return the minimum waiting time. For
example, if the service times are (2,5,1,3), if we schedule in the given order, the total
waiting time is0+(2)+(2+5)+(2+5+l) = 17. If however,weschedule queriesin order
of decreasing service times, the total waiting time is 0 + (5) + (5 + 3) + (5 + 3+ 2) = 23.
As we will see, for this example, the minimum waiting time is 10.

(2,5,1,3)

default order:
0 + (2) + (2+5) + (2 + 5 + 1) = 17

descending order:
0 + (5) + (5 + 3) + (5 + 3 + 2) = 23

Optimal is 10

    */

import java.util.*;

public class DatabaseWaitingTimes {
    public static void main(String... args) {
        long startTime1 = System.nanoTime();
        System.out.println(new MySolution().findMinimumWaitingTime(new int[] {2,5,1,3}));
        long endTime1 = System.nanoTime();
        System.out.println("Time 1: " + (endTime1 - startTime1));


    }
}

/*
(2,5,1,3)

default order:
0 + (2) + (2+5) + (2 + 5 + 1) = 17

descending order:
0 + (5) + (5 + 3) + (5 + 3 + 2) = 23

Optimal is 10



I think this is the optimum solution:
O(NlogN) due to the sort
O(N) due to the single iteration

So it's O(NlogN)

Mathematically you can show from the series that:
W = sum((N-n)t_n) (0 <= n <= N)

where t_n is the value of the array element at index n
=====
This is greedy because, once it's in order we can select the best value at each step as we move along.
There's no need to back-track to previous values, you just iterate to the end.
 */
class MySolution {
    long findMinimumWaitingTime(int[] queryServiceTimes) {
        Arrays.sort(queryServiceTimes);
        int valueCount = queryServiceTimes.length;
        long totalWaitingTime = 0;

        for (int i = 0; i < valueCount; i++) {
            totalWaitingTime += (long) (valueCount - (i + 1)) * queryServiceTimes[i];
        }

        return totalWaitingTime;
    }
}