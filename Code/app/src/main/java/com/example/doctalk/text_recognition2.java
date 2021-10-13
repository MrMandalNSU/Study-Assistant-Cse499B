package com.example.doctalk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.min;

public class text_recognition2 extends AppCompatActivity {

    EditText mResult;
    ImageView mImagePreview;
    CardView mSearchCard;
    ListView mListView;


    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;

    TextView stringTextView; //This is for diseases

    ArrayList<String> list1 = new ArrayList<String>(); //array list for diseases

    public static char getCharFromString(String str, int index)
    {
        return str.charAt(index);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Click + Button to insert Image");

        mResult = findViewById(R.id.result2);
        mImagePreview= findViewById(R.id.imagePreview2);
        mSearchCard = findViewById(R.id.ytcard2);
        mListView = findViewById(R.id.DisList);

        stringTextView = (TextView)findViewById(R.id.TV3); //This is for diseases


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

        String[] disease_list = getResources().getStringArray(R.array.diseases);
        Set<String> disease_set = new HashSet<String>();
        Set<String> alreadyIn = new HashSet<String>();

        for(int i=0; i<disease_list.length; i++) {
            disease_set.add(disease_list[i]);
        }

        String txt = mResult.getText().toString();
        String word = new String();

        list1.clear();

        for(int i=0; i<txt.length(); i++) {
            if(txt.charAt(i)>='a' && txt.charAt(i)<='z') {
                word += txt.charAt(i);
            }
            else if(txt.charAt(i)>='A' && txt.charAt(i)<='Z') {
                word += (txt.charAt(i)-'A'+'a');
            }
            else {
                if(word.length()>0) {
                    if(disease_set.contains(word) && !alreadyIn.contains(word)) {
                        list1.add(word);
                        alreadyIn.add(word);
                    }
                }
                word = "";
            }
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list1);

        mListView.setAdapter(arrayAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String urlString = "https://www.youtube.com/results?search_query=";

                urlString += list1.get(i).toString();
                urlString += " disease";

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));

                startActivity(browserIntent);
            }
        });
    }

    // --     mod of text ends here     -- //


    // get url string to search for
    public String getUrlStringG() {

        String str = mResult.getText().toString();

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

        String str = mResult.getText().toString();

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