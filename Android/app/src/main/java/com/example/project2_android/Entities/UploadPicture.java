package com.example.project2_android.Entities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import com.example.project2_android.R;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class UploadPicture {
    private Uri imageUri;
    private String imageBase64;

    private static final int GALLERY_REQUEST_CODE = 1000;
    private static final int CAMERA_REQUEST_CODE = 100;
    private final ImageView image;


    public UploadPicture(ImageView image, Uri imageUri) {
        this.image = image;
        this.imageUri = imageUri;
    }

    public String getImageBase64(){
        return this.imageBase64;
    }



    public void showPopupMenu(View v, Context context, Activity activity) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.inflate(R.menu.sign_in_image);

        popupMenu.setOnMenuItemClickListener(item -> {
            // Handle menu item clicks here
            handleMenuItemClick(item, activity);
            return true;
        });

        popupMenu.show();
    }

    private void handleMenuItemClick(MenuItem item, Activity activity) {
        if (item.getItemId() == R.id.gallery) {
            openGallery(activity);
        } else if (item.getItemId() == R.id.camera) {
            openCamera(activity);
        }
    }

    public void openGallery(Activity activity) {
        Intent iGallery = new Intent(Intent.ACTION_PICK);
        iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(iGallery, GALLERY_REQUEST_CODE);
    }

    public void openCamera(Activity activity) {
        Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(iCamera, CAMERA_REQUEST_CODE);
    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data, Context context) throws IOException {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {
                assert data != null;
                imageUri = data.getData();

                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                imageBase64 = bitmapToBase64(imageBitmap);

                // image.setImageURI(imageUri);
            } else if (requestCode == CAMERA_REQUEST_CODE) {
                assert data != null;
                Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                image.setImageBitmap(photo);
                assert photo != null;
                imageBase64 = bitmapToBase64(photo);


            }
        }
    }



    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String base64String = Base64.encodeToString(byteArray, Base64.DEFAULT);
        // Add the prefix
        String prefix = "data:image/png;base64,";
        return prefix + base64String;
    }

    public static Bitmap convertStringToBitmap(String imageString){
        try {
            // Remove the prefix if present
            String pureBase64 = imageString.substring(imageString.indexOf(",") + 1);
            byte[] encodeByte = Base64.decode(pureBase64, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
