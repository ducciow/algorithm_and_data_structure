package systematic.section14_Greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @Author: duccio
 * @Date: 14, 04, 2022
 * @Description: Given an array of meetings, where each meeting has start time and end time in forms of integers, return
 *      the maximum number of meetings that can take place without overlap.
 * @Note:   Ver1. Greedy strategy: prioritize meetings with earlier end time.
 *          Ver2. Brute force.
 */
public class Code02_SlotArrange {

    public static void main(String[] args) {
        validate();
    }

    public static class Meeting {
        int start;
        int end;

        public Meeting(int s, int e) {
            start = s;
            end = e;
        }
    }

    public static int slotArrange1(Meeting[] meetings) {
        if (meetings == null || meetings.length == 0) {
            return 0;
        }
        Arrays.sort(meetings, new EndComparator());
        int ans = 0;
        int timeline = 0;
        for (int i = 0; i < meetings.length; i++) {
            if (meetings[i].start >= timeline) {
                ans++;
                timeline = meetings[i].end;
            }
        }
        return ans;
    }

    public static class EndComparator implements Comparator<Meeting> {

        @Override
        public int compare(Meeting o1, Meeting o2) {
            return o1.end - o2.end;
        }
    }


    public static int slotArrange2(Meeting[] meetings) {
        if (meetings == null || meetings.length == 0) {
            return 0;
        }
        return process2(meetings, 0, 0);
    }

    public static int process2(Meeting[] toArrange, int arranged, int timeline) {
        if (toArrange.length == 0) {
            return arranged;
        }
        int maxArranged = arranged;
        for (int i = 0; i < toArrange.length; i++) {
            if (toArrange[i].start >= timeline) {
                Meeting[] remains = copyExcept(toArrange, i);
                maxArranged = Math.max(maxArranged, process2(remains, arranged + 1, toArrange[i].end));
            }
        }
        return maxArranged;
    }

    public static Meeting[] copyExcept(Meeting[] arr, int idx) {
        Meeting[] ret = new Meeting[arr.length - 1];
        int j = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i != idx) {
                ret[j++] = arr[i];
            }
        }
        return ret;
    }

    public static Meeting[] generateMeetings(int maxL, int maxV) {
        Meeting[] meetings = new Meeting[(int) (Math.random() * (maxL + 1))];
        for (int i = 0; i < meetings.length; i++) {
            int time1 = (int) (Math.random() * (maxV + 1));
            int time2 = (int) (Math.random() * (maxV + 1));
            if (time1 == time2) {
                meetings[i] = new Meeting(time1, time1 + 1);
            } else {
                meetings[i] = new Meeting(Math.min(time1, time2), Math.max(time1, time2));
            }
        }
        return meetings;
    }

    public static void validate() {
        int numTest = 10000;
        int maxL = 12;
        int maxV = 24;
        for (int i = 0; i < numTest; i++) {
            Meeting[] meetings = generateMeetings(maxL, maxV);
            int ans1 = slotArrange1(meetings);
            int ans2 = slotArrange2(meetings);
            if (ans1 != ans2) {
                System.out.println("Failed on case: " + Arrays.toString(meetings));
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
