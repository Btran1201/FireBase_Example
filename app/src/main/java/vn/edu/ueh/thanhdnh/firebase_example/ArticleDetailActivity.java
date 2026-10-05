package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class ArticleDetailActivity extends AppCompatActivity {
    ImageView imgDetail;
    TextView txtDetailTitle, txtDetailDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_article_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgDetail = findViewById(R.id.img_detail);
        txtDetailTitle = findViewById(R.id.txt_detail_title);
        txtDetailDescription = findViewById(R.id.txt_detail_description);

        String title = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");
        String description = getIntent().getStringExtra("description");

        txtDetailTitle.setText(title);
        txtDetailDescription.setText(description);
        if (image != null && !image.isEmpty()) {
            Picasso.get().load(image).resize(600, 400).centerCrop()
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(imgDetail);
        }
    }
}