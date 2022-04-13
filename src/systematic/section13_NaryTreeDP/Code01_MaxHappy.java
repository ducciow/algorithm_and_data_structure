package systematic.section13_NaryTreeDP;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: duccio
 * @Date: 13, 04, 2022
 * @Description: A company has the organisation structure as an n-ary tree. Now it wants to invite its employees to a
 *      party, requiring any employee cannot come if their direct manager or direct subordinates come. Evey person has
 *      a happy score, and then return the maximum total happy score of the party.
 * @Note:   1. Info: present, absent.
 *          2. If an employee presents, plus the happy score where all his subordinates are absent.
 *          3. Otherwise, plus the bigger happy score of his subordinates being present or absent.
 */
public class Code01_MaxHappy {

    public static void main(String[] args) {
        validate();
    }

    public static class Emp {
        int happy;
        List<Emp> subs;

        public Emp(int h) {
            happy = h;
            subs = new ArrayList<>();
        }
    }

    public static int maxHappy(Emp root) {
        if (root == null) {
            return 0;
        }
        Info rootInfo = process(root);
        return Math.max(rootInfo.present, rootInfo.absent);
    }

    public static class Info {
        int present;
        int absent;

        public Info(int p, int a) {
            present = p;
            absent = a;
        }
    }

    public static Info process(Emp emp) {
        if (emp == null) {
            return new Info(0, 0);
        }

        int present = emp.happy;
        int absent = 0;

        for (Emp sub : emp.subs) {
            Info subInfo = process(sub);
            present += subInfo.absent;
            absent += Math.max(subInfo.present, subInfo.absent);
        }

        return new Info(present, absent);
    }


    // validation method copied from zuo
    public static int verboseMaxHappy(Emp boss) {
        if (boss == null) {
            return 0;
        }
        return verboseProcess(boss, false);
    }

    public static int verboseProcess(Emp cur, boolean upPresents) {
        if (upPresents) {
            int ans = 0;
            for (Emp sub : cur.subs) {
                ans += verboseProcess(sub, false);
            }
            return ans;
        } else {
            int p1 = cur.happy;
            int p2 = 0;
            for (Emp sub : cur.subs) {
                p1 += verboseProcess(sub, true);
                p2 += verboseProcess(sub, false);
            }
            return Math.max(p1, p2);
        }
    }

    public static Emp generateBoss(int maxLevel, int maxSubs, int maxHappy) {
        if (Math.random() < 0.02) {
            return null;
        }
        Emp boss = new Emp((int) (Math.random() * (maxHappy + 1)));
        genarateSubs(boss, 1, maxLevel, maxSubs, maxHappy);
        return boss;
    }

    public static void genarateSubs(Emp e, int level, int maxLevel, int maxSubs, int maxHappy) {
        if (level > maxLevel) {
            return;
        }
        int subSize = (int) (Math.random() * (maxSubs + 1));
        for (int i = 0; i < subSize; i++) {
            Emp next = new Emp((int) (Math.random() * (maxHappy + 1)));
            e.subs.add(next);
            genarateSubs(next, level + 1, maxLevel, maxSubs, maxHappy);
        }
    }

    public static void validate() {
        int numTest = 10000;
        int maxLevel = 4;
        int maxSubs = 7;
        int maxHappy = 100;
        for (int i = 0; i < numTest; i++) {
            Emp boss = generateBoss(maxLevel, maxSubs, maxHappy);
            if (maxHappy(boss) != verboseMaxHappy(boss)) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
