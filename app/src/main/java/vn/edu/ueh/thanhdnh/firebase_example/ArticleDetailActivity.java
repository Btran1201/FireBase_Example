package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class ArticleDetailActivity extends AppCompatActivity {
    FirebaseFirestore db;
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

        String id = getIntent().getStringExtra("id");
        if (id == null) {
            finish();
            return;
        }

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        // Lắng nghe realtime đúng document của bài viết đang xem.
        // Truyền "this" để listener tự dừng khi màn hình này đóng.
        db.collection("articles").document(id)
                .addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (snapshot == null) return;

                        if (!snapshot.exists()) {
                            Toast.makeText(ArticleDetailActivity.this, "Bài viết đã bị xoá", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }

                        Map<String, Object> data = snapshot.getData();
                        Article article = new Article((String) data.get("title"),
                                (String) data.get("image"), (String) data.get("description"));
                        showArticle(article);
                    }
                });
    }

    private void showArticle(Article article) {
        txtDetailTitle.setText(article.getTitle());
        txtDetailDescription.setText(article.getDescription());

        String url = article.getImage();
        if (url != null && !url.isEmpty()) {
            Picasso.get().load(url).resize(600, 400).centerCrop()
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(imgDetail);
        } else {
            imgDetail.setImageResource(android.R.drawable.ic_menu_report_image);
        }
    }
}