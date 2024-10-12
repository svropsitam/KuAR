/*
 version 0.1


package com.example.SaveImageExtension;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.common.ComponentCategory;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@DesignerComponent(version = 1,
        description = "Extension to save a Base64 image as PNG",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = "images/extension.png")
@SimpleObject(external = true)
public class SaveImageExtension extends AndroidNonvisibleComponent {

    private final Activity activity;

    public SaveImageExtension(ComponentContainer container) {
        super(container.$form());
        this.activity = container.$context();
    }

    @SimpleFunction(description = "Converts Base64 string to PNG image and saves it to the app directory.")
    public void SaveBase64ToImage(String base64String, String fileName) {
        // Перевірка дозволу на запис у зовнішню пам'ять
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Запит дозволу, якщо його ще не надано
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            // Дозвіл надано, продовжуємо збереження зображення
            saveImage(base64String, fileName);
        }
    }

    private void saveImage(String base64String, String fileName) {
        // Перетворення Base64 у байтовий масив
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        // Створення зображення з байтового масиву
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        // Отримання директорії додатка
        String directoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyAppImages";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Створити директорію, якщо не існує
        }

        // Створення файлу для зображення
        File imageFile = new File(directory, fileName + ".png");

        // Збереження зображення як PNG
        try (FileOutputStream outputStream = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

*/



/*
package com.example.SaveImageExtension;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Base64;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@DesignerComponent(version = 1,
        description = "Extension to save a Base64 image as PNG",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = "images/extension.png")
@SimpleObject(external = true)
@UsesPermissions(permissionNames = "android.permission.READ_EXTERNAL_STORAGE, android.permission.WRITE_EXTERNAL_STORAGE, android.permission.MANAGE_EXTERNAL_STORAGE")
public class SaveImageExtension extends AndroidNonvisibleComponent {

    private final Activity activity;
    private boolean externalStorageEnabled = false;

    public SaveImageExtension(ComponentContainer container) {
        super(container.$form());
        this.activity = container.$context();
        requestStoragePermission();
    }

    @SimpleProperty(description = "Enable or disable external storage access.", category = PropertyCategory.BEHAVIOR)
    public boolean ExternalStorageEnabled() {
        return externalStorageEnabled;
    }

    @DesignerProperty(defaultValue = "false", editorType = "boolean")
    public void ExternalStorageEnabled(boolean enabled) {
        externalStorageEnabled = enabled;
    }

    @SimpleFunction(description = "Converts Base64 string to PNG image and saves it to the specified directory.")
    public void SaveBase64ToImage(String base64String, String directoryPath, String fileName) {
        if (checkStoragePermission() && externalStorageEnabled) {
            saveImage(base64String, directoryPath, fileName);
        } else {
            throw new YailRuntimeError("Permission not granted or external storage disabled.", "Permission Error");
        }
    }

    private void saveImage(String base64String, String directoryPath, String fileName) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        File directory = new File(Environment.getExternalStorageDirectory(), directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File imageFile = new File(directory, fileName + ".png");

        try (FileOutputStream outputStream = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Request permissions for Android 11+
    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                activity.startActivity(intent);
            }
        } else {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    private boolean checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            return ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED;
        }
    }
}

 */

package com.example.SaveImageExtension;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Base64;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.common.ComponentCategory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@DesignerComponent(version = 1,
        description = "Extension to save a Base64 image as PNG",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = "images/extension.png")
@SimpleObject(external = true)
@UsesPermissions(permissionNames = "android.permission.READ_EXTERNAL_STORAGE, android.permission.WRITE_EXTERNAL_STORAGE, android.permission.READ_MEDIA_IMAGES")
public class SaveImageExtension extends AndroidNonvisibleComponent {

    private final Activity activity;
    private boolean externalStorageEnabled = false;

    public SaveImageExtension(ComponentContainer container) {
        super(container.$form());
        this.activity = container.$context();
        requestStoragePermission();
    }

    @SimpleProperty(description = "Enable or disable external storage access.", category = PropertyCategory.BEHAVIOR)
    public boolean ExternalStorageEnabled() {
        return externalStorageEnabled;
    }

    @DesignerProperty(defaultValue = "false", editorType = "boolean")
    public void ExternalStorageEnabled(boolean enabled) {
        externalStorageEnabled = enabled;
    }

    @SimpleFunction(description = "Converts Base64 string to PNG image and saves it to the specified directory.")
    public void SaveBase64ToImage(String base64String, String directoryPath, String fileName) {
        if (checkStoragePermission() && externalStorageEnabled) {
            saveImage(base64String, directoryPath, fileName);
        } else {
            // Notify user about lack of permissions
        }
    }

    private void saveImage(String base64String, String directoryPath, String fileName) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        File directory = new File(Environment.getExternalStorageDirectory(), directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File imageFile = new File(directory, fileName + ".png");

        try (FileOutputStream outputStream = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Request permissions for Android 13+
    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
            }
        } else {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    private boolean checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES)
                    == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED;
        }
    }
}
