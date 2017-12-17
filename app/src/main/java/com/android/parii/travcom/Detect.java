
package com.android.parii.travcom;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

import java.io.File;
import java.io.FileNotFoundException;

public class Detect extends AppCompatActivity  {

    ImageView imageView, imgTakePicture;
    Button btnProcessNext, btnTakePicture;
    TextView txtSampleDesc, txtTakenPicDesc;
    private FaceDetector detector;
    Bitmap editedBitmap;
    int currentIndex = 0;
    int[] imageArray;
    private Uri imageUri;
    private static final int REQUEST_WRITE_PERMISSION = 200;
    private static final int CAMERA_REQUEST = 101;

    private static final String SAVED_INSTANCE_URI = "uri";
    private static final String SAVED_INSTANCE_BITMAP = "bitmap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageArray = new int[]{R.drawable.me, R.drawable.me, R.drawable.me};
        detector = new FaceDetector.Builder(getApplicationContext())
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_CLASSIFICATIONS)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        initViews();

    }


    public void see(View v) {

        Intent i = new Intent(Detect.this,Webview.class);
        startActivity(i);
    }


    public void take(View v) {

        ActivityCompat.requestPermissions(Detect.this, new
                String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
    }



    public void imgtake(View v) {
        ActivityCompat.requestPermissions(Detect.this, new
                String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);

    }


    private void initViews()
    {
        // imageView = (ImageView) findViewById(R.id.imageView);
        imgTakePicture = (ImageView) findViewById(R.id.imgTakePic);
        // btnProcessNext = (Button) findViewById(R.id.btnProcessNext);
        btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
        // txtSampleDesc = (TextView) findViewById(R.id.txtSampleDescription);
        txtTakenPicDesc = (TextView) findViewById(R.id.txtTakePicture);

        //processImage(imageArray[currentIndex]);
        //currentIndex++;

        //btnProcessNext.setOnClickListener(this);
        //   btnTakePicture.setOnClickListener(this);
        //   imgTakePicture.setOnClickListener(this);
    }


    /*
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnProcessNext:
                imageView.setImageResource(imageArray[currentIndex]);
                processImage(imageArray[currentIndex]);
                if (currentIndex == imageArray.length - 1)
                    currentIndex = 0;
                else
                    currentIndex++;

                break;



            case R.id.btnTakePicture:
                ActivityCompat.requestPermissions(Detect.this, new
                        String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
                break;

            case R.id.imgTakePic:
                ActivityCompat.requestPermissions(Detect.this, new
                        String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
                break;
        }
    }

    */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            launchMediaScanIntent();
            try {
                processCameraPicture();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to load Image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void launchMediaScanIntent()
    {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(imageUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "photo.jpg");
        imageUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (imageUri != null) {
            outState.putParcelable(SAVED_INSTANCE_BITMAP, editedBitmap);
            outState.putString(SAVED_INSTANCE_URI, imageUri.toString());
        }
        super.onSaveInstanceState(outState);
    }

    /*
        private void processImage(int image)
        {

            Bitmap bitmap = decodeBitmapImage(image);
            if (detector.isOperational() && bitmap != null) {
                editedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                        .getHeight(), bitmap.getConfig());
                float scale = getResources().getDisplayMetrics().density;
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.GREEN);
                paint.setTextSize((int) (16 * scale));
                paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(6f);
                Canvas canvas = new Canvas(editedBitmap);
                canvas.drawBitmap(bitmap, 0, 0, paint);
                Frame frame = new Frame.Builder().setBitmap(editedBitmap).build();
                SparseArray<Face> faces = detector.detect(frame);
                txtSampleDesc.setText(" . ");

                for (int index = 0; index < faces.size(); ++index) {
                    Face face = faces.valueAt(index);
                    canvas.drawRect(
                            face.getPosition().x,
                            face.getPosition().y,
                            face.getPosition().x + face.getWidth(),
                            face.getPosition().y + face.getHeight(), paint);


                    canvas.drawText("Face " + (index + 1), face.getPosition().x + face.getWidth(), face.getPosition().y + face.getHeight(), paint);

                    txtSampleDesc.setText(txtSampleDesc.getText() + "FACE " + (index + 1) + "\n");
                    txtSampleDesc.setText(txtSampleDesc.getText() + "Smile probability:" + " " + face.getIsSmilingProbability() + "\n");
                    txtSampleDesc.setText(txtSampleDesc.getText() + "Left Eye Is Open Probability: " + " " + face.getIsLeftEyeOpenProbability() + "\n");
                    txtSampleDesc.setText(txtSampleDesc.getText() + "Right Eye Is Open Probability: " + " " + face.getIsRightEyeOpenProbability() + "\n\n");

                    for (Landmark landmark : face.getLandmarks()) {
                        int cx = (int) (landmark.getPosition().x);
                        int cy = (int) (landmark.getPosition().y);
                        canvas.drawCircle(cx, cy, 8, paint);
                    }


                }

                if (faces.size() == 0) {
                    txtSampleDesc.setText("Scan Failed: Found nothing to scan");
                } else {
                    imageView.setImageBitmap(editedBitmap);
                    txtSampleDesc.setText(txtSampleDesc.getText() + "No of Faces Detected: " + " " + String.valueOf(faces.size()));
                }
            } else {
                txtSampleDesc.setText("Could not set up the detector!");
            }
        }
    */
    private Bitmap decodeBitmapImage(int image) {
        int targetW = 300;
        int targetH = 300;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(getResources(), image,
                bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeResource(getResources(), image,
                bmOptions);
    }

    private void processCameraPicture() throws Exception {
        Bitmap bitmap = decodeBitmapUri(this, imageUri);
        if (detector.isOperational() && bitmap != null) {
            editedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                    .getHeight(), bitmap.getConfig());
            float scale = getResources().getDisplayMetrics().density;
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.GREEN);
            paint.setTextSize((int) (16 * scale));
            paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(6f);
            Canvas canvas = new Canvas(editedBitmap);
            canvas.drawBitmap(bitmap, 0, 0, paint);
            Frame frame = new Frame.Builder().setBitmap(editedBitmap).build();
            SparseArray<Face> faces = detector.detect(frame);
            txtTakenPicDesc.setText(null);

            for (int index = 0; index < faces.size(); ++index) {
                Face face = faces.valueAt(index);
                float x = face.getIsSmilingProbability();
                canvas.drawRect(
                        face.getPosition().x,
                        face.getPosition().y,
                        face.getPosition().x + face.getWidth(),
                        face.getPosition().y + face.getHeight(), paint);


                canvas.drawText("Face " + (index + 1), face.getPosition().x + face.getWidth(), face.getPosition().y + face.getHeight(), paint);

                txtTakenPicDesc.setText("FACE " + (index + 1) + "\n");
                txtTakenPicDesc.setText(txtTakenPicDesc.getText() + "Smile probability:" + " " + face.getIsSmilingProbability() + "\n");
                txtTakenPicDesc.setText(txtTakenPicDesc.getText() + "Left Eye Is Open Probability: " + " " + face.getIsLeftEyeOpenProbability() + "\n");
                txtTakenPicDesc.setText(txtTakenPicDesc.getText() + "Right Eye Is Open Probability: " + " " + face.getIsRightEyeOpenProbability() + "\n\n");

                for (Landmark landmark : face.getLandmarks())
                {
                    int cx = (int) (landmark.getPosition().x);
                    int cy = (int) (landmark.getPosition().y);
                    canvas.drawCircle(cx, cy, 8, paint);
                }


                if(x > 0.800)
                {

                    Intent i = new Intent(Detect.this,Webview.class);
                    startActivity(i);
                }
            }

            if (faces.size() == 0) {
                txtTakenPicDesc.setText("Scan Failed: Found nothing to scan");
            } else {
                imgTakePicture.setImageBitmap(editedBitmap);
                txtTakenPicDesc.setText(txtTakenPicDesc.getText() + "No of Faces Detected: " + " " + String.valueOf(faces.size()));
            }
        } else {
            txtTakenPicDesc.setText("Could not set up the detector!");
        }
    }

    private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {
        int targetW = 300;
        int targetH = 300;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(ctx.getContentResolver()
                .openInputStream(uri), null, bmOptions);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        detector.release();
    }

}

