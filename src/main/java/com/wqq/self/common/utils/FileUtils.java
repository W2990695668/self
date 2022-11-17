package com.wqq.self.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Configuration
public class FileUtils {

    /**
     * 获取文件的扩展名小写，如mov、mp4
     * 
     * @param filename
     * @return 文件扩展名
     */
    public static String getFileExt(String filename) {
        // Check for "."
        String ext = "";
        int pos = filename.lastIndexOf('.');
        if (pos != -1) {
            // Any sort of path separator found...
            ext = filename.substring(pos + 1).toLowerCase();
        }
        return ext;
    }

    public static String calculateFileSize(long size) {
        String[] units = { "B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB" };
        double s = (double) size;
        int unit = 0;
        while (s >= 1024) {
            s = s / 1024;
            unit++;
        }
        return String.format("%.2f", s).concat(units[unit]);
    }

    public static String calculateFileMd5(String path) throws IOException {
        return calculateFileMd5(Paths.get(path));
    }

    public static String calculateFileMd5(Path path) throws IOException {
        return calculateFileMd5(Files.newInputStream(path));
    }

    public static String calculateFileMd5(File file) throws IOException {
        return calculateFileMd5(new FileInputStream(file));
    }

    public static String calculateFileMd5(InputStream is) throws IOException {
        return DigestUtils.md5DigestAsHex(is);
    }

//    public static String generateRandomFilename(String dir, String fileType) {
//        return generateRandomFilename(dir, fileType, "");
//    }

//    public static String generateRandomFilename(String dir, String fileType, String prefix) {
//        return generateRandomFilenameByExt(dir, "." + fileType, prefix);
//    }
//
//    public static String generateRandomFilenameByExt(String dir, String fileExt) {
//        return generateRandomFilenameByExt(dir, fileExt, "");
//    }
//
//    public static String generateRandomFilenameByExt(String dir, String ext, @Nullable String prefix) {
//        Assert.hasText(dir, "目录不能为空");
//        Assert.hasText(ext, "文件扩展名不能为空");
//        if (prefix == null) {
//            prefix = "";
//        }
//        Path dirPath = Paths.get(dir);
//        createDirIfNotExist(dirPath);
//        int loop = 10;
//        while (loop > 0) {
//            String filename = prefix.concat(RandomStringUtils.randomAlphanumeric(16)).toLowerCase() + ext;
//            if (!Files.exists(Paths.get(dir, filename))) {
//                return filename;
//            }
//            loop--;
//        }
//        throw new IllegalStateException("生成文件名失败");
//    }
//
//    public static String generateRandomDirname(String dir, String prefix) {
//        return generateRandomFilenameByExt(dir, "", "");
//    }

    public static String extractNameFromFilenameByFileType(String filename, String fileType) {
        return filename.replace("." + fileType, "");
    }

    public static String extractNameFromFilenameByFileExt(String filename, String fileExt) {
        return filename.replace(fileExt, "");
    }

    /**
     * 计算子目录名称，每个子目录10000个文件，如文件123.mov的存储目录是0
     * 
     * @param fileId
     * @return
     */
    public static String getDirNameById(Integer fileId) {
        Integer dirNum = fileId - fileId % 10000;
        return dirNum.toString();
    }

    /**
     * 检查路径并创建文件夹
     *
     * @param path 文件夹路径
     */
//    public static void createDirIfNotExist(String path) {
//        Assert.isTrue(StringUtils.isNotBlank(path), "必须指定文件上传路径");
//        createDirIfNotExist(Paths.get(path));
//    }

    /**
     * 检查路径并创建文件夹
     *
     * @param path 文件夹路径
     */
    public static void createDirIfNotExist(Path path) {
        Assert.notNull(path, "必须指定文件目录路径");
        if (!Files.exists(path)) {
            log.info("create dir: {}", path.toString());
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalStateException("文件目录创建失败");
            }
        } else if (!Files.isDirectory(path)) {
            throw new IllegalStateException("路径并非目录");
        } else {
            log.info("dir already exist: {}", path.toString());
        }
    }

    /**
     * 检查路径并创建文件硬链接
     *
     * @param target 文件路径
     * @param source 文件路径
     */
    public static Path createLink(Path target, Path source) {
        log.info("create link: {} from source file: {}", target.toString(), source.toString());
        try {
            return Files.createLink(target, source);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("创建文件链接失败");
        }
    }

    public static Path moveFile(Path target, Path source) {
        try {
            return Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            log.error("move file fail: source path = {}, target path = {} error:{}", source, target, e.getMessage());
            e.printStackTrace();
            throw new IllegalStateException("移动文件失败");
        }
    }

    public static Path copyFile(Path target, Path source) {
        try {
            return Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.info("copy file fail: source path = {}, target path = {} error:{}", source, target, e.getMessage());
            e.printStackTrace();
            throw new IllegalStateException("复制文件失败");
        }
    }

    public static void deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
            log.info("delete file: {}", path.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFileOrDir(Path path) {
        if (Files.isDirectory(path)) {
            // 如果是文件夹，将递归删除文件夹内的文件或子文件夹
            try {
                Files.list(path).forEach(FileUtils::deleteFileOrDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 然后删除文件或文件夹本身
        deleteFile(path);
    }

    public static boolean deleteDirIfEmpty(@Nullable Path path) {
        return Optional.ofNullable(path).filter(Files::isDirectory).map(Path::toFile)
                .filter(dir -> Objects.requireNonNull(dir.list()).length == 0).map(file -> {
                    log.info("delete empty dir: {}", file.toString());
                    return file;
                }).map(File::delete).orElse(false);
    }

    /**
     * 强制压缩/放大图片到固定的大小
     * 
     * @param w int 新宽度
     * @param h int 新高度
     */
    public static void resizeImg(int w, int h, String pathOrig, String pathDest) throws IOException {
        File file = new File(pathOrig);
        BufferedImage img = ImageIO.read(file);
        int width = img.getWidth(null);
        int height = img.getHeight(null);
        if (width / height > w / h) {
            h = (int) (height * w / width);
        } else {
            w = (int) (width * h / height);
        }
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
        File destFile = new File(pathDest);
        // FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
        // 可以正常实现bmp、png、gif转jpg
        // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        // encoder.encode(image); // JPEG编码
        // out.close();

        String formatName = pathDest.substring(pathDest.lastIndexOf(".") + 1);
        ImageIO.write(image, formatName , destFile);
    }

    /*public static String getImgBase64Str(String imgFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(data);
    }*/

    public static String getImgBase64Str(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 指定路径写入文本文件
     * @param path 文件路径
     * @param content 文件内容
     * @throws IOException
     */
    public static void createTextFile(String path, String content) throws IOException {
        try{
            File file = new File(path);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }

            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(content);
            write.flush();
            write.close();
        } catch (Exception e){
            throw new IOException(e.getMessage());
        }
    }

}
