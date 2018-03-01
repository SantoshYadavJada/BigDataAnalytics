package imageclassification.kiran.com.android_application;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ImageProfileActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;
    private ImageView view;
    private ImageButton spark;
    private ImageButton clarifai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        clarifai =(ImageButton)findViewById(R.id.clarifaiButton);
        clarifai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ClarifaiIntent=new Intent();
                ClarifaiIntent.setClass(ImageProfileActivity.this,OutputActivity.class);
                ClarifaiIntent.putExtra("Image",image_path);
                ClarifaiIntent.putExtra("Selection",2);
                startActivity(ClarifaiIntent);
            }
        });


        view = (ImageView) findViewById(R.id.Image) ;
        Intent intent = getIntent();
        final String image_path= intent.getStringExtra("ImagePath");
        Uri fileUri = Uri.parse(image_path);
        view.setImageURI(fileUri);


        spark =(ImageButton)findViewById(R.id.SparkButton);
        spark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sparkIntent= new Intent();
                sparkIntent.setClass(ImageProfileActivity.this,OutputActivity.class);
                sparkIntent.putExtra("Image",image_path);
                sparkIntent.putExtra("Selection",1);
                startActivity(sparkIntent);
            }
        });




    }


}
