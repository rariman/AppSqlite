package com.rariman.appsqlite.fragments;


import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Outline;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import com.rariman.appsqlite.R;
import com.rariman.appsqlite.adapters.BookRecyclerAdapter;
import com.rariman.appsqlite.database.DatabaseConnector;
import com.rariman.appsqlite.domain.Book;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookListFragment extends Fragment {

    public interface BookListFragmentInterface{
        void onAddNewBook();
    }

    private View addButton;
    private RecyclerView bookRecyclerView;
    private List<Book> books = new ArrayList<>();
    private BookListFragmentInterface listener;
    //private BookRecyclerAdapter bookRecyclerAdapter;

    public BookListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        books = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView.LayoutManager layoutManager;

        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        addButton = view.findViewById(R.id.add_button);
        addButton.setOutlineProvider(addButtonOutlineProvider);
        addButton.setClipToOutline(true);
        addButton.setOnClickListener(onAddButtonClick);

        bookRecyclerView = (RecyclerView)view.findViewById(R.id.bookRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        bookRecyclerView.setLayoutManager(layoutManager);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateListBook();
    }

    ViewOutlineProvider addButtonOutlineProvider = new ViewOutlineProvider() {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void getOutline(View view, Outline outline) {
            int diameter = getResources().getDimensionPixelSize(R.dimen.round_button_diameter);
            outline.setOval(0, 0, diameter, diameter);
        }
    };

    View.OnClickListener onAddButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            listener.onAddNewBook();
        }
    };

    private void setBooks(List<Book> bookList)
    {
        this.books = bookList;
        BookRecyclerAdapter bookRecyclerAdapter = new BookRecyclerAdapter(books);
        bookRecyclerAdapter.setOnItemClickListener(onBookListItemClick);
        bookRecyclerView.setAdapter(bookRecyclerAdapter);
    }

    BookRecyclerAdapter.onItemClickListener onBookListItemClick = new BookRecyclerAdapter.onItemClickListener() {
        @Override
        public void OnItemClick(View view, int position) {

        }
    };

    public void updateListBook()
    {
        GetBooksTask getBooksTask = new GetBooksTask();
        getBooksTask.execute((Object[]) null);
    }

    private class GetBooksTask extends AsyncTask<Object, Object, List<Book>>
    {
        DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());

        @Override
        protected List<Book> doInBackground(Object... params) {
            databaseConnector.open();
            return databaseConnector.getAllBooks();
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            super.onPostExecute(books);
            setBooks(books);
            databaseConnector.close();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.book_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (BookListFragmentInterface) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement BookListFragmentInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}
