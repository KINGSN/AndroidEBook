package com.example.androidebookapp.fragment.subjectTabFragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidebookapp.Interface.Helper;
import com.example.androidebookapp.R;
import com.example.androidebookapp.adapter.booksdapter.BookSubjectAdapter;
import com.example.androidebookapp.databinding.FragmentSubjectTabBinding;
import com.example.androidebookapp.https.HttpsRequest;
import com.example.androidebookapp.item.BookSubjectList;
import com.example.androidebookapp.util.GlobalVariables;
import com.example.androidebookapp.util.Method;
import com.example.androidebookapp.util.RestAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class subjectTabFragmentHn extends Fragment {

    public FragmentSubjectTabBinding binding;
    public Method method;
    public BookSubjectAdapter bookSubjectAdapter;
    public ArrayList<BookSubjectList> my_id_dataArrayList;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public String Language;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String SubCat;

    public subjectTabFragmentHn(String Lang, String subcategory) {
        // Required empty public constructor
        this.Language=Lang;
        this.SubCat=subcategory;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        method = new Method(getActivity());
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_subject_tab, container, false);

        binding = FragmentSubjectTabBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        getMyId(requireActivity());

        return view;
    }

    public void getMyId(Activity activity) {
        Log.d("KINGSHIN", "getMyId:subjecttabfrag ");
       method.params.clear();
       method.params.put("languageType","1" );
       method.params.put("Subcategory",SubCat );
        // method.showToasty(activity,"1",""+GlobalVariables.adminUserID);
        Log.d(GlobalVariables.TAG, "getHomeData2: called"+activity.toString());
        new HttpsRequest(RestAPI.get_bookSubjects, method.params, activity).stringPost2(GlobalVariables.TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, JSONObject abcdapp, String title, String message, JSONObject response) {
                //  ProjectUtils.pauseProgressDialog();
                //  binding.swipeRefreshLayout.setRefreshing(false);
                if (flag) {
                    // binding.tvNo.setVisibility(View.GONE);



                    try {
                        //  Log.d(GlobalVariables.TAG, "getIDhk:" + response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").toString());


                        my_id_dataArrayList = new ArrayList<>();
                        Type getpetDTO = new TypeToken<List<BookSubjectList>>() {
                        }.getType();


                       // Log.d("KINGSNCategoryN", "backResponse: "+CategoryN);
                        my_id_dataArrayList =new Gson().fromJson(response.getJSONObject(GlobalVariables.AppSid).getJSONArray("subjects").toString(), getpetDTO);

                        //  Log.d("KINGSH", "backResponse: "+my_id_dataArrayList1.get(0).getSubCategoryName());
                        // setData();
                        if (my_id_dataArrayList.size() > 0) {


                            bookSubjectAdapter = new BookSubjectAdapter(activity,my_id_dataArrayList,"eng");
                            RecyclerView.LayoutManager layoutManagerAuthor = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
                            binding.recycleview.setLayoutManager(layoutManagerAuthor);
                            binding.recycleview.setFocusable(false);
                            binding.recycleview.setAdapter(bookSubjectAdapter);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {

                   /* setData();

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireActivity());
                    alertDialogBuilder.setTitle(title);
                    alertDialogBuilder.setMessage(message);
                    alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                    alertDialogBuilder.setPositiveButton(requireActivity().getResources().getString(R.string.ok_message),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    method.loadingDialog.dismiss();
                                    requireActivity().finish();
                                    // startActivity(new Intent(requireActivity(), LoginPageActivity.class));
                                    //Log.d("Response",msg);
                                    // finishAffinity();

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // alertDialog.show();
         */       }
            }
        });
    }


}