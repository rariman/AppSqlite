package com.rariman.appsqlite.fragments;


import android.annotation.TargetApi;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import com.rariman.appsqlite.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookListFragment extends Fragment {

    View addButton;

    public BookListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        addButton = view.findViewById(R.id.add_button);
        addButton.setOutlineProvider(addButtonOutlineProvider);
        addButton.setClipToOutline(true);
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
