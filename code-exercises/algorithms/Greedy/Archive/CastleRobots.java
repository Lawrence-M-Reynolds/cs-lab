package Greedy;

/*
PDF file page 351 (book page 338)

You are responsible for the security of a castle. The castle has a circular
perimeter. A total of n robots patrol the perimeter—each robot is responsible for a
closed connected subset of the perimeter, i.e., an arc. (The arcs for different robots
may overlap.) You want to monitor the robots by installing cameras at the center
of the castle that look out to the perimeter. Each camera can look along a ray. To
save cost, you would like to minimize the number of cameras. See Figure 18.1 for an
example.

--
Input variables:
    perimeter distance from the wall


    n robots.
    Robot:
        - Start Angle (0 <= angle <= 2) (pi radians)
        - End Angle (0 <= angle <= 2) (pi radians)

--
Solution:
- Find arc segment (A) with the earliest ending past the zero mark. This arc may or may not have started before the 0 mark.
- If it started at the zero mark then we don't need to worry about overlapping arcs - if any do then they completely
envelop it, so this arc will still need it's own camera angle (which will also cover the one enveloping it).
- If it started before the zero mark then the ending of any arcs between the start of A and zero (and hence any within A)
could also be considered as valid camera angles (as they would cover A).

- So the greedy algorithm has to be run for each of these locations.
*/

import java.util.*;

class CastleRobots {
    public static void main(String... args) {
//        double[][] robotArcAngles = {{0,1},{2, 0.2},{0.3,0.4},{1.6,1.9}}; // result expected: 1,
//        double[][] robotArcAngles = {{11, 1},{0.5, 1.5}};
//        double[][] robotArcAngles = {{11, 1},{1.1, 2.1}};#

//        double[][] robotArcAngles = {
//                {11.0, 1.0},   // Robot A (Wide Wrap) -> 0.5
//                {11.5, 0.5},   // Robot B (Tiny Nested Wrap - Forces camera here) -> 0.5
//                {4.0, 6.0},    // Robot C (Wide Linear) -> 5.5
//                {4.5, 5.5},    // Robot D (Tiny Nested Linear - Forces camera here) -> 5.5
//                {8.0, 10.0},   // Robot E (Isolated Linear) -> 10
//                {0.0, 5.0}     // Robot F (Bridge) -> 0.5
//        };//[0.5, 5.5, 10.0]

//        double[][] robotArcAngles = {
//                {11.0, 11.2},  // Robot A: Ends "late" (11.2) -> 11.2
//                {11.1, 0.1},   // Robot B: Wraps around (Ends early at 0.1) -> 11.2
//                {11.3, 11.4}   // Robot C: Separate segment --> 11.4
//        };//[11.2, 11.4]

//        double[][] robotArcAngles = {
//                {5.0, 1.0}, // Wraps -> 4
//                {1.0, 3.0}, // -> 1
//                {2.0, 4.0}, // -> 4
//                {3.0, 5.0}, // -> 4
//                {4.0, 0.0}  // Ends at 0/6 // -> 1
//        };//[4.0, 1.0] -> 2 more solutions found

//        double[][] robotArcAngles = {
//                {0.0, 4.0},
//                {4.0, 8.0},
//                {8.0, 0.0}
//        };//[4.0, 8.0, 0.0] -> my solution found a better one [0.0, 8.0] and [8.0, 4.0]

        double[][] robotArcAngles = {
                {10.0, 2.0},  // Wide wrap
                {2.1, 2.2},   // Tiny, isolated
                {8.0, 11.0}   // Overlaps start of wrap
        };//[2.2, 11.0]

        long startTime1 = System.nanoTime();
        System.out.println(new Solution_Attempt1().solve(robotArcAngles, 12));
        long endTime1 = System.nanoTime();
        System.out.println("Time 1: " + (endTime1 - startTime1));
    }

