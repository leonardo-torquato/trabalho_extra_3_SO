import java.nio.file.Path;

public class Directory {
    private Path path;

    public Directory(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

}
