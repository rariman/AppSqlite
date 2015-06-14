package com.rariman.appsqlite.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.rariman.appsqlite.R;
import com.rariman.appsqlite.database.DatabaseConnector;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewBookFragment extends Fragment implements View.OnKeyListener{

    public interface NewBookFragmentInterface{
        void onAddCompleted();
    }

    private TextInputLayout titleTextInputLayout, descriptionTextInputLayout;
    private EditText titleEditText, descriptionEditText;
    private NewBookFragmentInterface listener;

    public NewBookFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_new_book, container, false);

        titleTextInputLayout = (TextInputLayout)view.findViewById(R.id.newTitleTextInputLayout);
        descriptionTextInputLayout = (TextInputLayout)view.findViewById(R.id.newDescriptionTextInputLayout);

        titleEditText = (EditText)view.findViewById(R.id.newTitleEditText);
        descriptionEditText = (EditText)view.findViewById(R.id.newDescriptionEditText);

        descriptionEditText.setOnKeyListener(this);

        return view;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event)
    {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_ENTER:
                    checkEditTexts();
                    break;
            }
        }

        return true;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (NewBookFragmentInterface) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void checkEditTexts()
    {
        boolean isEmpty = false;
        if (TextUtils.isEmpty(titleEditText.getText().toString().trim()))
        {
            titleTextInputLayout.setError(getResources().getString(R.string.empty_title));
            isEmpty = true;
        }
        else
        {
            titleTextInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(descriptionEditText.getText().toString().trim()))
        {
            descriptionTextInputLayout.setError(getResources().getString(R.string.empty_description));
            isEmpty = true;
        }
        else
        {
            descriptionTextInputLayout.setError(null);
        }

        if (!isEmpty)
        {
            new AddBookTask().execute((Object[]) null);
        }
    }

    private class AddBookTask extends AsyncTask<Object, Object, Long>
    {
        DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());
        String newTitle, newDescription;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            newTitle = titleEditText.getText().toString();
            newDescription = descriptionEditText.getText().toString();
        }

        @Override
        protected Long doInBackground(Object... params) {
            return  databaseConnector.insertNewBook(newTitle, newDescription);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(),0);

            listener.onAddCompleted();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.book_add_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.add_done_item:
                checkEditTexts();
        }

        return super.onOptionsItemSelected(item);
    }
}
