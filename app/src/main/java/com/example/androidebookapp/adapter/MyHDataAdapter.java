package com.example.androidebookapp.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.example.androidebookapp.R;
import com.example.androidebookapp.databinding.HdataAdapterBinding;
import com.example.androidebookapp.item.BookSubCategoryList;
import com.example.androidebookapp.util.Method;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MyHDataAdapter extends RecyclerView.Adapter<MyHDataAdapter.MyViewHolder> {


    //private static final String GlobalVariables.TAG="RecyclerAdapter";
   public ArrayList<BookSubCategoryList> myId_data;
    public final Context context;
    public HdataAdapterBinding binding;
    private SharedPreferences preferences, preferences1;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Method method;
    AlertDialog alertDialog;
    LayoutInflater layoutInflater;
    private Fragment IdsFragment;
    private int columnWidth;

    // int count=0;
    public MyHDataAdapter(ArrayList<BookSubCategoryList> myId_data, Activity context) {
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
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.hdata_adapter, parent, false);
        method = new Method((Activity) parent.getContext());
       /* preferences = parent.getContext().getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        IdsFragment=new IdsFragment();*/
        return new MyViewHolder(binding);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      /*  final IDD_Data myIdData = myId_data.get(position);
        holder.binding.titletxt.setText(myIdData.getTradeName());
        holder.binding.tearntxt.setText("Earn Rs "+myIdData.getTotalEarning());

        if (!myIdData.getTradeImg().equals("")) {
            Log.d("stockearn", "onBindViewHolder: " + myIdData.getTradeImg());
            Glide.with(context)
                    .load(myIdData.getTradeImg())
                    .placeholder(R.drawable.round_bg)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.binding.idImage);

        }

        holder.binding.idImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  method.copyToclipboard1(context,holder.binding.idName);


               *//* ((FragmentActivity)context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, new IdsFragment())
                                .commit();*//*

                FragmentTransaction transaction =  ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_activity_main, IdsFragment,
                        IdsFragment.getClass().getSimpleName());
                transaction.addToBackStack(null);
                transaction.commit();

             //   activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, IdsFragment).addToBackStack(null).commit();



            }
        });*/


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

        HdataAdapterBinding binding;

        public MyViewHolder(HdataAdapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
