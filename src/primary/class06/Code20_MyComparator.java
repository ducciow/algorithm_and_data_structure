package primary.class06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Author: duccio
 * @Date: 23, 03, 2022
 * @Description: Implement own version of comparator
 * @Note:
 */
public class Code20_MyComparator {

    public static class Student {
        String name;
        int id;
        int age;

        public Student(String name, int id, int age) {
            this.name = name;
            this.id = id;
            this.age = age;
        }
    }

    public static class MyComparator implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            if (o1.id < o2.id) {
                return -1;
            } else if (o1.id > o2.id) {
                return 1;
            } else {
                return 0;
            }
        }
    }


    public static void main(String[] args) {
        Student s1 = new Student("a", 5, 27);
        Student s2 = new Student("b", 1, 17);
        Student s3 = new Student("c", 4, 29);
        Student s4 = new Student("d", 3, 9);
        Student s5 = new Student("e", 2, 34);

        // array
        Student[] students = {s1, s2, s3, s4, s5};
        for (Student student : students) {
            System.out.println(student.name + ", " + student.id + "," + student.age);
        }

        System.out.println("====");
        Arrays.sort(students, new MyComparator());
        for (Student student : students) {
            System.out.println(student.name + ", " + student.id + "," + student.age);
        }

        // ArrayList
        System.out.println("=======");
        ArrayList<Student> arrList = new ArrayList<>();
        arrList.add(s1);
        arrList.add(s2);
        arrList.add(s3);
        arrList.add(s4);
        arrList.add(s5);

        for (Student s : arrList) {
            System.out.println(s.name + ", " + s.id + ", " + s.age);
        }

        System.out.println("====");
        arrList.sort(new MyComparator());
        for (Student s : arrList) {
            System.out.println(s.name + ", " + s.id + ", " + s.age);
        }

        // PriorityQueue
        System.out.println("=========");
        PriorityQueue<Student> heap = new PriorityQueue<>(new MyComparator());
        heap.add(s1);
        heap.add(s2);
        heap.add(s3);
        heap.add(s4);
        heap.add(s5);
        while (!heap.isEmpty()) {
            Student s = heap.poll();
            System.out.println(s.name + ", " + s.id + ", " + s.age);
        }
    }
}
