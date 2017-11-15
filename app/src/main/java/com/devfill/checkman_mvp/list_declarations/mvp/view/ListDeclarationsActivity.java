package com.devfill.checkman_mvp.list_declarations.mvp.view;

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
import com.devfill.checkman_mvp.model.Declarations;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        listDeclarationsPresenter = new ListDeclarationsPresenter(getBaseContext());



        progressBar.setVisibility(View.INVISIBLE);

        materialSearchBar.setOnSearchActionListener(this);
        materialSearchBar.setSuggstionsClickListener(this);

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
        //recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
       // materialSearchBar.disableSearch();
    }

    @Override
    public void showMessage(int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void close() {

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
}

