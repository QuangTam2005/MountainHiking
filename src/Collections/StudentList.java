package Collections;

import Model.Mountain;
import Model.Student;
import Utils.Validation;
import View.View;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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
        if (isExist(student.getId(), "ID already exists")) {
            return;
        }

        if (isExist(student.getEmail(), "Email already exists")) {
            return;
        }

        if (isExist(student.getPhone(), "Phone number already exists")) {
            return;
        }

        this.add(student);

        saveToFile("Student.dat");

        loadFromFile("Student.dat");

        countStep++;
    }

    public void updateStudent(String id, String fileName) {
        if (countStep == 0) {
            loadFromFile(fileName);
        }
        countStep++;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getId().equals(id)) {
                Student student = View.inputStudent();
                Student tmp = searchByID(id);
                int size = this.size();
                deleteStudent(id, "Student.dat");
                addStudent(student, "Student.dat");
                if (this.size() != size) {
                    addStudent(tmp, "Student.dat");
                }
                return;
            }
        }
        System.err.println("Your ID is not found");
    }

    public Student searchByID(String id) {
        for (Student student : this) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public boolean isExist(String itemChecking, String mess) {
        for (Student stuInList : this) {
            if (stuInList.getId().equals(itemChecking)) {
                System.out.println(mess);
                return true;
            }
        }
        return false;
    }

    public void deleteStudent(String id, String fileName) {
        if (countStep == 0) {
            loadFromFile(fileName);
        }
        countStep++;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getId().equals(id)) {
                System.out.println(this.get(i).toString());
                int ans = Validation.getIntInRange("Are you sure you want to delete this registration?"
                        + " (1/0): ", 0, 1);
                if (ans == 1) {
                    this.remove(i);
                    System.out.println("The registration has been successfully deleted.");
                    return;
                } else {
                    System.out.println("Process is cancelled.");
                    return;
                }
            }
        }
        System.err.println("This student has not registered yet.");
    }

    public void showStudentList(String fileName) {
        if (countStep == 0) {
            loadFromFile(fileName);
        }
        countStep++;
        if (this.isEmpty()) {
            System.out.println("No students have registered yet.");
        } else {
            System.out.println("----------------------------------------------------------------------");
            System.out.println("Student ID  | Name            | Phone      | Peak Code | Fee");
            System.out.println("----------------------------------------------------------------------");
            for (Student student : this) {
                System.out.println(student.toString());
            }
            System.out.println("----------------------------------------------------------------------");
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
        boolean found = false;
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Student ID  | Name            | Phone      | Peak Code | Fee");
        System.out.println("----------------------------------------------------------------------");
        for (Student student : this) {
            if (student.getName().toLowerCase().contains(nameSearch.toLowerCase())) {
                System.out.println(student.toString());
                found = true;
            }
        }
        if (!found) System.out.println("No one matches the search criteria!");
        System.out.println("----------------------------------------------------------------------");        
    }

    public void filterByCampus(String fileName) {
        if (countStep == 0) {
            loadFromFile(fileName);
        }
        countStep++;
        Scanner sc = new Scanner(System.in);
        int campCode = Validation.getCampus();
        boolean found = false;
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Student ID  | Name            | Phone      | Peak Code | Fee");
        System.out.println("----------------------------------------------------------------------");
        for (Student student : this) {
            if (campCode == student.getCampusCode()) {
                found = true;
                System.out.println(student.toString());
            }
        }
        if(!found) System.out.println("No students have registered under this campus.");
        System.out.println("----------------------------------------------------------------------");
    }

    public void countByLocation(String fileName) {
        if (countStep == 0) {
            loadFromFile(fileName);
        }
        countStep++;

        List<Mountain> mountList = readFromFile("MountainList.txt", true);

        Map<String, Integer> counting = new HashMap<>();

        for (Student student : this) {
            if (counting.containsValue(student.getCampusCode())) {
                counting.put(student.getMountainCode(), counting.get(student.getMountainCode()) + 1);
            } else {
                counting.put(student.getMountainCode(), 1);
            }
        }
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Peak Name          | Number of Participants | Total Cost");
        System.out.println("----------------------------------------------------------------------");

        for (Mountain mountain : mountList) {
            if (counting.containsKey(mountain.getMountainCode())) {
                double totalTuition = 0;
                for (Student student : this) {
                    if (student.getMountainCode().equals(mountain.getMountainCode())) {
                        totalTuition += student.getTuitionFee();
                    }
                }
                System.out.printf("MT0%-16s|  %-22s| %-9.0f\n", mountain.getMountainCode(), counting.get(mountain.getMountainCode()), totalTuition);
            }
        }

        System.out.println("----------------------------------------------------------------------");
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
                System.out.println("Registration data has been successfully saved to Student.dat.");
            } catch (IOException e) {
                e.printStackTrace();
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

    public List<Mountain> readFromFile(String fileName, boolean skipFirst) {
        List<Mountain> mountainList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean firstSkipped = false;

            while ((line = br.readLine()) != null) {
                // Nếu skipFirst = true, bỏ qua dòng đầu tiên
                if (skipFirst && !firstSkipped) {
                    firstSkipped = true;
                    continue;
                }

                String[] values = line.split(","); // Tách dữ liệu theo dấu phẩy
                if (values.length < 3) {
                    continue;  // Kiểm tra số lượng cột trong mỗi dòng
                }
                // Giả sử file có định dạng: Name, Location, Height
                String code = values[0].trim();
                String name = values[1].trim();
                String province = values[2].trim();
                String desc = values[3].trim();

                // Tạo đối tượng Mountain và thêm vào danh sách
                Mountain mountain = new Mountain(code, name, province, desc);
                mountainList.add(mountain);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return mountainList;
    }

}
