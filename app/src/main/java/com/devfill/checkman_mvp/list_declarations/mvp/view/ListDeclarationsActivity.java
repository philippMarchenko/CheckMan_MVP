package com.devfill.checkman_mvp.list_declarations.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.devfill.checkman_mvp.R;
import com.devfill.checkman_mvp.list_declarations.mvp.ListDeclarationsContract;
import com.devfill.checkman_mvp.list_declarations.mvp.ListDeclarationsPresenter;
import com.devfill.checkman_mvp.list_declarations.mvp.view.helper.SavedFragment;
import com.devfill.checkman_mvp.model_data.Declarations;
import com.devfill.checkman_mvp.util.ObjectSerializer;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListDeclarationsActivity extends AppCompatActivity implements ListDeclarationsContract.View,
        MaterialSearchBar.OnSearchActionListener,
        SuggestionsAdapter.OnItemViewClickListener,
        DeclarationsAdapter.IDeclarationsAdapterListener{

    @BindView(R.id.searchBar)
    MaterialSearchBar materialSearchBar;

    @BindView(R.id.recycler_view_declarations)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    ListDeclarationsPresenter listDeclarationsPresenter;
    private DeclarationsAdapter declarationsAdapter;
    private List<Declarations.Item> declarationsList = new ArrayList<>();
    private ArrayList<String> suggestionList = new ArrayList<String>();

    private String LOG_TAG = "ListDeclarationsActivity";

    private SavedFragment savedFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        listDeclarationsPresenter = new ListDeclarationsPresenter(getBaseContext());

        savedFragment = (SavedFragment) getSupportFragmentManager().findFragmentByTag("SAVE_FRAGMENT");

        if (savedFragment != null){
            declarationsList = savedFragment.getDeclarations();
        }
        else{
            savedFragment = new SavedFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(savedFragment, "SAVE_FRAGMENT")
                    .commit();
        }

        progressBar.setVisibility(View.INVISIBLE);

        materialSearchBar.setOnSearchActionListener(this);
        materialSearchBar.setSuggstionsClickListener(this);

        restoreSuggestionList();

        materialSearchBar.setLastSuggestions(suggestionList);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_declarations);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getBaseContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        declarationsAdapter = new DeclarationsAdapter(getBaseContext(),this,declarationsList,this);
        recyclerView.setAdapter(declarationsAdapter);

        // attach view to presenter
        listDeclarationsPresenter.attachView(this);

        // view is ready to work
        listDeclarationsPresenter.viewIsReady();
    }


    @Override
    public void showListDeclarations(Declarations declarations) {

        progressBar.setVisibility(View.INVISIBLE);
        declarationsList.clear();
        declarationsList.addAll(declarations.getItems());
        declarationsAdapter.notifyDataSetChanged();
        materialSearchBar.disableSearch();

    }

    @Override
    public void hideDownloadMode() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showDeclarationActivity(String name) {
        Intent intent = new Intent(getBaseContext(), DeclarationActivity.class);
        intent.putExtra("name",name);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getBaseContext().startActivity(intent);
    }

    @Override
    public void showMessage(int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        Log.d(LOG_TAG,"onSearchConfirmed ");

        suggestionList.add(text.toString());
        progressBar.setVisibility(View.VISIBLE);                        //покажем прогресбар
        materialSearchBar.disableSearch();
        listDeclarationsPresenter.getDeclarations(text.toString());
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }

    @Override
    public void OnItemClickListener(int position, View v) {

        recyclerView.setVisibility(View.INVISIBLE);                     //прячем список
        progressBar.setVisibility(View.VISIBLE);                        //покажем прогрусбар
        materialSearchBar.disableSearch();

        listDeclarationsPresenter.getDeclarations(suggestionList.get(position));              //запрос на сервер
    }

    @Override
    public void OnItemDeleteListener(int position, View v) {

    }

    @Override
    public void onClickItemListDeclarations(int position) {

        recyclerView.setVisibility(View.INVISIBLE);                     //прячем список
        progressBar.setVisibility(View.VISIBLE);                        //покажем прогрусбар

        listDeclarationsPresenter.onClickItemDeclarations(position);

    }

    private void restoreSuggestionList () {

        SharedPreferences prefs = getSharedPreferences("suggestionList", Context.MODE_PRIVATE);
        try {
            suggestionList = (ArrayList<String>) ObjectSerializer.deserialize(prefs.getString("LIST", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.i(LOG_TAG, "MainFragment onPause");


        savedFragment.setDeclarations(declarationsList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        SharedPreferences prefs = getSharedPreferences("suggestionList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            editor.putString("LIST", ObjectSerializer.serialize(suggestionList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();

        Log.i(LOG_TAG, "MainFragment onDestroy");



    }
}

