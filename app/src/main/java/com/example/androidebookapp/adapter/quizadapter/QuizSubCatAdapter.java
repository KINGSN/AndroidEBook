package com.example.androidebookapp.adapter.quizadapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.androidebookapp.R;
import com.example.androidebookapp.activity.BookSubjectActivity;
import com.example.androidebookapp.databinding.HdataAdapterBinding;
import com.example.androidebookapp.databinding.ItemDailyBoosterBinding;
import com.example.androidebookapp.fragment.bookfragments.SubjectFragment;
import com.example.androidebookapp.item.BookSubCategoryList;
import com.example.androidebookapp.util.Method;

import java.util.ArrayList;


public class QuizSubCatAdapter extends RecyclerView.Adapter<QuizSubCatAdapter.MyViewHolder> {


    //private static final String GlobalVariables.TAG="RecyclerAdapter";
   public ArrayList<BookSubCategoryList> myId_data;
    public final Context context;
    public ItemDailyBoosterBinding binding;
    private SharedPreferences preferences, preferences1;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Method method;
    AlertDialog alertDialog;
    LayoutInflater layoutInflater;
    private SubjectFragment IdsFragment;
    private int columnWidth;

    // int count=0;
    public QuizSubCatAdapter(ArrayList<BookSubCategoryList> myId_data, Activity context) {
        this.myId_data = myId_data;
        this.context = context;

        method = new Method( context);
        Resources r = context.getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, r.getDisplayMetrics());
        columnWidth = (int) ((method.getScreenWidth() - ((5 + 3) * padding)));

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Log.i(GlobalVariables.TAG, "onCreateViewHolder: " + count++);


        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        method = new Method((Activity) parent.getContext());
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_daily_booster, parent, false);
        method = new Method((Activity) parent.getContext());
       /* preferences = parent.getContext().getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        IdsFragment=new IdsFragment();*/
        return new MyViewHolder(binding);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

     //   holder.binding.mainCard.setLayoutParams(new ConstraintLayout.LayoutParams(columnWidth / 3, columnWidth / 3));

        holder.binding.title1.setText(myId_data.get(position).getSubCategoryName());
        Log.d("stockearn", "onBindViewHolder: " + myId_data.get(position).getSubCategoryName());

        if (!myId_data.get(position).getSubCategoryImage().equals("")) {
            Log.d("stockearn", "onBindViewHolder: " + myId_data.get(position).getSubCid());
            Glide.with(context)
                    .load(myId_data.get(position).getSubImageThumb())
                    .dontAnimate()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .encodeFormat(Bitmap.CompressFormat.PNG)
                    .into(holder.binding.icon);



        }



        holder.binding.mainl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  method.copyToclipboard1(context,holder.binding.idName);
                Log.d("KINGSN", "onClick:SID "+myId_data.get(position).getSid());



                Intent intent = new Intent(context, BookSubjectActivity.class);
                intent.putExtra("screenType","QuizSubjectActivity");
                intent.putExtra("Sid", myId_data.get(position).getSid());
                intent.putExtra("SubjectName", myId_data.get(position).getSubCategoryName());
                context.startActivity(intent);

              /*  FragmentTransaction transaction =  ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout_main, IdsFragment,
                        IdsFragment.getClass().getSimpleName());
                transaction.addToBackStack(null);
                transaction.commit();*/

             //   activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, IdsFragment).addToBackStack(null).commit();



            }
        });


    }




   public void open( String url) {
        Uri uri = Uri.parse("googlechrome://navigate?url=" + url);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        if (i.resolveActivity(context.getPackageManager()) == null) {
            i.setData(Uri.parse(url));
        }
        context.startActivity(i);
    }

    public void openDipositIdActivity(){

    }

    @Override
    public int getItemCount() {
        return myId_data.size();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ItemDailyBoosterBinding binding;

        public MyViewHolder(ItemDailyBoosterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
   /* public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public static ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.progressBar_loading);
            progressBar.setVisibility(View.GONE);
        }
    }*/

   /* public class AdOption extends RecyclerView.ViewHolder {

        private ConstraintLayout conAdView;

        public AdOption(View itemView) {
            super(itemView);
            conAdView = itemView.findViewById(R.id.con_adView);
        }
    }*/
   /* public void hideHeader() {
        ProgressViewHolder.progressBar.setVisibility(View.GONE);
    }*/
}
