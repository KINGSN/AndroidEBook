package com.example.androidebookapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidebookapp.Interface.Helper;
import com.example.androidebookapp.R;
import com.example.androidebookapp.activity.BookDetail;
import com.example.androidebookapp.activity.MainActivity;
import com.example.androidebookapp.activity.SearchBook;
import com.example.androidebookapp.adapter.BookHomeAdapter;
import com.example.androidebookapp.adapter.BookTabAdapter;
import com.example.androidebookapp.adapter.BookhCategoryAdapterr;
import com.example.androidebookapp.adapter.CategoryAdapter;
import com.example.androidebookapp.adapter.HomeAuthorAdapter;
import com.example.androidebookapp.adapter.SliderAdapter;
import com.example.androidebookapp.https.HttpsRequest;
import com.example.androidebookapp.interfaces.OnClick;
import com.example.androidebookapp.item.BookSubCategoryList;
import com.example.androidebookapp.item.BookhSubCategoryList;
import com.example.androidebookapp.item.CategoryList;
import com.example.androidebookapp.response.CatRP;
import com.example.androidebookapp.rest.ApiClient;
import com.example.androidebookapp.rest.ApiInterface;
import com.example.androidebookapp.util.API;
import com.example.androidebookapp.util.EnchantedViewPager;
import com.example.androidebookapp.util.GlobalVariables;
import com.example.androidebookapp.util.Method;
import com.example.androidebookapp.util.RestAPI;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.rd.PageIndicatorView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksTabFragment extends Fragment {


    private Method method;
    private OnClick onClick;
    private ProgressBar progressBar;
    private SliderAdapter sliderAdapter;
    private BookHomeAdapter latestAdapter;
    private BookHomeAdapter popularAdapter;
    private BookHomeAdapter continueAdapter;
    private HomeAuthorAdapter authorAdapter;
    private BookhCategoryAdapterr homeCatAdapter;
    private InputMethodManager imm;
    private TextInputEditText editText_search;
    private PageIndicatorView pageIndicatorView;
    private EnchantedViewPager enchantedViewPager;
    private List<CategoryList> categoryLists;
    private BookTabAdapter categoryAdapter;
    private Boolean isOver = false;
    private int paginationIndex = 1;
    private String adsParam = "1";


    private ArrayList<BookSubCategoryList> my_id_dataArrayList;
    private ConstraintLayout conMain, conNoData, conSlider, conContinue, conCategory, conLatest, conPopular, conAuthor;
    private RecyclerView recyclerViewContinue, recyclerViewLatest, recyclerViewPopular, recyclerViewCat, recyclerViewAuthor;

    private Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    private Boolean isTimerStart = false;
    private final Handler handler = new Handler();
    private Runnable Update;
    SeekBar seekBar1,seekBar2,seekBar3,seekBar4;
    TextView tvOption1,tvOption2,tvOption3,tvOption4;
    TextView tvPercent1,tvPercent2,tvPercent3,tvPercent4;
    double count1=1,count2=1,count3=1,count4=1;
    boolean flag1=true,flag2=true,flag3=true,flag4=true;
    private LayoutAnimationController animation;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.book_home_fragment, container, false);

        if (MainActivity.toolbar != null) {
            MainActivity.toolbar.setTitle(getResources().getString(R.string.home));
        }

        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        int resId = R.anim.layout_animation_fall_down;
        animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);


        method = new Method(requireActivity(), onClick);
        categoryLists = new ArrayList<>();
        progressBar = view.findViewById(R.id.progressBar_home);
        conMain = view.findViewById(R.id.con_main_home);
        conNoData = view.findViewById(R.id.con_noDataFound);
        editText_search = view.findViewById(R.id.editText_home);
        enchantedViewPager = view.findViewById(R.id.slider_home);
        pageIndicatorView = view.findViewById(R.id.pageIndicatorView_home);
        conSlider = view.findViewById(R.id.con_slider_home);
        conContinue = view.findViewById(R.id.con_continue_home);
        conCategory = view.findViewById(R.id.con_category_home);

        ImageView imageViewSearch = view.findViewById(R.id.imageView_search_home);
        MaterialTextView textViewCat = view.findViewById(R.id.textView_categoryViewAll_home);

        recyclerViewCat = view.findViewById(R.id.recyclerView_category_home);




        conNoData.setVisibility(View.GONE);
      // conMain.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        enchantedViewPager.useScale();
        enchantedViewPager.removeAlpha();



        recyclerViewCat.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerCat = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewCat.setLayoutManager(layoutManagerCat);
        recyclerViewCat.setFocusable(false);

      /*  recyclerViewCat.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (homeCatAdapter.getItemViewType(position) == 1) {
                    return 1;
                } else {
                    return 3;
                }
            }
        });
        recyclerViewCat.setLayoutManager(layoutManager);
        recyclerViewCat.setFocusable(false);
*/




        if (method.isNetworkAvailable()) {
            if (method.isLogin()) {
                home(method.userId());
            } else {
               // home("0");
               category();
                //getMyId(requireActivity());
            }
        } else {
            method.alertBox(getResources().getString(R.string.internet_connection));
            progressBar.setVisibility(View.GONE);
        }

        setHasOptionsMenu(true);

        return view;
    }

    private void bookFragment(String type, String title, String id, String subId) {
        BookFragment bookFragment = new BookFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        if (type.equals("home_cat")) {
            bundle.putString("title", title);
            bundle.putString("id", id);
            bundle.putString("subId", subId);
        }
        bookFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_main,
                bookFragment, title).addToBackStack(title).commitAllowingStateLoss();
    }

    private void hideKeyBord() {
        if (requireActivity().getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void search() {
        String search = editText_search.getText().toString();
        //do something
        if (!search.isEmpty() || !search.equals("")) {
            editText_search.clearFocus();
            imm.hideSoftInputFromWindow(editText_search.getWindowToken(), 0);
            startActivity(new Intent(getActivity(), SearchBook.class)
                    .putExtra("id", "0")
                    .putExtra("search", search)
                    .putExtra("type", "normal"));
        } else {
            if (getActivity().getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }
            method.alertBox(getResources().getString(R.string.please_enter_book));
        }

    }

    private void home(String userId) {

        if (getActivity() != null) {

            progressBar.setVisibility(View.VISIBLE);

            JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(getActivity()));
            jsObj.addProperty("user_id", userId);
            jsObj.addProperty("method_name", "get_bookcategory");
            jsObj.addProperty("ads_param", "1");
            Log.d("KINGSN", "jsonobject: "+jsObj);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BookhSubCategoryList> call = apiService.getBookSubCategoryList(API.toBase64(jsObj.toString()));
            Log.d("KINGSNH", "onResponsejb: ");
            call.enqueue(new Callback<BookhSubCategoryList>() {
                @Override
                public void onResponse(@NotNull Call<BookhSubCategoryList> call, @NotNull Response<BookhSubCategoryList> response) {

                    Log.d("KINGSNH", "onResponse: "+response);
                    if (getActivity() != null) {

                        try {

                            BookhSubCategoryList bookhSubCategoryList = response.body();
                            assert bookhSubCategoryList != null;

                            if (bookhSubCategoryList.getStatus().equals("1")) {
                                conCategory.setVisibility(View.VISIBLE);

                                Log.d("KINGSNH" ,"onResponse: "+bookhSubCategoryList.getCategoryLists().size());

                                if (bookhSubCategoryList.getCategoryLists().size()!= 0) {

                                    homeCatAdapter = new BookhCategoryAdapterr(getActivity(), bookhSubCategoryList.getCategoryLists(),0, "category", onClick);
                                    recyclerViewCat.setAdapter(homeCatAdapter);
                                    recyclerViewCat.setLayoutAnimation(animation);

                                    Log.d("KINGSN", "onResponse: "+GlobalVariables.categoryLists.get(0).getCategory_name().toString());
                                } else {
                                    conCategory.setVisibility(View.GONE);
                                }

                               /* if (homeRP.getLatestList().size() != 0) {
                                    latestAdapter = new BookHomeAdapter(getActivity(), homeRP.getLatestList(), "home_latest", onClick);
                                    recyclerViewLatest.setAdapter(latestAdapter);
                                } else {
                                    conLatest.setVisibility(View.GONE);
                                }

                                if (homeRP.getPopularList().size() != 0) {
                                    popularAdapter = new BookHomeAdapter(getActivity(), homeRP.getPopularList(), "home_most", onClick);
                                    recyclerViewPopular.setAdapter(popularAdapter);
                                } else {
                                    conPopular.setVisibility(View.GONE);
                                }

                                if (homeRP.getAuthorLists().size() != 0) {
                                    authorAdapter = new HomeAuthorAdapter(getActivity(), homeRP.getAuthorLists(), "home_author", onClick);
                                    recyclerViewAuthor.setAdapter(authorAdapter);
                                } else {
                                    conAuthor.setVisibility(View.GONE);
                                }
*/
                                conMain.setVisibility(View.VISIBLE);

                            } else if (bookhSubCategoryList.getStatus().equals("2")) {
                                method.suspend(bookhSubCategoryList.getMessage());
                            } else {
                                conNoData.setVisibility(View.VISIBLE);
                                method.alertBox(bookhSubCategoryList.getMessage());
                            }

                        } catch (Exception e) {
                            Log.d("exception_error", e.toString());
                            method.alertBox(getResources().getString(R.string.failed_try_again));
                        }

                    }

                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(@NotNull Call<BookhSubCategoryList> call, @NotNull Throwable t) {
                    // Log error here since request failed
                    Log.e("fail", t.toString());
                    conNoData.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    method.alertBox(getResources().getString(R.string.failed_try_again));
                }
            });

        }
    }


    private void category() {

        if (getActivity() != null) {

            if (categoryAdapter == null) {
                categoryLists.clear();
                progressBar.setVisibility(View.VISIBLE);
            }

            JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(getActivity()));
            jsObj.addProperty("ads_param", adsParam);
            jsObj.addProperty("page", paginationIndex);
            jsObj.addProperty("method_name", "get_category");
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<CatRP> call = apiService.getCategory(API.toBase64(jsObj.toString()));
            call.enqueue(new Callback<CatRP>() {
                @Override
                public void onResponse(@NotNull Call<CatRP> call, @NotNull Response<CatRP> response) {

                    if (getActivity() != null) {
                        conCategory.setVisibility(View.VISIBLE);
                        try {

                            CatRP catRP = response.body();
                            assert catRP != null;

                            if (catRP.getStatus().equals("1")) {

                                adsParam = catRP.getAds_param();

                                if (catRP.getCategoryLists().size() == 0) {
                                    if (categoryAdapter != null) {
                                        categoryAdapter.hideHeader();
                                        isOver = true;
                                    }
                                } else {
                                    categoryLists.addAll(catRP.getCategoryLists());
                                }

                                if (categoryAdapter == null) {
                                    if (categoryLists.size() == 0) {
                                        conNoData.setVisibility(View.VISIBLE);
                                    } else {
                                        categoryAdapter = new BookTabAdapter(getActivity(), categoryLists, "category", onClick);
                                        recyclerViewCat.setAdapter(categoryAdapter);
                                        recyclerViewCat.setLayoutAnimation(animation);

                                       /* homeCatAdapter = new BookhCategoryAdapterr(getActivity(), GlobalVariables.categoryLists,0, "category", onClick);
                                        recyclerViewCat.setAdapter(homeCatAdapter);
                                        recyclerViewCat.setLayoutAnimation(animation);*/
                                    }
                                } else {
                                    categoryAdapter.notifyDataSetChanged();
                                }

                            } else {
                                conNoData.setVisibility(View.VISIBLE);
                                method.alertBox(catRP.getMessage());
                            }

                        } catch (Exception e) {
                            Log.d("exception_error", e.toString());
                            method.alertBox(getResources().getString(R.string.failed_try_again));
                        }

                    }

                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(@NotNull Call<CatRP> call, @NotNull Throwable t) {
                    // Log error here since request failed
                    Log.e("fail", t.toString());
                    progressBar.setVisibility(View.GONE);
                    method.alertBox(getResources().getString(R.string.failed_try_again));
                }
            });

        }
    }

    public void getMyId(Activity activity) {
       // method.loadingDialogg(activity);
        conCategory.setVisibility(View.VISIBLE);
        method.params.clear();
        method.params.put("mobile","" );
        // method.showToasty(activity,"1",""+GlobalVariables.adminUserID);
        Log.d(GlobalVariables.TAG, "getHomeData2: called"+activity.toString());
        new HttpsRequest(RestAPI.get_bookcategory, method.params, activity).stringPost2(GlobalVariables.TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, JSONObject abcdapp, String title, String message, JSONObject response) {
                //  ProjectUtils.pauseProgressDialog();
                //  binding.swipeRefreshLayout.setRefreshing(false);
                if (flag) {
                    // binding.tvNo.setVisibility(View.GONE);

                    conMain.setVisibility(View.VISIBLE);

                    try {
                        //  Log.d(GlobalVariables.TAG, "getIDhk:" + response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").toString());


                       // setData();

                       /* homeCatAdapter = new BookhCategoryAdapterr(getActivity(), GlobalVariables.categoryLists,0, "category", onClick);
                        recyclerViewCat.setAdapter(homeCatAdapter);
                        recyclerViewCat.setLayoutAnimation(animation);
*/
                        for(int i=0;i<GlobalVariables.categoryLists.size();i++){
                            int j=i;
                            // Get current json object

                            //JSONObject ob=array.getJSONObject(i);


                            homeCatAdapter = new BookhCategoryAdapterr(getActivity(), GlobalVariables.categoryLists,0, "category", onClick);
                            recyclerViewCat.setAdapter(homeCatAdapter);
                            recyclerViewCat.setLayoutAnimation(animation);




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
