package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArticleViewAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
    private LayoutInflater mInflater;
    private List<Article> articles;
    private List<String> ids = new ArrayList<>(); // ID document tương ứng với từng bài

    public ArticleViewAdapter(Context context, List<Article> articles) {
        this.mInflater = LayoutInflater.from(context);
        this.articles = articles;
    }

    public void update(List<Article> articles) {
        this.articles = articles;
    }

    // Cập nhật cả danh sách bài viết lẫn ID document
    public void update(List<Article> articles, List<String> ids) {
        this.articles = articles;
        this.ids = ids;
    }

    public Article getArticle(int position) {
        return articles.get(position);
    }

    public String getArticleId(int position) {
        return ids.get(position);
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View customView = mInflater.inflate(R.layout.article_list, parent, false);
        return new ArticleViewHolder(customView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article current = articles.get(position);
        holder.getTxtTitle().setText(current.getTitle());
        holder.getTxtDescription().setText(current.getDescription());

        String url = current.getImage();
        if (url != null && !url.isEmpty()) {
            Picasso.get().load(url)
                    .resize(300, 300).centerCrop()
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(holder.getImgArticle());
        } else {
            holder.getImgArticle().setImageResource(android.R.drawable.ic_menu_report_image);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}