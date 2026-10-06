package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView imgArticle;
    private TextView txtTitle, txtDescription;
    private ArticleViewAdapter adapter;

    public ArticleViewHolder(@NonNull View itemView, ArticleViewAdapter adapter) {
        super(itemView);
        imgArticle = itemView.findViewById(R.id.img_article);
        txtTitle = itemView.findViewById(R.id.txt_title);
        txtDescription = itemView.findViewById(R.id.txt_description);
        this.adapter = adapter;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int position = getLayoutPosition();
        if (position == RecyclerView.NO_POSITION) return;

        // Chỉ gửi ID document; trang Detail tự đọc dữ liệu mới nhất từ Firestore
        Intent intent = new Intent(view.getContext(), ArticleDetailActivity.class);
        intent.putExtra("id", adapter.getArticleId(position));
        view.getContext().startActivity(intent);
    }

    public ImageView getImgArticle() {
        return imgArticle;
    }

    public TextView getTxtTitle() {
        return txtTitle;
    }

    public TextView getTxtDescription() {
        return txtDescription;
    }
}