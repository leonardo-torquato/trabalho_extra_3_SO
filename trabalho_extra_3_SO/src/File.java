import java.nio.file.Path;

public class File {
    private Path path;

    public File(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

}
