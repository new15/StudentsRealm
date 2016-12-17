package com.example.alex.studentsrealm.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alex.studentsrealm.R;
import com.example.alex.studentsrealm.adapters.RealDataAdapter;
import com.example.alex.studentsrealm.realmHelper.RealmInit;
import com.example.alex.studentsrealm.realmHelper.models.StudentRealmObj;

import io.realm.RealmResults;

/**
 * Created by Alex on 15.12.2016.
 */

public class DataBaseRecyclerFragment extends Fragment {
    private static final String TAG = "log";
    RecyclerView recyclerView;
    RealDataAdapter adapter;
    RealmInit realmInit=null;
    RealmResults<StudentRealmObj> studentsList = null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.data_base_recycler_view, null);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu,menu);


        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {



                Log.d(TAG, "onQueryTextSubmit: ");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (searchView.getQuery().length()>0) {
                    RealmResults<StudentRealmObj> studentsListSearch = realmInit
                            .realmGetSearchStudents(searchView.getQuery().toString().toLowerCase());

                    Log.d(TAG, "onQueryTextChange: " + searchView.getQuery().toString());

                    Log.d(TAG, "onQueryTextChange: " + studentsListSearch.size());
                    recyclerView.setAdapter(new RealDataAdapter(studentsListSearch));
                    recyclerView.invalidate();
                } else if (searchView.getQuery().length()==0){

                    recyclerView.setAdapter(new RealDataAdapter(studentsList));
                    recyclerView.invalidate();

                }
                

                return false;
            }

        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





        realmInit = new RealmInit(getContext()); // get new Realm class
        realmInit.realmInit(); // get Realm Init and Realm Config

        // check for Realm database is empty
        if (realmInit.isStudentsEmpty()){
            realmInit.realmFirstSet(); // if database empty, run firstSet for insert default values
            Log.d(TAG, "onViewCreated: is Students empty TRUE -> FirstSet");
        } else {
            Log.d(TAG, "onViewCreated: is StudentsEmpty false -> next");
        }

        studentsList = realmInit.realmGetAllStudents(); // List of Realm Results for RV adapter


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.data_base_recycler_view_id);
        adapter = new RealDataAdapter(studentsList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realmInit.closeAllRealm();
        realmInit=null;
    }
}