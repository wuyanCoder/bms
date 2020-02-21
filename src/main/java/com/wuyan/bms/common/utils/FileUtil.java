package com.wuyan.bms.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * 文件工具类
 *
 */
@Slf4j
public class FileUtil {

	/**
	 * 定义GB的计算常量
	 */
	private static final int GB = 1024 * 1024 * 1024;
	/**
	 * 定义MB的计算常量
	 */
	private static final int MB = 1024 * 1024;
	/**
	 * 定义KB的计算常量
	 */
	private static final int KB = 1024;
	/**
	 * 格式化小数
	 */
	private static final DecimalFormat DF = new DecimalFormat("0.00");

	/**
	 * 默认最大大小 50M
	 */
	public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

	/**
	 * 默认的文件名最大长度 256
	 */
	public static final int DEFAULT_FILE_NAME_LENGTH = 256;

	/**
	 * 上传文件到指定路径
	 *
	 * @param file     文件
	 * @param filePath 路径
	 * @return 文件信息
	 */
	public static File upload(MultipartFile file, String filePath) {
		//校验文件名大小
		int fileNameLength = file.getOriginalFilename().length();
		if (fileNameLength > DEFAULT_FILE_NAME_LENGTH) {
			throw new RuntimeException("默认的文件名最大长度 256");
		}

		String name = getFileNameNoExtension(file.getOriginalFilename());
		String suffix = getExtensionName(file.getOriginalFilename());
		String nowStr = DateFormatUtils.format(new Date(), "yyyyMMddhhmmss");
		try {
			String fileName = name + nowStr + "." + suffix;
			String path = filePath + fileName;
			//getCanonicalPath会将文件路径解析为与操作系统相关的唯一的规范形式的字符串，而getAbsolutePath并不会
			File dest = new File(path).getCanonicalFile();
			// 检测是否存在目录
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();
			}
			// 文件写入
			file.transferTo(dest);
			return dest;
		} catch (IOException e) {
			log.error("上传文件出错,{}", e.getMessage());
		}
		return null;
	}

	/**
	 * 获取文件的后缀名,不带"."
	 *
	 * @param fileName 文件名
	 * @return 后缀名
	 */
	public static String getExtensionName(String fileName) {
		if (StringUtils.isNotEmpty(fileName)) {
			int dot = fileName.lastIndexOf(".");
			if (dot > -1 && dot < (fileName.length() - 1)) {
				return fileName.substring(dot + 1);
			}
		}
		return fileName;
	}

	/**
	 * 获取文件类型
	 * @param type
	 * @return
	 */
	public static String getFileType(String type) {
		String documents = "txt doc pdf ppt pps xlsx xls docx";
		String music = "mp3 wav wma mpa ram ra aac aif m4a";
		String video = "avi mpg mpe mpeg asf wmv mov qt rm mp4 flv m4v webm ogv ogg";
		String image = "bmp dib pcp dif wmf gif jpg tif eps psd cdr iff tga pcd mpt png jpeg ico";
		if (image.contains(type)) {
			return "image";
		} else if (documents.contains(type)) {
			return "document";
		} else if (music.contains(type)) {
			return "music";
		} else if (video.contains(type)) {
			return "video";
		} else {
			return "other";
		}
	}

	/**
	 * 获取文件名称,不带后缀
	 *
	 * @param fileName 文件名
	 * @return 文件名(不带后缀)
	 */
	public static String getFileNameNoExtension(String fileName) {
		if (StringUtils.isNotEmpty(fileName)) {
			int dot = fileName.lastIndexOf(".");
			if (dot > -1 && dot < fileName.length()) {
				return fileName.substring(0, dot);
			}
		}
		return fileName;
	}

	/**
	 * 大小转换,将字节大小转为对应的单位
	 *
	 * @param size 字节大小
	 * @return 转换后的大小
	 */
	public static String getSizeString(long size) {
		String resultSize = "";
		if (size / GB >= 1) {
			//如果当前Byte的值大于等于1GB
			resultSize = DF.format(size / (float) GB + "GB");
		} else if (size / MB >= 1) {
			//如果当前Byte的值大于等于1MB
			resultSize = DF.format(size / (float) MB) + "MB";
		} else if (size / KB >= 1) {
			//如果当前Byte的值大于等于1KB
			resultSize = DF.format(size / (float) KB) + "KB";
		} else {
			resultSize = size + "B";
		}
		return resultSize;
	}

	/**
	 * 删除文件<br>
	 *
	 * @param file 文件对象
	 * @return 成功与否
	 * @throws RuntimeException IO异常
	 */
	public static boolean del(File file) throws RuntimeException {
		if (file == null || false == file.exists()) {
			// 如果文件不存在或已被删除，此处返回true表示删除成功
			return true;
		}
		if (file.isDirectory()) {
			return false;
		}
		// 删除文件或清空后的目录
		return file.delete();
	}

	public static String saveFile(MultipartFile file, String pathname) {
		try {
			File targetFile = new File(pathname);
			if (targetFile.exists()) {
				return pathname;
			}

			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			file.transferTo(targetFile);

			return pathname;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static boolean deleteFile(String pathname) {
		File file = new File(pathname);
		if (file.exists()) {
			boolean flag = file.delete();

			if (flag) {
				File[] files = file.getParentFile().listFiles();
				if (files == null || files.length == 0) {
					file.getParentFile().delete();
				}
			}

			return flag;
		}

		return false;
	}

	public static String fileMd5(InputStream inputStream) {
		try {
			return DigestUtils.md5Hex(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getPath() {
		return "/" + LocalDate.now().toString().replace("-", "/") + "/";
	}

	/**
	 * 将文本写入文件
	 * 
	 * @param value
	 * @param path
	 */
	public static void saveTextFile(String value, String path) {
		FileWriter writer = null;
		try {
			File file = new File(path);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			writer = new FileWriter(file);
			writer.write(value);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getText(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}

		try {
			return getText(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getText(InputStream inputStream) {
		InputStreamReader isr = null;
		BufferedReader bufferedReader = null;
		try {
			isr = new InputStreamReader(inputStream, "utf-8");
			bufferedReader = new BufferedReader(isr);
			StringBuilder builder = new StringBuilder();
			String string;
			while ((string = bufferedReader.readLine()) != null) {
				string = string + "\n";
				builder.append(string);
			}

			return builder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}
}
