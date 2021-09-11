package com.Client.pay.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.Client.pay.R;
import com.Client.pay.utils.SharedPreferenceKeys;
import com.Client.pay.utils.Utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ProfileActivity extends AppCompatActivity {

    private View toolbar;
    private Context context;
    private ImageView imgmenu;
    private ImageView imgback;
    private TextView txt_home;
    private ImageView imgsearch;
    private RelativeLayout rel_main;

    private LinearLayout llsub;
    private ImageView img_userprofile;
    private EditText edt_mobilenumber;
    private EditText et_name;
    private EditText et_email;
    private EditText et_add;
    private EditText et_city;

    private RelativeLayout relname;
    private RelativeLayout relmobnumber;
    private RelativeLayout rel_email;
    private RelativeLayout rel_address;
    private RelativeLayout rel_city;
    private RelativeLayout rel_update;

    private File destination;
    private String imagePath = "";
    private Uri uri;
    private String currentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        overridePendingTransition(R.anim.enter, R.anim.exit);

        initUI();
        setVisibility();
        setClickListener();

    }


    private void initUI() {
        context = this;
        toolbar = findViewById(R.id.toolbar);
        imgmenu = findViewById(R.id.imgmenu);
        imgback = findViewById(R.id.imgback);
        txt_home = findViewById(R.id.txt_home);
        imgsearch = findViewById(R.id.imgsearch);
        rel_update = findViewById(R.id.rel_update);
        rel_update.setClickable(false);

        rel_main = findViewById(R.id.rel_main);

        llsub = findViewById(R.id.llsub);
        llsub.setClickable(false);

        img_userprofile = findViewById(R.id.img_userprofile);
        img_userprofile.setClickable(false);

        edt_mobilenumber = findViewById(R.id.edt_accountnumber);
        edt_mobilenumber.setText(Utilities.getInstance().getPreference(context, SharedPreferenceKeys.phone_number));
        edt_mobilenumber.setEnabled(false);

        et_name = findViewById(R.id.et_name);
        et_name.setText(Utilities.getInstance().getPreference(context, SharedPreferenceKeys.user_name));
        et_name.setEnabled(false);

        et_email = findViewById(R.id.et_confirm_account);
        et_email.setEnabled(false);

        et_add = findViewById(R.id.et_ifsc);
        et_add.setEnabled(false);

        et_city = findViewById(R.id.et_city);
        et_city.setEnabled(false);

    }

    private void setVisibility() {
        imgmenu.setVisibility(View.INVISIBLE);
        imgback.setVisibility(View.VISIBLE);
        txt_home.setText(R.string.profile);
        imgsearch.setImageResource(R.drawable.ic_edit_button);
    }

    private void setClickListener() {


        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEnabled();
                if (llsub.isClickable()) {
                    llsub.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CharSequence[] items = {"Camera", "Gallery"};
                            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Select an Option");
                            builder.setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    if (i == 0) {
                                        dispatchTakePictureIntent();
                                    } else if (i == 1) {
                                        openGallery();
                                    }
                                }
                            });
                            builder.show();
                        }
                    });
                }

                if (rel_update.isClickable()) {
                    rel_update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(ProfileActivity.this, "Update button is click", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void openGallery() {
        if (Utilities.getInstance().checkPermissions((Activity) context, WRITE_EXTERNAL_STORAGE, 102)) {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, 2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();

                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissions[0])) {
                        } else {
                            Utilities.getInstance().grantPermission(context, rel_main, "Please Enable Camera And Storage Permission.");
                        }
                    }
                }

                break;
            case 102:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    openGallery();
                else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissions[0])) {
                        } else {
                            Utilities.getInstance().grantPermission(context, rel_main, "Please Enable Storage Permission.");
                        }
                    }
                }

                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1://camera
                if (resultCode == RESULT_OK) {
                    File f = new File(currentPhotoPath);
                    img_userprofile.setImageURI(Uri.fromFile(f));
                    Log.d("image path is", "path" + Uri.fromFile(f));


                }

                break;
            case 2://gallery
                if (resultCode == RESULT_OK) {
                    Uri contenturi = data.getData();
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imageFileName = "JPEG_" + timeStamp + "_" + getFileExt(contenturi);
                    Log.d("image path is", "path" + imageFileName);
                    img_userprofile.setImageURI(contenturi);

                }
        }
    }

    private String getFileExt(Uri contenturi) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contenturi));
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.pay250.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    private void setEnabled() {
        llsub.setClickable(true);
        et_name.setEnabled(true);
        edt_mobilenumber.setEnabled(true);
        et_email.setEnabled(true);
        et_add.setEnabled(true);
        et_city.setEnabled(true);
        rel_update.setClickable(true);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }

}