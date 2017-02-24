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
 * ͼ������
 */
public class ImageUtils {
	
	/**
	 * ���ش洢·��
	 * @param imgPath
	 * @return
	 */
    public static String saveBmpImage(String imgPath) {
    	int tail = imgPath.lastIndexOf("."); 
    	if(tail >= 0){
    		//�����bmp�ļ�����ִ�в���,ֱ�ӷ���ԭ·��
    		if(imgPath.substring(tail + 1).toLowerCase().equals("bmp"))
    			return imgPath;
    	}
    	Bitmap originBitmap = null;
    	try{
    		originBitmap = BitmapFactory.decodeFile(imgPath);
    	}catch(OutOfMemoryError error){
    		//���ڷ���OOM�Ŀ�����
    		System.gc();
    	}
    			
        if (originBitmap == null)  
            return null; 
        // λͼ��С  
        int nBmpWidth = originBitmap.getWidth();  
        int nBmpHeight = originBitmap.getHeight();  
        // ͼ�����ݴ�С  
        int bufferSize = nBmpHeight * (nBmpWidth * 3 + nBmpWidth % 4);  
        try {  
        	File originParentFile = new File(imgPath).getParentFile();
        	File saveFile = new File(originParentFile, UUID.randomUUID().toString() + ".bmp");
        	String savePath = saveFile.getPath();
        /*    if (!saveFile.exists()) {  
                saveFile.createNewFile();  
            }  */
            FileOutputStream fileos = new FileOutputStream(saveFile);  
            // bmp�ļ�ͷ  
            int bfType = 0x4d42;  
            long bfSize = 14 + 40 + bufferSize;  
            int bfReserved1 = 0;  
            int bfReserved2 = 0;  
            long bfOffBits = 14 + 40;  
            // ����bmp�ļ�ͷ  
            writeWord(fileos, bfType);  
            writeDword(fileos, bfSize);  
            writeWord(fileos, bfReserved1);  
            writeWord(fileos, bfReserved2);  
            writeDword(fileos, bfOffBits);  
            // bmp��Ϣͷ  
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
            // ����bmp��Ϣͷ  
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
            // ����ɨ��  
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
