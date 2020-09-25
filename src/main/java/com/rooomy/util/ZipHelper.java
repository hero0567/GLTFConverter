package com.rooomy.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipHelper {

    private static LogHelper log = LogHelper.getLog();

    public static void main(String[] args) throws Exception {
//        zip("C:\\Users\\lin.xia\\Documents\\rooomy\\test.zip", "C:\\Users\\lin.xia\\Documents\\rooomy\\test");
//        unzip("C:\\Users\\lin.xia\\Documents\\rooomy\\test.zip", "C:\\Users\\lin.xia\\Documents\\rooomy");
//        unzip("C:\\Users\\lin.xia\\Documents\\rooomy\\test.zip");
//        zip("C:\\Users\\lin.xia\\Documents\\rooomy\\test");
        String name = getFileName("test.zip");
        System.out.println(name);
    }

    public static void zip(String zipFileName, String sourceFileName) throws Exception {
        System.out.println("压缩中...");
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        BufferedOutputStream bos = new BufferedOutputStream(out);
        File sourceFile = new File(sourceFileName);
        compress(out, bos, sourceFile, sourceFile.getName());
        out.flush();
        bos.flush();
        bos.close();
        out.close();
        System.out.println("压缩完成:" + zipFileName);
    }

    public static void zip(String sourceFileName) throws Exception {
        String zipFileName = sourceFileName + ".zip";
        zip(zipFileName, sourceFileName);
        FileHelper.deleteDir(sourceFileName);
    }

    private static void compress(ZipOutputStream out, BufferedOutputStream bos, File sourceFile, String base) throws Exception {
        if (sourceFile.isDirectory()) {
            File[] flist = sourceFile.listFiles();
            if (flist.length == 0)//如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
            {
                System.out.println(base + "/");
                out.putNextEntry(new ZipEntry(base + "/"));
            } else//如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
            {
                for (int i = 0; i < flist.length; i++) {
                    compress(out, bos, flist[i], base + "/" + flist[i].getName());
                }
            }
        } else//如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
        {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream fos = new FileInputStream(sourceFile);
            BufferedInputStream bis = new BufferedInputStream(fos);

            int tag;
            System.out.println(base);
            while ((tag = bis.read()) != -1) {
                bos.write(tag);
            }
            bos.flush();
            bis.close();
            fos.close();
        }
    }

    public static void unzip(String zipFileName) throws IOException {
        File zipFile = new File(zipFileName);
        if (!zipFile.exists()) {
            System.out.println(zipFileName + " not found.");
            return;
        }
        String destFolderName = zipFile.getParent();
        unzip(zipFileName, destFolderName);
    }

    public static void unzip(String zipFileName, String destFileName) throws IOException {
        File destFolder = new File(destFileName);
        ZipInputStream zi = new ZipInputStream(new FileInputStream(zipFileName));
        ZipEntry ze = null;
        FileOutputStream fo = null;
        byte[] buff = new byte[1024];
        int len;
        while ((ze = zi.getNextEntry()) != null) {
            File file = new File(destFolder, ze.getName());
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            if (ze.isDirectory()) {
                file.mkdir();
            } else {
                fo = new FileOutputStream(file);
                while ((len = zi.read(buff)) > 0) fo.write(buff, 0, len);
                fo.flush();
                fo.close();
            }
            zi.closeEntry();
        }
        zi.close();
        System.out.println("Unzip successful " + zipFileName);
    }

    public static String getFileName(String path) {
        int lastIndex = path.lastIndexOf(File.separator);
        return path.substring(lastIndex + File.separator.length(), path.length());
    }
}
