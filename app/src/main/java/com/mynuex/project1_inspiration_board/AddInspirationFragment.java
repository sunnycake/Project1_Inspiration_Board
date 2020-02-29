package com.mynuex.project1_inspiration_board;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddInspirationFragment extends Fragment {
    // Constant for logging
    private static String TAG = "ADD_INSPIRATION_FRAGMENT";

    private static int REQUEST_CODE_CAPTURE_IMAGE = 0;
    // Member variables
    private Button mCaptureButton;
    private ImageView mImageView;
    private EditText mTitleEditText;
    private EditText mDescriptionEditText;
    private EditText mCategoryEditText;
    private EditText mTagsEditText;
    private Button mSaveButton;
    private String imagePath;

    private InspirationViewModel mInspirationViewModel;
    private InspirationAdapter mInspirationAdapter;
    private List<Inspiration> mInspirationList;

    public interface NewInspirationListener{
        void addedInspiration(Inspiration inspiration);
    }
    private NewInspirationListener mNewInspirationListener;

    public AddInspirationFragment() {
        // Required empty public constructor
    }

    public static AddInspirationFragment newInstance() {
        return new AddInspirationFragment();
    }
    // verify listener has implemented listener else, throws exceptions.
    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        Log.d(TAG, "onAttach");
        if (context instanceof NewInspirationListener){
            mNewInspirationListener = (NewInspirationListener) context;
            Log.i(TAG,"on attach listener set");
        }else {
            throw new RuntimeException(context.toString()+" must implement New Inspiration Listener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate AddInspiration Fragment");
        mInspirationViewModel = new ViewModelProvider(this).get(InspirationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_inspiration, container, false);

        mCaptureButton = view.findViewById(R.id.capture_button);
        mImageView = view.findViewById(R.id.camera_thumbnail_picture);
        mTitleEditText = view.findViewById(R.id.title_EditText);
        mDescriptionEditText = view.findViewById(R.id.description_EditText);
        mCategoryEditText = view.findViewById(R.id.category_EditText);
        mTagsEditText = view.findViewById(R.id.tags_EditText);
        mSaveButton = view.findViewById(R.id.save_button);
        // Click listener to capture image
        mCaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeThumbnailPicture();

            }
        });
        // Click listener to save data
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = mTitleEditText.getText().toString();
                String description = mDescriptionEditText.getText().toString();
                String category = mCategoryEditText.getText().toString().toUpperCase();
                String tags = mTagsEditText.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter a name in order to save inspiration. ", Toast.LENGTH_LONG).show();
                    return;
                }
                Inspiration inspiration = new Inspiration(name, description, category, tags, imagePath);

                mInspirationViewModel.insert(inspiration);
                mNewInspirationListener.addedInspiration(inspiration);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            Bitmap thumbnail = data.getParcelableExtra("data");
            mImageView.setImageBitmap(thumbnail);
            bitmapToBase64(thumbnail);
        }
    }

    // Tip from https://www.thepolyglotdeveloper.com/2015/06/from-bitmap-to-base64-and-back-with-native-android/
    private void bitmapToBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream);
        byte [] byteArray = byteArrayOutputStream.toByteArray();
        imagePath = Base64.encodeToString(byteArray, Base64.DEFAULT);

    }

    private void takeThumbnailPicture() {

        // Implicit Intent to open an app which can take a picture, probably the built-in camera app
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Is there a camera on this device? Can check by asking the intent which Activity it plans
        // to open to handle this request. If Activity is null, no suitable activity was found
        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(pictureIntent, REQUEST_CODE_CAPTURE_IMAGE);
        } else {
            Toast.makeText(getActivity(), "Your device does not have a camera app", Toast.LENGTH_LONG).show();
        }
    }
}
