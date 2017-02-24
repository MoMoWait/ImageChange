package com.rockchips.imagechange;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

/**
 * @author GaoFei
 * 图像处理工具
 */
public class ImageUtils {
	
	/**
	 * 返回存储路径
	 * @param imgPath
	 * @return
	 */
    public static String saveBmpImage(String imgPath) {
    	int tail = imgPath.lastIndexOf("."); 
    	if(tail >= 0){
    		//如果是bmp文件，不执行操作,直接返回原路径
    		if(imgPath.substring(tail + 1).toLowerCase().equals("bmp"))
    			return imgPath;
    	}
    	Bitmap originBitmap = null;
    	try{
    		originBitmap = BitmapFactory.decodeFile(imgPath);
    	}catch(OutOfMemoryError error){
    		//存在发生OOM的可能性
    		System.gc();
    	}
    			
        if (originBitmap == null)  
            return null; 
        // 位图大小  
        int nBmpWidth = originBitmap.getWidth();  
        int nBmpHeight = originBitmap.getHeight();  
        // 图像数据大小  
        int bufferSize = nBmpHeight * (nBmpWidth * 3 + nBmpWidth % 4);  
        try {  
        	File originParentFile = new File(imgPath).getParentFile();
        	File saveFile = new File(originParentFile, UUID.randomUUID().toString() + ".bmp");
        	String savePath = saveFile.getPath();
        /*    if (!saveFile.exists()) {  
                saveFile.createNewFile();  
            }  */
            FileOutputStream fileos = new FileOutputStream(saveFile);  
            // bmp文件头  
            int bfType = 0x4d42;  
            long bfSize = 14 + 40 + bufferSize;  
            int bfReserved1 = 0;  
            int bfReserved2 = 0;  
            long bfOffBits = 14 + 40;  
            // 保存bmp文件头  
            writeWord(fileos, bfType);  
            writeDword(fileos, bfSize);  
            writeWord(fileos, bfReserved1);  
            writeWord(fileos, bfReserved2);  
            writeDword(fileos, bfOffBits);  
            // bmp信息头  
            long biSize = 40L;  
            long biWidth = nBmpWidth;  
            long biHeight = nBmpHeight;  
            int biPlanes = 1;  
            int biBitCount = 24;  
            long biCompression = 0L;  
            long biSizeImage = 0L;  
            long biXpelsPerMeter = 0L;  
            long biYPelsPerMeter = 0L;  
            long biClrUsed = 0L;  
            long biClrImportant = 0L;  
            // 保存bmp信息头  
            writeDword(fileos, biSize);  
            writeLong(fileos, biWidth);  
            writeLong(fileos, biHeight);  
            writeWord(fileos, biPlanes);  
            writeWord(fileos, biBitCount);  
            writeDword(fileos, biCompression);  
            writeDword(fileos, biSizeImage);  
            writeLong(fileos, biXpelsPerMeter);  
            writeLong(fileos, biYPelsPerMeter);  
            writeDword(fileos, biClrUsed);  
            writeDword(fileos, biClrImportant);  
            // 像素扫描  
            byte bmpData[] = new byte[bufferSize];  
            int wWidth = (nBmpWidth * 3 + nBmpWidth % 4);  
            for (int nCol = 0, nRealCol = nBmpHeight - 1; nCol < nBmpHeight; ++nCol, --nRealCol)  
                for (int wRow = 0, wByteIdex = 0; wRow < nBmpWidth; wRow++, wByteIdex += 3) {  
                    int clr = originBitmap.getPixel(wRow, nCol);  
                    bmpData[nRealCol * wWidth + wByteIdex] = (byte) Color.blue(clr);  
                    bmpData[nRealCol * wWidth + wByteIdex + 1] = (byte) Color.green(clr);  
                    bmpData[nRealCol * wWidth + wByteIdex + 2] = (byte) Color.red(clr);  
                }  
  
            fileos.write(bmpData);  
            fileos.flush();  
            fileos.close();  
            return savePath;
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        
        return null;
    }  
  
    private static void writeWord(FileOutputStream stream, int value) throws IOException {  
        byte[] b = new byte[2];  
        b[0] = (byte) (value & 0xff);  
        b[1] = (byte) (value >> 8 & 0xff);  
        stream.write(b);  
    }  
  
    private static void writeDword(FileOutputStream stream, long value) throws IOException {  
        byte[] b = new byte[4];  
        b[0] = (byte) (value & 0xff);  
        b[1] = (byte) (value >> 8 & 0xff);  
        b[2] = (byte) (value >> 16 & 0xff);  
        b[3] = (byte) (value >> 24 & 0xff);  
        stream.write(b);  
    }  
  
    private static void writeLong(FileOutputStream stream, long value) throws IOException {  
        byte[] b = new byte[4];  
        b[0] = (byte) (value & 0xff);  
        b[1] = (byte) (value >> 8 & 0xff);  
        b[2] = (byte) (value >> 16 & 0xff);  
        b[3] = (byte) (value >> 24 & 0xff);  
        stream.write(b);  
    }  
}
