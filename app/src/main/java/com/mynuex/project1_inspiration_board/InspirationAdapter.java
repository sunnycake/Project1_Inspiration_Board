package com.mynuex.project1_inspiration_board;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

// Adapter creates and binds ViewHolders.
// Holds description & priority of a task to RecyclerView to efficiently display data
public class InspirationAdapter extends RecyclerView.Adapter<InspirationAdapter.InspirationViewHolder> {
    // Class variable to hold list
    private List<Inspiration> mInspirationList;
    private InspirationClickListener mInspirationClickListener;

    public InspirationAdapter(Context context, InspirationClickListener inspirationClickListener) {
        this.mInspirationClickListener = inspirationClickListener;

    }
    // Method from fragment to notify of changes
    // Then notifies the adapter to use new values
    void setInspirationList(List<Inspiration> inspirationList){
        this.mInspirationList = inspirationList;
        notifyDataSetChanged();
    }

    public class InspirationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView mImageView;
        private TextView mTitleView;
        private TextView mDescriptionView;
        private TextView mCategoryView;
        private TextView mDateCreatedView;

        InspirationClickListener mListener;

        public InspirationViewHolder(@NonNull View itemView, InspirationClickListener listener) {
            super(itemView);
            this.mListener = listener;

            mImageView = itemView.findViewById(R.id.imageView);
            mTitleView = itemView.findViewById(R.id.titleTextView);
            mDescriptionView = itemView.findViewById(R.id.descriptionTextView);
            mCategoryView = itemView.findViewById(R.id.categoryTextView);
            mDateCreatedView = itemView.findViewById(R.id.dataCreatedTextView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View view) {
            mListener.onListClick(getAdapterPosition());

        }

        @Override
        public boolean onLongClick(View view) {
            mListener.onListLongClick(getAdapterPosition());
            return true;
        }
    }

    @NonNull
    @Override
    public InspirationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.inspiration_element, parent, false);
        // Create a new viewHolder, to contain this TextView
        return new InspirationViewHolder(viewItem, mInspirationClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InspirationViewHolder holder, int position) {
        // Configures a ViewHolder to display the data for the given position
        // In Android terminology, bind the view and it's data
        Inspiration inspiration = mInspirationList.get(position);
        holder.mTitleView.setText(inspiration.getTitle());
        holder.mDescriptionView.setText(inspiration.getDescription());
        holder.mCategoryView.setText("Category: " + inspiration.getCategory());
        holder.mDateCreatedView.setText("Created: " + inspiration.getDate());
        Bitmap bitmap = bitMapToString(inspiration.getImagePath());
        if (bitmap !=null) {
            holder.mImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return mInspirationList.size();
    }
    // Tip from https://www.thepolyglotdeveloper.com/2015/06/from-bitmap-to-base64-and-back-with-native-android/
    private Bitmap bitMapToString(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

    }
}