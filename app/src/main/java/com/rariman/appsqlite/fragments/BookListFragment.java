package com.rariman.appsqlite.fragments;


import android.annotation.TargetApi;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import com.rariman.appsqlite.R;
import com.rariman.appsqlite.adapters.BookRecyclerAdapter;
import com.rariman.appsqlite.domain.Book;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookListFragment extends Fragment {

    private View addButton;
    private RecyclerView bookRecyclerView;
    private List<Book> books;


    public BookListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        books = new ArrayList<>();
        books.add(new Book("Harry Potter", "Hogwarts"));
        books.add(new Book("The Lord of the Rings", "Frodo"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView.LayoutManager layoutManager;

        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        addButton = view.findViewById(R.id.add_button);
        addButton.setOutlineProvider(addButtonOutlineProvider);
        addButton.setClipToOutline(true);

        bookRecyclerView = (RecyclerView)view.findViewById(R.id.bookRecyclerView);
        bookRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        bookRecyclerView.setLayoutManager(layoutManager);
        BookRecyclerAdapter bookRecyclerAdapter = new BookRecyclerAdapter(books);
        bookRecyclerView.setAdapter(bookRecyclerAdapter);


        // Inflate the layout for this fragment
        return view;
    }

    ViewOutlineProvider addButtonOutlineProvider = new ViewOutlineProvider() {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void getOutline(View view, Outline outline) {
            int diameter = getResources().getDimensionPixelSize(R.dimen.round_button_diameter);
            outline.setOval(0, 0, diameter, diameter);
        }
    };


}
