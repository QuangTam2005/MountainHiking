package Collections;

import Model.Mountain;
import Model.Student;
import Utils.Validation;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StudentList extends ArrayList<Student> {

    private int countStep = 0;

    public void addStudent(Student student, String fileName) {
        if (countStep == 0) {
            loadFromFile(fileName);
        }
        countStep++;
        for (Student stuInList : this) {
            if (stuInList.getId().equals(student.getId())) {
                System.out.println("ID already exists");
                return;
            }
        }
        this.add(student);
    }

    public void updateStudent(Student student, String fileName) {
        if (countStep == 0) {
            loadFromFile(fileName);
        }
        countStep++;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getId().equals(student.getId())) {
                this.set(i, student);
                saveToFile(fileName);
                return;
            }
        }
        System.err.println("Your ID is not found");
    }

    public void deleteStudent(String id, String fileName) {
        if (countStep == 0) {
            loadFromFile(fileName);
        }
        countStep++;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getId().equals(id)) {
                this.remove(i);
                saveToFile(fileName);
                return;
            }
        }
        System.err.println("Your ID is not found");
    }

    public void showStudentList(String fileName) {
        if (countStep == 0) {
            loadFromFile(fileName);
        }
        countStep++;
        if (this.isEmpty()) {
            System.out.println("Registration is not found");
        } else {
            for (Student student : this) {
                System.out.println(student.toString());
            }
        }
    }

    public void sortByName(String fileName) {
        if (countStep == 0) {
            loadFromFile(fileName);
        }
        countStep++;
        StudentList tmpList = new StudentList();
        for (Student student : this) {
            tmpList.add(student);
        }
        Collections.sort(tmpList, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                int nameCompare = s1.getName().compareTo(s2.getName());
                if (nameCompare != 0) {
                    return nameCompare;
                }
                return s1.getId().compareTo(s2.getId());
            }
        });

        for (Student student : tmpList) {
            System.out.println(student.toString());
        }
    }

    public void searchByName(String fileName) {
        if (countStep == 0) {
            loadFromFile(fileName);
        }
        countStep++;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the name wanting to search: ");
        String nameSearch = sc.nextLine();
        for (Student student : this) {
            if (student.getName().toLowerCase().contains(nameSearch.toLowerCase())) {
                System.out.println(student.toString());
            }
        }
    }

    public void filterByCampus(String fileName) {
        if (countStep == 0) {
            loadFromFile(fileName);
        }
        countStep++;
        Scanner sc = new Scanner(System.in);
        System.out.println(". Ha Noi | 2. Ho Chi Minh | 3. Da Nang: | 4. Quy Nhon:"
                + " | 5. Can Tho\nEnter the campus code to filter: ");
        int campCode = Integer.parseInt(sc.nextLine());

        for (Student student : this) {
            if (campCode == student.getCampusCode()) {
                System.out.println(student.toString());
            }
        }
    }

    public void countByLocation(String fileName) {
        List<Object> objList = readFromFile(fileName);
        List<Mountain> mountList = new ArrayList<>();
        Map<String, Integer> counting = new HashMap<>();

        for (Object obj : objList) {
            mountList.add((Mountain) obj);
        }

        for (Mountain mountain : mountList) {
            if (counting.containsKey(mountain.getMountain())) {
                counting.put(mountain.getMountain(), counting.get(mountain.getMountain()) + 1);
            } else {
                counting.put(mountain.getMountain(), 1);
            }
        }

        for (Map.Entry<String, Integer> entry : counting.entrySet()) {
            System.out.println(entry.getKey() + "has: " + entry.getValue() + " registrations.");
        }
    }

    public void saveToFile(String fileName) {
        int ans = Validation.getIntInRange("Do you want to save it?(1/0): ", 0, 1);
        if (ans == 1) {
            try {
                FileOutputStream fileOS = new FileOutputStream(fileName);
                ObjectOutputStream objectOS = new ObjectOutputStream(fileOS);
                objectOS.writeObject(this);
                objectOS.close();
                fileOS.close();
                System.out.println("Process is successfull");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Choose other option!");
        }
    }

    public void appendToFile(String fileName) {
        int ans = Validation.getIntInRange("Do you want to save it?(1/0): ", 0, 1);
        if (ans == 1) {
            try (FileOutputStream fileOS = new FileOutputStream(fileName, true);
                    ObjectOutputStream objectOS = new ObjectOutputStream(fileOS) {
                @Override
                protected void writeStreamHeader() throws IOException {
                    if (new File(fileName).length() == 0) {
                        super.writeStreamHeader(); // Chỉ ghi header nếu file rỗng
                    } else {
                        reset(); // Nếu file đã có dữ liệu, reset để tránh lỗi
                    }
                }
            }) {

                objectOS.writeObject(this);
                System.out.println("Process is successful");

            } catch (IOException e) {
                System.err.println("Process errors");
            }
        } else {
            System.out.println("Choose other option!");
        }
    }

    public void loadFromFile(String fileName) {
        try {
            FileInputStream fileIS = new FileInputStream(fileName);
            ObjectInputStream objectIS = new ObjectInputStream(fileIS);
            StudentList studentList = (StudentList) objectIS.readObject();
            this.clear();
            this.addAll(studentList);
            objectIS.close();
            fileIS.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Process errors");
        }
    }

    public List<Object> readFromFile(String fileName) {
        List<Object> objectList = new ArrayList<>();

        try (FileInputStream fileIS = new FileInputStream(fileName);
                ObjectInputStream objectIS = new ObjectInputStream(fileIS)) {

            while (true) {
                try {
                    Object obj = objectIS.readObject();
                    objectList.add(obj);
                } catch (EOFException e) {
                    break; // Đọc hết file, thoát vòng lặp
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return objectList;
    }
}
