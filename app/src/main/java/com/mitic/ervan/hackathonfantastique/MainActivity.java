package com.mitic.ervan.hackathonfantastique;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gestevent:
                gererEvent();
                return true;
            case R.id.creerparc:
                createParcours();
                return true;
            case R.id.searchpar:
                searchParcours();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void gererEvent(){

    }
    private void  createParcours(){

    }
    private void searchParcours(){

    }
}
