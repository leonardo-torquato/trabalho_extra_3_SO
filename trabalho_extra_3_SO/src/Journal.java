import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Journal {
    private static final String JOURNAL_FILE = "journal.log";

    public void logOperation(String operation, String... details) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(JOURNAL_FILE, true))) {
            writer.write(LocalDateTime.now() + " - " + operation + ": ");
            for (String detail : details) {
                writer.write(detail + " ");
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
