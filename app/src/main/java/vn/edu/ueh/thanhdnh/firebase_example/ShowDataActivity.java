package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShowDataActivity extends AppCompatActivity {
    FirebaseFirestore db;
    RecyclerView recyclerView;
    List<Article> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FirebaseApp.initializeApp(this);

        recyclerView = findViewById(R.id.reclyclerview);
        ArticleViewAdapter adapter = new ArticleViewAdapter(this, articles);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        /* Day la cach doc dlieu: cach nay doc mot lan tu luc mo man hinh,
        con bai minh dung addSnapshotListener se doc dlieu lan dau va moi lan dlieu thay doi
        db.collection("articles").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
          @Override
          public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if(task.isSuccessful()){
              articles.clear();
              for(QueryDocumentSnapshot q : task.getResult()){
                Map<String, Object> data = q.getData();
                Article article = new Article((String)data.get("title"),
                        (String)data.get("image"), (String)data.get("description"));
                articles.add(article);
              }
              adapter.update(articles);
              adapter.notifyDataSetChanged();
            }
          }
        });*/
        db.collection("articles").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                if (snapshots != null) {
                    articles.clear();
                    for (QueryDocumentSnapshot q : snapshots) {
                        Map<String, Object> data = q.getData();
                        Article article = new Article((String) data.get("title"),
                                (String) data.get("image"), (String) data.get("description"));
                        articles.add(article);
                    }
                    adapter.update(articles);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
