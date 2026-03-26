package Greedy;

/*
PDF file page 351 (book page 338)

There are a number of points in the plane that you want to observe. You are
located at the point (0,0). You can rotate about this point, and your field-of-view is
a fixed angle. Which direction should you face to maximize the number of visible
points?

---

Parameters:
    - fieldOfViewAngle (0-180)
    - pointsList (List of 2-d vectors)

Strategy:
Determine the angle each points makes from North - use Math.atan2
-> This angle is the only determining factor as the distance won't have any affect on whether it is inside/outsise the fov.
Sort this into a list of angles "pointAnglesSorted".

NOTE: it's possible that some may have the same angle

Iterate through each angle.
- If it's the same angle as the previous iteration then skip, it will have been included there.
- Add the fov to the angle -> "fovEnd"
- Count ahead of this point in the angles list and count how many are below fovEnd. break when false.
    -> take into account the wrapping at the x axis.




*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class PointsOn2dPlane {
    public static void main(String... args) {
//        int[][] points = {{10, 10}}; // result expected: pi/2 - 0.7853981633974483
//        int[][] points = {{10, 10}}; // result expected: pi/2 - 0.7853981633974483
//        double fovAnglePiRadians = 0.25; // 1/8 of a circle

//        int[][] points = {{10, 0}, {-10, 0}}; // Two points, 180 degrees apart
//        double fovAnglePiRadians = 0.1;       // Small FOV (approx 18 degrees)

        int[][] points = {{355, 0}, {5, 0}};
        double fovAnglePiRadians = 0.349066;

        long startTime1 = System.nanoTime();
        System.out.println(new Solution().solve(points, fovAnglePiRadians));
        long endTime1 = System.nanoTime();
        System.out.println("Time 1: " + (endTime1 - startTime1));
    }

    static class Solution {
        double solve(int[][] points, double fovAnglePiRadians) {
            int numPoints = points.length;

            // convert fovAnglePiRadians into radians
            double fovAngleRadians = fovAnglePiRadians * Math.PI;

            // Covert each point into a sorted list of angles from the x axis (in radians).
            List<Double> pointAnglesRadians = new ArrayList<>(numPoints * 2);
            List<Double> pointAnglesRadiansNonWrapped = Arrays.stream(points)
                    .map(point -> Math.atan2(point[1], point[0]))
                    .map(signedAngle -> signedAngle >= 0 ? signedAngle : (2 * Math.PI) + signedAngle)
                    .sorted()
                    .toList();

            // Adding the same values but + 360 degrees so that we don't have to wrap later on.
            List<Double> pointAnglesRadiansWrappedValues = pointAnglesRadiansNonWrapped.stream()
                    .map(angle -> angle + (2 * Math.PI))
                    .toList();

            pointAnglesRadians.addAll(pointAnglesRadiansNonWrapped);
            pointAnglesRadians.addAll(pointAnglesRadiansWrappedValues);

            /*
             Iterate through each angle and look ahead to how many angles the fov covers.
             - in the main iteration, skip points that are the same as the last one.
             - Use "sliding window" -> on each iteration, retain the information on the results from the last.
             - when looking ahead and the end of the list is reached, start from the beginning again, but add 2 * pi radians to each value.
             */

            // Find initial pov count.
            double leftWindowAngle = pointAnglesRadians.getFirst();
            double rightWindowAngle = leftWindowAngle + fovAngleRadians;

            int currentSumPoints = 1;
            int rightWindowIndex = 0;
            for (int pointCheckIndex = 1; pointCheckIndex < numPoints; pointCheckIndex++) {
                Double pointCheckAngle = pointAnglesRadians.get(pointCheckIndex);
                if (pointCheckAngle <= rightWindowAngle) {
                    currentSumPoints++;
                    rightWindowIndex = pointCheckIndex;
                } else {
                    break;
                }
            }

            System.out.println("initial rightWindowIndex: " + rightWindowIndex);
            System.out.println("initial leftWindowAngle: " + leftWindowAngle);
            System.out.println("initial maxSumPoints: " + currentSumPoints);

            int maxSumPoints = currentSumPoints;
            double optimumLeftWindowAngle = leftWindowAngle;
            double prevLeftWindowAngle = leftWindowAngle;
            for (int leftWindowIndex = 1; leftWindowIndex < numPoints; leftWindowIndex++) {
                leftWindowAngle = pointAnglesRadians.get(leftWindowIndex);

                // Subtract the previous point, but don't set to zero as we must include this point.
                currentSumPoints = Math.max(1, (currentSumPoints-1));

                if (leftWindowAngle == prevLeftWindowAngle) {
                    // This point falls on the same angle as the previous, so skip this.
                    continue;
                }

                rightWindowAngle = leftWindowAngle + fovAngleRadians;

                // If the previous point didn't have any other points in the fov then rightWindowIndex == leftWindowIndex - 1.
                // In that case we want to search from leftWindowIndex + 1 to start looking through a new window, rather than
                // rightWindowIndex + 1 to look incrementally on from the existing one.
                int checkIndexStart = rightWindowIndex == (leftWindowIndex - 1) ? leftWindowIndex + 1 : rightWindowIndex + 1;
                System.out.println("checkIndexStart : " + checkIndexStart);
                for (int pointCheckIndex = checkIndexStart; pointCheckIndex < pointAnglesRadians.size(); pointCheckIndex++) {
                    Double pointCheckAngle = pointAnglesRadians.get(pointCheckIndex);
                    if (pointCheckAngle <= rightWindowAngle) {
                        System.out.println("add point : " + pointCheckAngle);
                        currentSumPoints++;
                        rightWindowIndex = pointCheckIndex;
                    } else {
                        break;
                    }
                }

                if (currentSumPoints > maxSumPoints) {
                    optimumLeftWindowAngle = leftWindowAngle;
                    maxSumPoints = Math.max(currentSumPoints, maxSumPoints);
                }
                prevLeftWindowAngle = leftWindowAngle;
            }

            System.out.println("maxSumPoints: " + maxSumPoints);
            return optimumLeftWindowAngle;
        }

    }
}
