package com.rooomy.util;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

public class ZipFilesVisitor extends SimpleFileVisitor<Path> {
    private List<String> result = new LinkedList();

    public ZipFilesVisitor(List<String> result) {
        this.result = result;
    }

    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (file.toString().endsWith(".zip")) {
            this.result.add(file.toString());
        }

        return FileVisitResult.CONTINUE;
    }
}