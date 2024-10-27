package steps;

import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.File;
import java.nio.file.*;
import java.util.List;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;


public class Utils {

    private static final String BASE_DIR = "evidences";

    public static List<String> paths() {
        return Arrays.asList(
            "reports",
            "screenshots"
        );
    }

    public static void validateAndCreateDirectories() throws IOException {
        boolean printMessage = true;  // Flag to print first time message
        List<String> directories = paths();

        for( String item : directories ) {
            String directory = Paths.get( BASE_DIR, item ).toString();
            if( !Files.exists( Paths.get( directory ) ) ) {
                if( printMessage ) {  // Print the first time message
                    System.out.println("--- The evidences folder will be created ---");
                    printMessage = false;  // Flag update after printing message
                }
                createDirectory(directory);
                createTodaysDirectory(directory);
            } 
            else {
                createTodaysDirectory(directory);
            }
        }
    }

    public static void createDirectory(String directory) throws IOException {
        System.out.println("Creating directory: " + directory);
        Files.createDirectories(Paths.get(directory));
    }

    public static void createTodaysDirectory(String directory) throws IOException {
        String folderDate = getDate();
        String todaysFolder = Paths.get(directory, folderDate).toString();
        Files.createDirectories(Paths.get(todaysFolder));
        executionFolder(todaysFolder);
    }

    public static void executionFolder(String path) throws IOException {
        File folder = new File(path);
        String[] subfolders = folder.list((current, name) -> new File(current, name).isDirectory());
        int subfolderCount = (subfolders != null ? subfolders.length : 0) + 1;
        String executionFolder = Paths.get(path, String.valueOf(subfolderCount)).toString();
        Files.createDirectories(Paths.get(executionFolder));
    }

    public static String getDate() {
        return new SimpleDateFormat("ddMMyyyy").format(new Date());
    }
    
    public static String getLastReportFolderName(String directory) throws IOException {
        String folderDate = getDate();
        String path = Paths.get(BASE_DIR, directory, folderDate).toString();

        try {
            // Get list of elements in folder
            File folder = new File(path);
            String[] elements = folder.list();

            if (elements == null) {
                throw new IOException("No elements found in the directory: " + path);
            }
            

            // Filters only directories name
            List<String> directories = Arrays.stream(elements)
                    .filter(element -> new File(path, element).isDirectory())
                    .collect(Collectors.toList());

            // Get the last folder name created
            int lastFolderNumber = directories.stream()
                    .mapToInt(Integer::parseInt)
                    .max()
                    .orElseThrow(() -> new IOException("No directories found in the directory: " + path));

            String lastFolder = String.valueOf(lastFolderNumber);
            String relativePath = Paths.get(path, lastFolder).toString();

            return relativePath;
        } catch (IOException e) {
            System.err.println("There was an error: " + e.getMessage());
            throw e;
        }
    }

    public static void moveAndDeleteAllureReports(String targetDir) throws IOException  {
        String sourceDir = "allure-results";
        Path sourcePath = Paths.get(sourceDir);
        Path targetPath = Paths.get(targetDir);
        
        if (!Files.exists(targetPath)) {
            Files.createDirectories(targetPath);
        }

        Files.walk(sourcePath)
            .forEach(source -> {
                Path destination = targetPath.resolve(sourcePath.relativize(source));
                try {
                    // Solo mover los archivos, no las carpetas vacías
                    if (Files.isDirectory(source)) {
                        Files.createDirectories(destination); // Crea subcarpetas en el destino si existen en el origen
                    } else {
                        Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        // Eliminar el directorio "allure-reports" después de mover los archivos
        deleteDirectoryRecursively(new File(sourceDir));
    }

    public static boolean deleteDirectoryRecursively(File directory) {
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectoryRecursively(file);
            }
        }
        return directory.delete();
    }
}
