import java.io.*;
import java.util.zip.*;

public class ZipUtils {

    // Create a ZIP archive from a folder
    public static void createZipFromFolder(String sourceFolderPath, String zipFilePath) throws IOException {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            File sourceFolder = new File(sourceFolderPath);
            addFolderToZip(sourceFolder, sourceFolder.getName(), zipOutputStream);
        }
    }

    // Add folder to ZIP
    private static void addFolderToZip(File folder, String baseFolderName, ZipOutputStream zipOutputStream) throws IOException {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                addFolderToZip(file, baseFolderName + "/" + file.getName(), zipOutputStream);
            } else {
                addFileToZip(file, baseFolderName, zipOutputStream);
            }
        }
    }

    // Add a single file to ZIP
    private static void addFileToZip(File file, String baseFolderName, ZipOutputStream zipOutputStream) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            ZipEntry zipEntry = new ZipEntry(baseFolderName + "/" + file.getName());
            zipOutputStream.putNextEntry(zipEntry);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) >= 0) {
                zipOutputStream.write(buffer, 0, length);
            }
            zipOutputStream.closeEntry();
        }
    }

    // Extract files from a ZIP archive
    public static void extractZip(String zipFilePath, String destFolderPath) throws IOException {
        File destFolder = new File(destFolderPath);
        if (!destFolder.exists()) {
            destFolder.mkdir();
        }
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                File newFile = new File(destFolder, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    // Make sure parent directory exists
                    new File(newFile.getParent()).mkdirs();
                    try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(newFile))) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipInputStream.read(buffer)) >= 0) {
                            bufferedOutputStream.write(buffer, 0, length);
                        }
                    }
                }
                zipInputStream.closeEntry();
            }
        }
    }
}