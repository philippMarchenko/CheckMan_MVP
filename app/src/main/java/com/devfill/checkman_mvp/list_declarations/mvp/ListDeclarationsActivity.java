package com.devfill.checkman_mvp.list_declarations.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.devfill.checkman_mvp.R;
import com.devfill.checkman_mvp.app.App;
import com.devfill.checkman_mvp.list_declarations.dagger.ListDeclarationsActivityModule;
import com.devfill.checkman_mvp.model.Declarations;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ListDeclarationsActivity extends AppCompatActivity implements ListDeclarationsContract.View{

    @BindView(R.id.searchBar)
    MaterialSearchBar materialSearchBar;

    @BindView(R.id.recycler_view_declarations)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @Inject
    ListDeclarationsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // inject activity
        App.getApp(this)
                .getComponentsHolder()
                .getActivityComponent(getClass(), new ListDeclarationsActivityModule())
                .inject(this);


        progressBar.setVisibility(View.INVISIBLE);

        // attach view to presenter
        presenter.attachView(this);

        // view is ready to work
        presenter.viewIsReady();
    }


    @Override
    public void showListDeclarations(List<Declarations> declarationsList) {

    }

    @Override
    public void hideListDeclarations() {

    }

    @Override
    public void showMessage(int messageResId) {

    }

    @Override
    public void close() {

    }
}

