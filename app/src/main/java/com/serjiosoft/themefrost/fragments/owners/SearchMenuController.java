package com.serjiosoft.themefrost.fragments.owners;

import android.support.v7.widget.SearchView;
import android.view.Menu;

import com.serjiosoft.themefrost.MainActivity;
import com.serjiosoft.themefrost.R;

/**
 * Created by autoexec on 01.03.2017.
 */

public class SearchMenuController implements SearchView.OnQueryTextListener {

    private MainActivity mainActivity;
    private SearchView searchView;

    public SearchMenuController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        this.mainActivity.sendEvent(newText);
        return true;

    }

    public void initialize(Menu menu) {
       /* if (!menu.hasVisibleItems() || menu.findItem(R.id.action_filter) == null) {
            this.searchView = null;
            return;
        }
        this.searchView = (SearchView) menu.findItem(R.id.action_filter).getActionView();
        this.searchView.setOnQueryTextListener(this);*/
    }

}
