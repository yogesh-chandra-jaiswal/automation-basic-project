package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.commons.io.FileUtils;

/**
 * FileUtil - Utility class for file operations
 * Provides methods for file handling, reading, writing and deletion
 */
public class FileUtil {

    /**
     * Create directory if not exists
     */
    public static void createDirectory(String path) {
        try {
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
                LoggerUtil.info("Directory created: " + path);
            }
        } catch (Exception e) {
            LoggerUtil.error("Failed to create directory: " + path, e);
        }
    }

    /**
     * Delete file
     */
    public static void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
                LoggerUtil.info("File deleted: " + filePath);
            }
        } catch (Exception e) {
            LoggerUtil.error("Failed to delete file: " + filePath, e);
        }
    }

    /**
     * Delete directory and its contents
     */
    public static void deleteDirectory(String dirPath) {
        try {
            File directory = new File(dirPath);
            if (directory.exists()) {
                FileUtils.deleteDirectory(directory);
                LoggerUtil.info("Directory deleted: " + dirPath);
            }
        } catch (Exception e) {
            LoggerUtil.error("Failed to delete directory: " + dirPath, e);
        }
    }

    /**
     * Copy file
     */
    public static void copyFile(String source, String destination) {
        try {
            FileUtils.copyFile(new File(source), new File(destination));
            LoggerUtil.info("File copied from " + source + " to " + destination);
        } catch (Exception e) {
            LoggerUtil.error("Failed to copy file", e);
        }
    }

    /**
     * Check if file exists
     */
    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * Get file count in directory
     */
    public static int getFileCountInDirectory(String dirPath) {
        try {
            File directory = new File(dirPath);
            File[] files = directory.listFiles();
            return files != null ? files.length : 0;
        } catch (Exception e) {
            LoggerUtil.error("Failed to get file count", e);
            return 0;
        }
    }

    /**
     * Read file as string
     */
    public static String readFileAsString(String filePath) {
        try {
            return FileUtils.readFileToString(new File(filePath), "UTF-8");
        } catch (Exception e) {
            LoggerUtil.error("Failed to read file: " + filePath, e);
            return "";
        }
    }

    /**
     * Write string to file
     */
    public static void writeStringToFile(String filePath, String content) {
        try {
            FileUtils.writeStringToFile(new File(filePath), content, "UTF-8");
            LoggerUtil.info("Content written to file: " + filePath);
        } catch (Exception e) {
            LoggerUtil.error("Failed to write to file: " + filePath, e);
        }
    }
}

