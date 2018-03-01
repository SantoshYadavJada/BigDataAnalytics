package imageclassification.kiran.com.android_application;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button NewImage;
    private Button Gallery;

    private int PICK_IMAGE_REQUEST = 1;
    private int REQUEST_IMAGE_CAPTURE=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NewImage = (Button) findViewById(R.id.NewImage);
        Gallery =(Button) findViewById(R.id.Gallery);
        NewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");                                 // Show only images, no videos or anything else
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            Intent Show= new Intent();
            Show.setClass(this,ImageProfileActivity.class);
            Show.putExtra("ImagePath",uri.toString());
            startActivity(Show);
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            Intent Show= new Intent();
            Show.setClass(this,ImageProfileActivity.class);
            Show.putExtra("ImagePath",uri.toString());
            startActivity(Show);
        }


    }


}
