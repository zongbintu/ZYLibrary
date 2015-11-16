package com.zongyou.library.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 * 
 * @author Altas
 * @email Altas.TuTu@gmail.com
 * @date 2014年9月25日
 */
public final class FileUtils {
	private static final String TAG = FileUtils.class.getSimpleName();

	public static String getAppExternalStorageDirectory() {

		File file = Environment.getExternalStorageDirectory();
		String p = file.getAbsolutePath() + File.separator + "DCIM"
				+ File.separator;
		File appFile = new File(p);
		if (!appFile.exists()) {
			appFile.mkdirs();
		}

		return appFile.getAbsolutePath();
	}

	/**
	 * 获取app的图片存放路径
	 * 
	 * @return
	 */
	public static String getAppMediaStorageDirectory() {
		File file = Environment.getExternalStorageDirectory();
		File appFile = new File(new StringBuilder()
				.append(file.getAbsolutePath()).append(File.separator)
				.append("DCIM").append(File.separator).append("Camera")
				.append(File.separator).toString());
		if (!appFile.exists()) {
			appFile.mkdirs();
		}

		return appFile.getAbsolutePath() + File.separator;
	}

	public static List<File> getFileNotRecursively(File directory, String suffix) {
		List<File> list = null;
		File[] files = directory.listFiles();
		if (files == null) {
			Log.w(TAG, "listFiles() returned null (directory: " + directory
					+ ")");
		} else {
			list = new ArrayList<File>();
			for (File file : files) {
				if ((file.isFile())
						&& (file.getName().toLowerCase().endsWith(suffix))
						&& (file.canRead())) {
					list.add(file);
				}
			}
		}
		return list;
	}

	public static <T> T readObjectFile(Context context, String fileName,
			Class<T> clazz) {
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		try {
			fileInputStream = context.openFileInput(fileName);
			objectInputStream = new ObjectInputStream(fileInputStream);
			T localObject2 = clazz.cast(objectInputStream.readObject());
			return localObject2;
		} catch (IOException e) {
			LogUtils.e(TAG, "read file fail...", e);
		} catch (ClassNotFoundException e) {
			LogUtils.e(TAG, "read file cast object fail...", e);
		} finally {
			IOUtils.closeSilently(fileInputStream);
			IOUtils.closeSilently(objectInputStream);
		}
		return null;
	}

	public static void writeObjectFile(Context context, String fileName,
			Object dataObject) {
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			fileOutputStream = context.openFileOutput(fileName, 0);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(dataObject);
			objectOutputStream.flush();
		} catch (IOException e) {
			LogUtils.e(TAG, "write file fail...", e);
		} finally {
			IOUtils.closeSilently(objectOutputStream);
			IOUtils.closeSilently(fileOutputStream);
		}
	}

	/**
	 * 写对象文件
	 * 
	 * @param file
	 *            目标文件
	 * @param dataObject
	 *            数据对象
	 */
	public static boolean writeObjectFile(File file, Object dataObject) {
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file, false);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(dataObject);
			objectOutputStream.flush();
		} catch (IOException e) {
			LogUtils.e(TAG, "write file fail...", e);
			return false;
		} finally {
			IOUtils.closeSilently(objectOutputStream);
			IOUtils.closeSilently(fileOutputStream);
		}
		return true;
	}

	public static void writeExternalFile(String dirPath, String fileName,
			String content, boolean append) {
		writeExternalFile(createFile(dirPath, fileName), content, append);
	}

	public static File createFile(String dirPath, String fileName) {
		File dirFile = new File(dirPath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		File file = new File(dirFile, fileName);
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException localIOException) {
			}
		return file;
	}

	public static void writeExternalFile(File targetFile, String content,
			boolean append) {
		OutputStream outputStream = null;
		try {
			outputStream = new BufferedOutputStream(new FileOutputStream(
					targetFile, append));
			outputStream.write(content.getBytes());
		} catch (Exception e) {
			LogUtils.d(TAG, e.getMessage(), e);
		} finally {
			IOUtils.closeSilently(outputStream);
		}
	}

	public static void writeExternalFile(File targetFile,
			InputStream inputStream, boolean append) {
		OutputStream outputStream = null;
		try {
			outputStream = new BufferedOutputStream(new FileOutputStream(
					targetFile, append));
			IOUtils.copyStream(inputStream, outputStream);
		} catch (Exception e) {
			LogUtils.d(TAG, e.getMessage(), e);
		} finally {
			IOUtils.closeSilently(outputStream);
			IOUtils.closeSilently(inputStream);
		}
	}

	public static void writeExternalFile(String dirPath, String fileName,
			InputStream inputStream, boolean append) {
		writeExternalFile(createFile(dirPath, fileName), inputStream, append);
	}

	public static boolean deleteFile(Context context, String name) {
		return context.deleteFile(name);
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				inStream = new FileInputStream(oldPath); // 读入原文件
				fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}

			}
		} catch (Exception e) {
			LogUtils.e("copyFile error", e);
		} finally {
			try {
				if (null != inStream)

					inStream.close();
				if (null != fs)
					fs.close();
			} catch (IOException e) {
			}

		}
	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();

		}

	}
}