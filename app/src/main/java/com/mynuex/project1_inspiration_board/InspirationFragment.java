package com.mynuex.project1_inspiration_board;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class InspirationFragment extends Fragment implements InspirationClickListener {
    // Constant for logging
    private static String TAG = "INSPIRATION_FRAGMENT";

    private EditText mSearchText;
    private Button mNewButton;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private InspirationViewModel mInspirationViewModel;
    private InspirationAdapter mInspirationAdapter;

    private List<Inspiration> mInspirationList;

    public interface InspirationFragmentListener {
        void newInspiration();
    }

    private InspirationFragmentListener mListener;

    public InspirationFragment() {
        // Required empty public constructor
    }

    public static InspirationFragment newInstance() {
        return new InspirationFragment();
    }
    // verify listener has implemented listener else, throws exceptions.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d(TAG, "onAttach");

        if(context instanceof InspirationFragmentListener){
            mListener = (InspirationFragmentListener) context;
            Log.d(TAG, "On attach Inspiration Fragment listener set " + mListener);
        } else {
            throw new RuntimeException(context.toString() + " must implement SurveyVoteListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Getting View Model instance and use observe to return live data
        mInspirationViewModel = new ViewModelProvider(this).get(InspirationViewModel.class);
        mInspirationViewModel.getAllInspirations().observe(this, new Observer<List<Inspiration>>() {
            @Override
            public void onChanged(List<Inspiration> inspirations) {
                Log.d(TAG,"Inspiration changed: "+ mInspirationList);
                mInspirationList = inspirations;
                mInspirationAdapter.setInspirationList(inspirations);
                mInspirationAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inspiration, container, false);
        // Set RecyclerView to its corresponding view
        mRecyclerView = view.findViewById(R.id.inspired_list_recycler_view);

        // Set RecyclerView to be Linear layout
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Initialize the adapter and attach it to the RecyclerView
        mInspirationAdapter = new InspirationAdapter(this.getContext(), this);
        mInspirationList = new ArrayList<>();
        mInspirationAdapter.setInspirationList(mInspirationList);
        mRecyclerView.setAdapter(mInspirationAdapter);

        mNewButton = view.findViewById(R.id.new_button);
        mNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.newInspiration();

            }
        });

        mSearchText = view.findViewById(R.id.search_EditText);
        mSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        return view;

    }

    @Override
    public void onListClick(int position) {

        Inspiration inspiration = mInspirationList.get(position);
        Uri locationUri = Uri.parse("geo:0,0?q=" + Uri.encode(inspiration.getTitle())); // Map
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri); // Intent to open map
        startActivity(mapIntent);

    }
    // Method to remove place on longClick
    @Override
    public void onListLongClick(final int position) {

        final Inspiration inspiration = mInspirationList.get(position);

        AlertDialog confirmDeleteDialog = new AlertDialog.Builder(getActivity())
                .setMessage(getString(R.string.delete_place_message, mInspirationList.get(position).getTitle()))
                .setTitle(getString(R.string.delete_dialog_title))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        mInspirationViewModel.delete(inspiration);
                        mInspirationAdapter.notifyItemRemoved(position);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null) // No event handler needed
                .create();
        confirmDeleteDialog.show();

    }
}
