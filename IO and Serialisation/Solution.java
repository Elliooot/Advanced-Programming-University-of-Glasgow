package lab8;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class Solution {

    private static final int MAX_BYTES = 50;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Student[] originalStudents = createStudents(10000);

        // Part 1
        writeToCsv(originalStudents, "students.csv");
        Student[] loadedStudents = readFromCsv("students.csv");
        if (areStudentsEqual(originalStudents, loadedStudents)) {
            System.out.println("students loaded correctly from csv");
        } else {
            System.out.println("loaded students are different =(");
        }

        // Part 2
        serialize(originalStudents, "students.ser");
        loadedStudents = deserialize("students.ser");
        if (areStudentsEqual(originalStudents, loadedStudents)) {
            System.out.println("students loaded correctly by deserialisation");
        } else {
            System.out.println("loaded students are different =(");
        }

        // Part 3
        writeToPaddedBinary(originalStudents, "students.padded");
        readOneFromPaddedBinary("students.padded");

        // Part 4
        writeToPackedBinary(originalStudents, "students.packed");
        loadedStudents = readFromPackedBinary("students.packed");
        if (areStudentsEqual(originalStudents, loadedStudents)) {
            System.out.println("students loaded correctly from packed binary");
        } else {
            System.out.println("loaded students are different =(");
        }
    }

    private static Student[] readFromPackedBinary(String filename) throws  IOException {
        int numBytesPerStudent = 4 + 2 + 4 + 4 + 4;
        try (RandomAccessFile file = new RandomAccessFile(filename, "r")) {

            // Prepare a suitable size array to hold the students
            int numStudents = file.readInt();
            Student[] students = new Student[numStudents];
            for (int index = 0; index < numStudents; ++index) {
                // Seek the file to the start position of the next student (extra 4 to skip int numStudents)
                file.seek(4 + index * numBytesPerStudent);

                // Read all the 'simple' fields and create student object
                students[index] = new Student(
                    file.readInt(),
                    "",    // we'll fix this below
                    file.readShort(),
                    file.readFloat()
                );

                // Read the fields describing where the name is stored
                int nameOffset = file.readInt();
                int nameLength = file.readInt();

                // Seek to the start of the name, read it, and store in the student object
                // We jump past all the fixed-size student records, then further to the required name
                file.seek(4 + numStudents * numBytesPerStudent + nameOffset);
                byte[] nameBytes = new byte[nameLength];
                file.read(nameBytes);
                students[index].name = new String(nameBytes, Charset.forName("UTF-8"));
            }
            return students;
        }
    }

    private static void writeToPackedBinary(Student[] students, String filename) throws IOException {
        int nextNameStartByte = 0;
        byte[][] allNameBytes = new byte[students.length][];
        for (int index = 0; index < students.length; ++index) {
            Student student = students[index];
            byte[] nameBytes = student.name.getBytes(Charset.forName("UTF-8"));
            student.nameLength = nameBytes.length;
            student.nameOffset = nextNameStartByte;
            nextNameStartByte += nameBytes.length;
            allNameBytes[index] = nameBytes;
        }

        try (
                OutputStream stream = new FileOutputStream(filename);
                DataOutputStream dos = new DataOutputStream(stream)
        ) {
            // Write the total number of students -- this will make reading easier
            dos.writeInt(students.length);

            // Write each student as a fixed-size record
            for (int index = 0; index < students.length; ++index) {
                Student student = students[index];
                dos.writeInt(student.rollNumber);
                dos.writeShort(student.age);
                dos.writeFloat(student.grade);
                dos.writeInt(student.nameOffset);
                dos.writeInt(student.nameLength);
            }

            // Write all the name bytes packed together at the end
            for (int index = 0; index < allNameBytes.length; ++index) {
                dos.write(allNameBytes[index]);
            }
        }
    }

    private static void readOneFromPaddedBinary(String filename) throws IOException {
        System.out.println("enter the index of a student:");
        int index = new Scanner(System.in).nextInt();
        int numBytesPerStudent = 4 + MAX_BYTES + 2 + 4;
        try (RandomAccessFile file = new RandomAccessFile(filename, "r")) {
            file.seek(index * numBytesPerStudent);
            System.out.println("rollNumber = " + file.readInt());
            byte[] nameBytes = new byte[MAX_BYTES];
            file.read(nameBytes);
            System.out.println("name = " + fromPaddedBytes(nameBytes));
            System.out.println("age = " + file.readShort());
            System.out.println("grade = " + file.readFloat());
        }
    }

    private static void writeToPaddedBinary(Student[] students, String filename) throws IOException {
        try (
            OutputStream stream = new FileOutputStream(filename);
            DataOutputStream dos = new DataOutputStream(stream)
        ) {
            for (int index = 0; index < students.length; ++index) {
                Student student = students[index];
                dos.writeInt(student.rollNumber);
                dos.write(toPaddedBytes(student.name));
                dos.writeShort(student.age);
                dos.writeFloat(student.grade);
            }
        }
    }

    private static byte[] toPaddedBytes(String string) {
        byte[] name = string.getBytes(Charset.forName("UTF-8"));
        byte[] namePadded = new byte[MAX_BYTES];
        System.arraycopy(name, 0, namePadded, 0, name.length);
        return namePadded;
    }

    private static String fromPaddedBytes(byte[] bytes) {
        int length = 0;
        for (; length < bytes.length; ++length) {
            if (bytes[length] == 0)
                break;  // length of string is index of first padding zero
        }
        return new String(bytes, 0, length, Charset.forName("UTF-8"));
    }

    private static Student[] deserialize(String filename) throws IOException, ClassNotFoundException {
        try (
                InputStream stream = new FileInputStream(filename);
                ObjectInputStream ois = new ObjectInputStream(stream)
        ) {
            return (Student[]) ois.readObject();
        }
    }

    private static void serialize(Student[] students, String filename) throws IOException {
        try (
            OutputStream stream = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(stream)
        ) {
            oos.writeObject(students);
        }
    }

    private static boolean areStudentsEqual(Student[] originals, Student[] loadeds) {
        if (originals.length != loadeds.length)
            return false;
        for (int index = 0; index < originals.length; ++index) {
            Student original = originals[index];
            Student loaded = loadeds[index];
            if (original.rollNumber != loaded.rollNumber ||
                !original.name.equals(loaded.name) ||
                original.age != loaded.age ||
                original.grade != loaded.grade
            ) {
                return false;
            }
        }
        return true;
    }

    private static Student[] readFromCsv(String filename) throws IOException {
        ArrayList<Student> students = new ArrayList<>();
        try (
            FileReader reader = new FileReader(filename) ;
            Scanner scanner = new Scanner(reader)
        ) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] bits = line.split(",");
                assert bits.length == 4;
                students.add(new Student(
                    Integer.parseInt(bits[0]),
                    bits[1],
                    Short.parseShort(bits[2]),
                    Float.parseFloat(bits[3])
                ));
            }
            return students.toArray(new Student[0]);
        }
    }

    private static void writeToCsv(Student[] students, String filename) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (int index = 0; index < students.length; ++index) {
                String line = students[index].rollNumber + "," + students[index].name + "," + students[index].age + "," + students[index].grade;
                writer.println(line);
            }
        }
    }

    private static Student[] createStudents(int numStudents) {
        Random random = new Random();
        Student[] students = new Student[numStudents];
        for (int index = 0; index < students.length; ++index) {
            String nameChar = String.valueOf((char)(random.nextInt(26) + 'a'));  // choose a random character, by first choosing a random integer 0-26
            String name = nameChar.repeat(random.nextInt(6) + 4);  // repeat that character 4-10 times to create the name
            students[index] = new Student(
                index,
                name,
                (short)(random.nextInt(90) + 10),
                random.nextFloat() * 10.0f
            );
        }
        return students;
    }
}
