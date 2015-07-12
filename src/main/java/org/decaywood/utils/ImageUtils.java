package org.decaywood.utils;

import org.decaywood.exceptions.ValueCantCastException;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author: decaywood
 * @date: 2015/6/11 16:51
 *
 * if two pictures are same in pixel level, we define these two
 * pictures are similar, and the uuid of these two pictures
 * are same.
 * the main reason to define this rule is to save the picture
 * storage space.
 *
 */
public class ImageUtils {

    private final static char[] digits = {
            '0' , '1' , '2' , '3' , '4' , '5' ,
            '6' , '7' , '8' , '9' , 'a' , 'b' ,
            'c' , 'd' , 'e' , 'f'};



    private static String generateUUID(BufferedImage image, int UUIDBitsX, int UUIDBitsY) throws ValueCantCastException {

        int barrierX = image.getWidth() / UUIDBitsX;
        int barrierY = image.getHeight() / UUIDBitsY;

        StringBuilder builder = new StringBuilder();

        for (int x = 0; x < UUIDBitsX; x++) {
            for (int y = 0; y < UUIDBitsY; y++) {
                int rgb = image.getRGB(x * barrierX, y * barrierY);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb >> 0) & 0xFF;
                int grayPixel = (b * 29 + g * 150 + r * 77 + 128) >> 8;

                int integer = (int) (grayPixel * 1d / 255 * 15);
                char hex = integerToHexChar(integer);
                builder.append(hex);
            }
        }

        return builder.toString();
    }

    public static BufferedImage resizeImage(BufferedImage srcImage, int targetWidth, int targetHeight) {

        BufferedImage targetImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = targetImage.getGraphics();
        graphics.drawImage(srcImage, 0, 0, targetWidth, targetHeight, null);

        return targetImage;

    }

    public static String generateUUID(BufferedImage bufferedImage) throws ValueCantCastException {
        return generateUUID(bufferedImage, 64);
    }

    public static String generateUUID(BufferedImage bufferedImage, int bits) throws ValueCantCastException {

        int UUIDBits = (int) Math.sqrt(bits);
        return generateUUID(bufferedImage, UUIDBits, UUIDBits);

    }

    public static BufferedImage resizeImage(BufferedImage srcImage) {
        return resizeImage(srcImage, 32);
    }

    public static BufferedImage resizeImage(BufferedImage srcImage, int size) {
        return resizeImage(srcImage, size, size);
    }

    private static char integerToHexChar(int i) throws ValueCantCastException {

        if (i < 0 || i > 15) {
            throw new ValueCantCastException("Can't cast the number to hex, number = " + i);
        }

        return digits[i];
    }
}
