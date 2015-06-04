package com.rariman.appsqlite.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rariman.appsqlite.R;
import com.rariman.appsqlite.domain.Book;

import java.util.List;

public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.BookViewHolder> {

    private List<Book> bookList;

    public BookRecyclerAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("LOG", "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_short_description, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Log.d("LOG", "onBindViewHolder");
        holder.title.setText(bookList.get(position).getTitle());
        holder.description.setText(bookList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;

        public BookViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleTextView);
            description = (TextView) itemView.findViewById(R.id.descriptionTextView);
        }
    }
}
