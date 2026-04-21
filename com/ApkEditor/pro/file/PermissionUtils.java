import java.io.File;

public class PermissionUtils {
    public static String getPermissions(File file) {
        StringBuilder permissions = new StringBuilder();
        if (file.canRead()) permissions.append("Read ✓\n");
        else permissions.append("Read ✗\n");
        if (file.canWrite()) permissions.append("Write ✓\n");
        else permissions.append("Write ✗\n");
        if (file.canExecute()) permissions.append("Execute ✓");
        else permissions.append("Execute ✗");
        return permissions.toString();
    }

    public static void setReadable(File file, boolean readable) {
        file.setReadable(readable);
    }

    public static void setWritable(File file, boolean writable) {
        file.setWritable(writable);
    }

    public static void setExecutable(File file, boolean executable) {
        file.setExecutable(executable);
    }
}