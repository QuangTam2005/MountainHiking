package View;

import Model.Student;
import Utils.Validation;

public class View {

    public static void displayMenu() {
        System.out.println("MENU:");
        System.out.print("1. New Registration\n"
                + "2. Update Registration Information\n" //
                + "3. Display Registered List\n" //
                + "4. Delete Registration Information\n" //
                + "5. Search Participants by Name\n" //
                + "6. Filter Data by Campus\n" //
                + "7. Statistics of Registration Numbers by Location\n" //
                + "8. Save Data to File\n"
                + "9. Exit the Program\n");
    }

    public static Student inputStudent() {
        String id = Validation.getValid("Enter your ID: ", "Invalid ID", "^(HE|SE|DE|QE|CE)\\d{6}$");

        int campus = Validation.getCampus();

        String name = Validation.getName("Enter your name: ");

        String phoneNum = Validation.getValid("Enter your phone number: ", "Phone number is invalid!",
                "^(086|096|097|098|032|033|034|035|036"
                + "|037|038|039|089|090|093|070|076|077|078|079|088|091"
                + "|094|081|082|083|084|085|092|056|058|099|059|087)\\d{7}$");

        String email = Validation.getValid("Enter your email: ", "Email is invalid", "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

        String mountCode = Validation.getMountainCode("1. Ham Rong Mountain\n2. Doi Bo Mountain\n3. Pha Luong Mountain\n"
                + "4. Hon Vuon Mountain\n5. Da Do Mountain\n"
                + "6. Da Bia Mountain\n7. Chu Hreng Mountain\n"
                + "8. Lang Biang Mountain\n9. Ta Nang Mountain\n"
                + "10. Cam Mountain\n11. Thi Vai Mountain\n"
                + "12. Dinh Mountain\n13. Co Tien Mountain\nEnter campus code: ");

        return new Student(id, campus, name, phoneNum, email, mountCode);
    }

    public static void showMess(String mess) {
        System.out.println(mess);
    }
}
