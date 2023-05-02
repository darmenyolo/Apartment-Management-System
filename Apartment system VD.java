import java.util.Scanner;

 class GuardFunction {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // Capture visitor info
    System.out.print("Enter visitor name: ");
    String visitorName = scanner.nextLine();

    System.out.print("Enter visitor ID: ");
    String visitorID = scanner.nextLine();

    // Capture time in/time out
    System.out.print("Enter time in (format: HH:mm:ss): ");
    String timeIn = scanner.nextLine();

    System.out.print("Enter time out (format: HH:mm:ss): ");
    String timeOut = scanner.nextLine();

    // Display captured information
    System.out.println("Visitor info:");
    System.out.println("Name: " + visitorName);
    System.out.println("ID: " + visitorID);
    System.out.println("Time in: " + timeIn);
    System.out.println("Time out: " + timeOut);
  }
}