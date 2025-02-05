package Utils;

import java.util.Scanner;

public class Validation {

    public static int getIntInRange(String message, int min, int max) {
        Scanner sc = new Scanner(System.in);
        int intInput;
        do {
            try {
                System.out.print(message);
                intInput = Integer.parseInt(sc.nextLine());
                if (intInput < min || intInput > max) {
                    System.err.println("Input is invalid. It must be between " + min + " VNĐ and " + max + " VNĐ!");
                    continue;
                }
                return intInput;
            } catch (Exception e) {
                System.err.println("Input is invalid. It is not an integer number!");
            }
        } while (true);
    }
    
    public static String getValid(String inputMessage, String errMessage, String pattern){
        Scanner sc = new Scanner(System.in);
        String input = "";
        do{
            System.out.print(inputMessage);
            input = sc.nextLine();
            boolean check = input.matches(pattern);
            if(!check){
                System.err.println(errMessage);
                continue;
            }
            return input;
        }while(true);
    }    

    public static int getCampus(String message) {
        Scanner sc = new Scanner(System.in);
        int inputCampusCode;
        do {
            try {
                System.out.print("1. Ha Noi | 2. Ho Chi Minh | 3. Da Nang: | 4. Quy Nhon:"
                        + " | 5. Can Tho\nEnter campus code(1 - 5): ");
                inputCampusCode = Integer.parseInt(sc.nextLine());
                if (inputCampusCode > 5 || inputCampusCode < 1) {
                    System.err.println("Invalid campus code!");

                    continue;
                }
                return inputCampusCode;
            } catch (Exception e) {
                System.err.println("Invalid campus code!");
            }
        } while (true);
    }

    public static String getName(String message) {
        Scanner sc = new Scanner(System.in);
        String name = null;
        do {
            System.out.print(message);
            name = sc.nextLine();
            if (name.length() < 2 || name.length() > 20 || name == null) {
                System.err.println("Name is invalid!");
                continue;
            }
            return name;

        } while (true);
    }

    public static String getMountainCode(String message) {
        Scanner sc = new Scanner(System.in);
        int inputMountainCode;
        do {
            try {
                System.out.print(message);
                inputMountainCode = Integer.parseInt(sc.nextLine());
                if (inputMountainCode > 5 || inputMountainCode < 1) {
                    System.err.println("Invalid mountain code!");
                    continue;
                }
                return String.valueOf(inputMountainCode);
            } catch (Exception e) {
                System.err.println("Invalid mountain code!");
            }
        } while (true);
    }
}
