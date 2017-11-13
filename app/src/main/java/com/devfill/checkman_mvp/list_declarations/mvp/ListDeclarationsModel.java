package com.devfill.checkman_mvp.list_declarations.mvp;


import com.devfill.checkman_mvp.model.Declarations;
import com.devfill.checkman_mvp.storage.Preferences;

import java.util.ArrayList;
import java.util.List;

public class ListDeclarationsModel implements ListDeclarationsContract.Model{

    private final Preferences preferences;


    public ListDeclarationsModel(Preferences preferences){

        this.preferences = preferences;
    }

    @Override
    public ArrayList<String> getSuggestionList() {
        return null;
    }

    @Override
    public List<Declarations> getDeclarations(String name) {
        return null;
    }
}