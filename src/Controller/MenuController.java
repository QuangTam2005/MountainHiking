package Controller;

import Collections.StudentList;
import Utils.Validation;
import View.View;
import java.util.Scanner;

public class MenuController {

    StudentList students = new StudentList();
    String filePath = "Students.dat";
    
    public void start() {
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            View.displayMenu();
            int choice = Validation.getIntInRange("Enter your option: ", 1, 9);
            switch (choice) {
                case 1:
                    students.addStudent(View.inputStudent(), filePath);
                    break;
                case 2:
                    students.updateStudent(Validation.getValid("Enter ID wanting to delete: ", "ID is not found", "^(HE|SE|QE|DE|CE)\\d{6}$"), filePath);
                    break;
                case 3:
                    students.showStudentList(filePath);
                    break;
                case 4:
                    students.deleteStudent(Validation.getValid("Enter ID wanting to delete: ", "ID is not found", "^(HE|SE|QE|DE|CE)\\d{6}$"), filePath);
                    break;
                case 5:
                    students.searchByName(filePath);
                    break;
                case 6:
                    students.filterByCampus(filePath);
                    break;
                case 7:
                    students.countByLocation(filePath);
                    break;
                case 8:
                    students.saveToFile(filePath);
                    break;
                case 9:
                    running = false;
                    break;
            }
        }
    }
}
