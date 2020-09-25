package com.rooomy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;

public class FileHelper {

    public static void main(String[] args) throws IOException {
        String sourcePath = "D:\\work\\GLTFConverter\\resource\\20200925\\≤‚ ‘\\PTRC4NKHFM9NETJ6.zip";

        Path from = Paths.get(sourcePath);
        Path to = from.getParent().resolve("glb");

        System.out.println("hello");
    }

    public static void moveFile(Path from, Path to) throws IOException {
        Files.move(from, to, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Move glb from:" + from + ", to:" + to);

    }

    public static void copyDir(String sourcePath, String destPath) throws IOException {
        File soureFile = new File(sourcePath);
        String[] filePath = soureFile.list();
        File destFile = new File(destPath);
        if (!destFile.exists()) {
            destFile.mkdir();
        }

        for (int i = 0; i < filePath.length; i++) {
            String from = sourcePath + soureFile.separator + filePath[i];
            String to = destPath + soureFile.separator + filePath[i];
            File sourceFile = new File(from);
            if (sourceFile.isDirectory()) {
                copyDir(from, to);
            } else if (sourceFile.isFile()) {
                copyFile(sourceFile, to);
            }
        }
    }

    public static void copyFile(File oldFile, String newPath) throws IOException {
        File file = new File(newPath);
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);

        byte[] buffer = new byte[2097152];
        int readByte = 0;
        while ((readByte = in.read(buffer)) != -1) {
            out.write(buffer, 0, readByte);
        }
        out.flush();
        in.close();
        out.close();
    }

    public static List<String> getDestZipFiles(String path) throws IOException {
        Path startingDir = Paths.get(path);
        List<String> result = new LinkedList();
        Files.walkFileTree(startingDir, new ZipFilesVisitor(result));
        return result;
    }

    public static boolean deleteDir(String dir) {
        System.out.println("Remove folder:" + dir);
        File folder = new File(dir);
        return deleteDir(folder);
    }

    public static boolean deleteDir(File dirFile) {
        if (!dirFile.exists()) {
            return false;
        }
        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {
            for (File file : dirFile.listFiles()) {
                deleteDir(file);
            }
        }
        return dirFile.delete();
    }
}
