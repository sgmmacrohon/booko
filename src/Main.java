import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

class Main {

    static final String HEADER = "Booko — 20% off all buko!";

    public static void main(String[] args) {
        System.out.println(HEADER);
        System.out.println("========================");

        BookingService service = new BookingService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("1) View flavors");
            System.out.println("2) Book an order");
            System.out.println("3) Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    viewFlavors(service);
                    break;
                case "2":
                    bookOrder(service, scanner);
                    break;
                case "3":
                    System.out.println("Salamat! See you next order.");
                    return;
                default:
                    System.out.println("Please enter 1, 2, or 3.");
                    break;
            }
        }
    }

    static void viewFlavors(BookingService service) {
        List<Drink> drinks = service.listDrinks();
        for (int i = 0; i < drinks.size(); i++) {
            Drink d = drinks.get(i);
            System.out.printf("%d) %s - %s (%s)%n", i + 1, d.name(), d.description(), d.priceLabel());
        }
    }

    static void bookOrder(BookingService service, Scanner scanner) {
        List<Drink> drinks = service.listDrinks();
        viewFlavors(service);

        System.out.print("Your name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Flavor number: ");
        int flavorNumber;
        try {
            flavorNumber = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("That's not a valid number.");
            return;
        }
        if (flavorNumber < 1 || flavorNumber > drinks.size()) {
            System.out.println("No flavor with that number.");
            return;
        }

        System.out.print("How many: ");
        int quantity;
        try {
            quantity = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("That's not a valid number.");
            return;
        }
        if (quantity < 1 ) {
            System.out.println("Quantity must be a positive number.");
            return;
        }

        System.out.print("Delivery date (yyyy-MM-dd): ");
        String dateInput = scanner.nextLine().trim();
        LocalDate deliveryDate;
        try {
            deliveryDate = LocalDate.parse(dateInput);
        } catch (DateTimeParseException e) {
            System.out.println("Use the format yyyy-MM-dd.");
            return;
        }

        Drink chosen = drinks.get(flavorNumber - 1);
        System.out.println(service.book(name, quantity,chosen, deliveryDate.toString()));
    }
}
