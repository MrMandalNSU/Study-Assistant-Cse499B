package com.example.doctalk;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

import static java.lang.Math.min;

public class text_recognition extends AppCompatActivity {

    EditText mResult;
    ImageView mImagePreview;
    CardView mSearchCard;


    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;

    TextView stringTextView; //This is for medicines
    TextView stringTextView2; //This is for exercises

    ArrayList<String> list1 = new ArrayList<>(); //array list for medicines
    ArrayList<String> list2 = new ArrayList<>(); //array list for exercise

    String selectedItem;

    private android.app.AlertDialog.Builder spinner;

    public static char getCharFromString(String str, int index)
    {
        return str.charAt(index);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Click + Button to insert Image");

        mResult = findViewById(R.id.result);
        mImagePreview= findViewById(R.id.imagePreview);
        mSearchCard = findViewById(R.id.ytcard);

        //stringTextView = (TextView)findViewById(R.id.TV1); //This is for medicines
        //stringTextView2 = (TextView)findViewById(R.id.TV2); //This is for exercises


        //Camera Permission

        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    }

    //action bar menu //latest
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    // handle actionbar item clicks

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addimage)
        {
            showImageimportDialog();
        }

        if(id == R.id.settings)
        {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showImageimportDialog() {

        String[] items = {"Camera", "Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        //set title
        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0)
                {
                    //camera clicked
                    if(!checkCamerapermission())
                    {
                        //camera permission not allowed
                        requestCameraPermission();

                    }
                    else  {
                        //permission allowed.
                        PickCamera();
                    }
                }
                if (i==1)
                {
                    //gallery option clicked
                    if(!checkStoragepermission())
                    {
                        //Storage permission not allowed
                        requestStoragePermission();

                    }
                    else  {
                        //permission allowed.
                        PickGallery();
                    }
                }


            }
        });

        dialog.create().show(); //show dialog
    }

    private void PickGallery() {
        //intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);

        //set intent type to image

        intent.setType("Image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);


    }

    private void PickCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image to Text");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragepermission() {
        boolean result =  ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return  result;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);

    }

    private boolean checkCamerapermission() {

        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 =  ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    //Handle permission result

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writestorageaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (cameraaccepted && writestorageaccepted) {
                        PickCamera();
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }


                }
                break;
            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean writestorageaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (writestorageaccepted) {
                        PickGallery();
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }


                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // get image from camera
        if (resultCode == RESULT_OK)
        {
            if (requestCode == IMAGE_PICK_GALLERY_CODE)
            {
                // get that image from gallery now crop the image
                CropImage.activity(data.getData()).setGuidelines(CropImageView.Guidelines.ON).start(this); // enable image guideline

            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE)
            {
                // got the image from camera now crop it
                CropImage.activity(image_uri).setGuidelines(CropImageView.Guidelines.ON).start(this);
            }
        }

        // get the crop image

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                Uri resultUri = result.getUri(); // set image  to imageview
                mImagePreview.setImageURI(resultUri);

                // get drawable bitmap for text recog.

                BitmapDrawable bitmapDrawable = (BitmapDrawable)mImagePreview.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

                if (!recognizer.isOperational())

                {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder stringBuilder = new StringBuilder();
                    // get text from string builder until there is no text.

                    for (int i = 0; i<items.size(); i++)
                    {
                        TextBlock myItem = items.valueAt(i);
                        stringBuilder.append(myItem.getValue());
                        stringBuilder.append("\n");

                    }

                    //set text to edit text

                    mResult.setText(stringBuilder.toString());

                    mSearchCard.setVisibility(View.VISIBLE);

                    textMod(); //calling the function for text modification
                }
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
            {
                // If there is any error show that.
                Exception error = result.getError();
                Toast.makeText(this, ""+ error, Toast.LENGTH_SHORT).show();
            }
        }

    }

    // -- modification of the text part -- //


    public void textMod() {

        list1.clear();
        list2.clear();

        String line = mResult.getText().toString();

        String word = "";

        Integer cnt = 0;

        Boolean flag1 = false;
        Boolean flag2 = false;

        Integer sz = line.length();

        for (int i = 0; i < sz; i++) {

            if (getCharFromString(line, i) == ':' && cnt == 0) {
                cnt++;
                flag1 = true;
                continue;
            }

            if (getCharFromString(line, i) == ':' && cnt != 0) {
                flag2 = true;
                continue;
            }

            if (flag1) //For medicines only
            {
                if (getCharFromString(line, i) == ',' || getCharFromString(line, i) == '.') {
                    list1.add(word);
                    word = "";

                    if (getCharFromString(line, i) == '.') {
                        flag1 = false;
                        continue;
                    }

                    continue;

                }

                if (getCharFromString(line, i) != ' ') {
                    word += getCharFromString(line, i);
                }
            }

            if (flag2) //For exercises only
            {
                if (getCharFromString(line, i) == ',' || getCharFromString(line, i) == '.') {
                    list2.add(word);
                    word = "";

                    if (getCharFromString(line, i) == '.') {
                        flag2 = false;
                        continue;
                    }

                    continue;

                }

                if (getCharFromString(line, i) != ' ') {
                    word += getCharFromString(line, i);
                }
            }

        }

        /* Previous version of Med and Exe

        for(int i=0; i < list1.size(); i++){
            stringTextView.setText(stringTextView.getText() + list1.get(i) + " -- ");
        }

        for(int i=0; i < list2.size(); i++){
            stringTextView2.setText(stringTextView2.getText() + list2.get(i) + " -- ");
        }

        */

        //list1.clear();
        //list2.clear();

        //////////////////// -- Spinner -- ////////////////////

        Spinner spinnerMed = findViewById(R.id.spinnerMed); //This is for the Spinner Med
        Spinner spinnerExe = findViewById(R.id.spinnerExe); //This is for the Spinner Exe

        //Medicine
        final ArrayAdapter<String> arrayAdapterMed = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1);
        arrayAdapterMed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMed.setAdapter(arrayAdapterMed);

        spinnerMed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String tutorialsName = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(), "Selected: " + tutorialsName, Toast.LENGTH_LONG).show();

                selectedItem = tutorialsName;
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


        //Exercise
        final ArrayAdapter<String> arrayAdapterExe = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);
        arrayAdapterExe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExe.setAdapter(arrayAdapterExe);

        spinnerExe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String tutorialsName = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(), "Selected: " + tutorialsName, Toast.LENGTH_LONG).show();

                selectedItem = tutorialsName;
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }

        });

        //////////////////// -- Spinner -- ////////////////////

    }

    // --     mod of text ends here     -- //


    // get url string to search for
    public String getUrlStringG() {

        //String str = mResult.getText().toString();

        String str = selectedItem;

        String ret = "https://www.google.com/search?q=";

        for(int i=0; i<min(str.length(), 250); i++) {

            ret += str.charAt(i);
        }

        return ret;
    }

    // search on YouTube
    public void searchGoogle(View view) {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getUrlStringG()));

        startActivity(browserIntent);

    }

    // get url string to search for
    public String getUrlString() {

        //String str = mResult.getText().toString();

        String str = selectedItem;

        String ret = "https://www.youtube.com/results?search_query=";

        for(int i=0; i<min(str.length(), 250); i++) {

            ret += str.charAt(i);
        }

        return ret;
    }

    // search on YouTube
    public void searchYoutube(View view) {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getUrlString()));

        startActivity(browserIntent);

    }

}