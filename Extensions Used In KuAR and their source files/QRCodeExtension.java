package com.example.QRCodeExtension;

import android.graphics.Bitmap;
import android.util.Base64;
import android.graphics.Bitmap.CompressFormat;
import com.google.zxing.WriterException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import java.io.ByteArrayOutputStream;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.common.*;

@DesignerComponent(version = 1,
        description = "Extension to generate QR code from text",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = "images/extension.png")
@SimpleObject(external = true)
public class QRCodeExtension extends AndroidNonvisibleComponent {

    public QRCodeExtension(ComponentContainer container) {
        super(container.$form());
    }

    @SimpleFunction(description = "Generates a QR code image from the given text and returns it as Base64 string.")
    public String GenerateQRCode(String text) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512);
            Bitmap bmp = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888); // Use ARGB_8888 for better quality

            for (int x = 0; x < 512; x++) {
                for (int y = 0; y < 512; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF); // Black and white pixels
                }
            }

            // Convert Bitmap to Base64 string
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bmp.compress(CompressFormat.PNG, 100, byteArrayOutputStream); // Use PNG format
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT); // Encode as Base64

        } catch (WriterException e) {
            return "Error: " + e.getMessage(); // Return error message if encoding fails
        }
    }
}