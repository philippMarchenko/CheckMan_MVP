package com.devfill.checkman_mvp.list_declarations.mvp.view.helper;

import android.os.Bundle;
import android.support.v4.app.Fragment;


import com.devfill.checkman_mvp.model_data.Declarations;

import java.util.List;

public class SavedFragment extends Fragment {

    private List<Declarations.Item> declarationsList;


    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setRetainInstance(true);
    }

    public List<Declarations.Item> getDeclarations() {
        return declarationsList;
    }

    public void setDeclarations(List<Declarations.Item> declarationsList) {
        this.declarationsList = declarationsList;
    }
}