import java.util.ArrayList;
import java.util.Scanner;

 class ApartmentManagerFunction {
    private static ArrayList<Resident> residents = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. Generate visitor report");
            System.out.println("2. Create resident account");
            System.out.println("3. Search resident info");
            System.out.println("4. Generate payment record");
            System.out.println("5. Send reminder");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    generateVisitorReport();
                    break;
                case 2:
                    createResidentAccount(scanner);
                    break;
                case 3:
                    searchResidentInfo(scanner);
                    break;
                case 4:
                    generatePaymentRecord(scanner);
                    break;
                case 5:
                    sendReminder(scanner);
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void generateVisitorReport() {
        // Code to generate visitor report
        // ...
    }

    private static void createResidentAccount(Scanner scanner) {
        System.out.println("Enter resident name:");
        String name = scanner.nextLine();

        System.out.println("Enter resident email:");
        String email = scanner.nextLine();

        System.out.println("Enter resident phone number:");
        String phone = scanner.nextLine();

        Resident resident = new Resident(name, email, phone);
        residents.add(resident);

        System.out.println("Resident account created successfully.");
    }

    private static void searchResidentInfo(Scanner scanner) {
        System.out.println("Enter resident email:");
        String email = scanner.nextLine();

        for (Resident resident : residents) {
            if (resident.getEmail().equals(email)) {
                System.out.println("Resident info:");
                System.out.println("Name: " + resident.getName());
                System.out.println("Email: " + resident.getEmail());
                System.out.println("Phone: " + resident.getPhone());
                return;
            }
        }

        System.out.println("Resident not found.");
    }

    private static void generatePaymentRecord(Scanner scanner) {
        System.out.println("Enter resident email:");
        String email = scanner.nextLine();

        for (Resident resident : residents) {
            if (resident.getEmail().equals(email)) {
                // Code to generate payment record
                // ...
                System.out.println("Payment record generated successfully.");
                return;
            }
        }

        System.out.println("Resident not found.");
    }

    private static void sendReminder(Scanner scanner) {
        System.out.println("Enter resident email:");
        String email = scanner.nextLine();

        for (Resident resident : residents) {
            if (resident.getEmail().equals(email)) {
                // Code to send reminder
                // ...
                System.out.println("Reminder sent successfully.");
                return;
            }
        }

        System.out.println("Resident not found.");
    }
}

class Resident {
    private String name;
    private String email;
    private String phone;

    public Resident(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;

    }

    public String getPhone() {
        return phone;

    }
}