    /**
     * The perimeter distance is arbitrary.
     *
     * Sort all robots by their last angle
     * Take the first one - the one with the earliest end.
     *
     * Find all Arcs that end within this first arc
     *
     * Iterate through each one and run the greedy algorithm.
     *
     *
     *
     */
    static class Solution_Attempt1 {
        long solve(double[][] robotArcAngles, double wrapValue) {

            List<RobotArc> robotArcs = Arrays.stream(robotArcAngles)
                    .map(robotArcAngle -> new RobotArc(robotArcAngle[0], robotArcAngle[1]))
                    .sorted(Comparator.comparing(RobotArc::angleRadians2))
                    .toList();

            // Identify all arcs to use as run start points.
            // Note that we can assume that moving clockwise (while selecting the end of each arc) is symmetrical with
            // moving anti-clockwise (while selecting the starts of each arc).
            List<Integer> greedyStartIndexes = new ArrayList<>();
            RobotArc firstRobotArc = robotArcs.getFirst();
            greedyStartIndexes.add(0);
            for(int i = robotArcs.size() - 1; i >= 0 ; i--) {
                RobotArc robotArc = robotArcs.get(i);
                // We only add if the end is more than the previous marked arc's start, and it must not be wrapping
                // so check the start is before the end.
                if (robotArc.angleRadians2() >= firstRobotArc.angleRadians1()
                        && robotArc.angleRadians1() < robotArc.angleRadians2()) {
                    greedyStartIndexes.add(i);
                } else {
                    break;
                }
            }

            // Preparing variable for each run
            int totalRuns = greedyStartIndexes.size();
            List<List<Double>> cameraAngleResults = new ArrayList<>(totalRuns);
            for (int runIndex = 0; runIndex < totalRuns; runIndex++) {
                cameraAngleResults.add(new ArrayList<>());
            }

            // Each run
            for (int runIteration = 0; runIteration < robotArcs.size(); runIteration++) {
                for (int runIndex = 0; runIndex < totalRuns; runIndex++) {
                    // wrap around for i + greedyStartIndex > max index of array
                    int greedyStartIndex = greedyStartIndexes.get(runIndex);

                    int robotArcCheckIndex = greedyStartIndex + runIteration;
                    robotArcCheckIndex = robotArcCheckIndex % robotArcs.size();

                    // Get the result storage list for run and it's current iteration
                    List<Double> runCameraAngleResults = cameraAngleResults.get(runIndex);
                    RobotArc robotArcCheck = robotArcs.get(robotArcCheckIndex);

                    if (runCameraAngleResults.isEmpty()){
                        // First Arc, so adding.
                        runCameraAngleResults.add(robotArcCheck.angleRadians2());
                    } else if (!isArcCovered(wrapValue, runCameraAngleResults, robotArcCheck)) {
                        runCameraAngleResults.add(robotArcCheck.angleRadians2());
                    }
                }
            }

            // Process results
            List<Double> optimumCameraAngles = cameraAngleResults.getFirst();
            for(List<Double> runResults : cameraAngleResults) {
                System.out.println("Result: " + runResults);
                if(runResults.size() < optimumCameraAngles.size()) {
                    optimumCameraAngles = runResults;
                }
            }

            System.out.println("Optimum result: " + optimumCameraAngles);
            return optimumCameraAngles.size();
        }

        private boolean isArcCovered(double wrapValue, List<Double> runCameraAngleResults, RobotArc robotArcCheck) {
            System.out.println("isArcCovered: " + runCameraAngleResults + " :::: " + robotArcCheck);
            for (Double cameraAngle : runCameraAngleResults) {
                if (robotArcCheck.angleRadians1() < robotArcCheck.angleRadians2()) {
                    // Not wrapping.
                    if(cameraAngle >= robotArcCheck.angleRadians1() && cameraAngle <= robotArcCheck.angleRadians2()) {

                        System.out.println("covered 1");
                        return true;
                    }
                } else {
                    // Wrapped interval.
                    if(cameraAngle >= robotArcCheck.angleRadians1() && cameraAngle <= wrapValue || cameraAngle <= robotArcCheck.angleRadians2() && cameraAngle >= 0) {
                        System.out.println("covered 2");
                        return true;
                    }
                }
            }
            System.out.println("not covered");
            return false;
        }
    }

    record RobotArc(double angleRadians1, double angleRadians2){}
}
