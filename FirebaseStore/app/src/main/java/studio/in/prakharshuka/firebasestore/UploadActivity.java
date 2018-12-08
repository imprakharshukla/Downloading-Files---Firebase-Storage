package studio.in.prakharshuka.firebasestore;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class UploadActivity extends AppCompatActivity {
    private StorageReference mStorageRef;
    Button upload;
    Button select;
    int CODE=215;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        upload=findViewById(R.id.upload);
        select=findViewById(R.id.select);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();
            }
        });

    }

    public void selectFile ()
    {

        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("*/*");
        startActivityForResult(Intent.createChooser(i,"Select a file"), CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String filePath = data.getDataString();
        Uri SelectedFileLocation=Uri.parse(filePath);
        UploadFile(SelectedFileLocation);

        super.onActivityResult(requestCode, resultCode, data);

    }

    public  void UploadFile(Uri file)
    {
        Toast.makeText(this, "Please wait.. the file is uploading!", Toast.LENGTH_SHORT).show();
        //Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        StorageReference riversRef = mStorageRef.child(file.getLastPathSegment());

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(UploadActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(UploadActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
