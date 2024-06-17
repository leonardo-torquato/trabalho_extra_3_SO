import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class FileSystemSimulator {
    private Journal journal;

    public FileSystemSimulator() {
        journal = new Journal();
    }

    public void copyFile(Path source, Path destination) throws IOException {
        journal.logOperation("copyFile", source.toString(), destination.toString());
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    public void deleteFile(Path path) throws IOException {
        journal.logOperation("deleteFile", path.toString());
        Files.delete(path);
    }

    public void renameFile(Path source, Path destination) throws IOException {
        journal.logOperation("renameFile", source.toString(), destination.toString());
        Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    public void createDirectory(Path path) throws IOException {
        journal.logOperation("createDirectory", path.toString());
        Files.createDirectories(path);
    }

    public void deleteDirectory(Path path) throws IOException {
        journal.logOperation("deleteDirectory", path.toString());
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public void renameDirectory(Path source, Path destination) throws IOException {
        journal.logOperation("renameDirectory", source.toString(), destination.toString());
        Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    public List<Path> listFiles(Path directory) throws IOException {
        return Files.list(directory).toList();
    }

    //Teste
    public static void main(String[] args) {
        //Instancia um novo simulador de sistema de arquivos
        FileSystemSimulator fs = new FileSystemSimulator();

        //Define o path do diretorio de teste
        Path testDir = Paths.get("C:\\Users\\leo\\dirTeste");

        try {
            // Cria o diretório de teste
            fs.createDirectory(testDir);

            // Cria um arquivo de exemplo dentro do diretório
            Path sourceFile = Files.createFile(testDir.resolve("sourceFile.txt"));

            // Copia o arquivo dentro do diretório
            fs.copyFile(sourceFile, testDir.resolve("copiedFile.txt"));

            // Lista os arquivos no diretório
            List<Path> files = fs.listFiles(testDir);
            System.out.println("Arquivos em " + testDir + ":");
            for (Path file : files) {
                System.out.println(file.getFileName());
            }

            // Deleta o arquivo
            fs.deleteFile(testDir.resolve("copiedFile.txt"));

            // Renomea o diretório
            Path renamedDir = Paths.get("C:\\DIRETORIOTESTE_RENAMED");
            fs.renameDirectory(testDir, renamedDir);

            // Deleta o diretório renomeado
            fs.deleteDirectory(renamedDir);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
