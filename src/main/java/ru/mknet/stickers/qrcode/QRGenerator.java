package ru.mknet.stickers.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Hashtable;

import static java.lang.String.format;

public class QRGenerator {

    public static String generateBase64Qr(String ssid, String password) {

        String wifiString = "WIFI:S:%s;T:WPA;P:%s;;";
        wifiString = format(wifiString, ssid, password);

        MultiFormatWriter writer = new MultiFormatWriter();
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            BitMatrix bitMatrix = writer.encode(wifiString, BarcodeFormat.QR_CODE, 1000, 1000, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }
}
