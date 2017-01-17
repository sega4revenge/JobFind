package com.finger.jobfind.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.finger.jobfind.R;
import com.finger.jobfind.pref.PrefManager;

import java.util.ArrayList;

import za.co.riggaroo.materialhelptutorial.TutorialItem;
import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialActivity;


/**
 * Created by sontho on 1/16/2017.
 */

public class IntroActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1234;
    private PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
        }
        else
        {
            startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            finish();
        }


    }
    public void launchHomeScreen() {

            Intent mainAct = new Intent(this, MaterialTutorialActivity.class);
            mainAct.putParcelableArrayListExtra(MaterialTutorialActivity.MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS, getTutorialItems(this));
            startActivityForResult(mainAct, REQUEST_CODE);


    }

    private ArrayList<TutorialItem> getTutorialItems(Context context) {
        TutorialItem tutorialItem1 = new TutorialItem(R.string.slide_1_title_search, R.string.slide_1_african_story_books_subtitle,
                R.color.slide_1, R.drawable.find_slide_1_front, R.drawable.find_slide_1_background);

        TutorialItem tutorialItem2 = new TutorialItem(R.string.slide_2_tile_saving_time, R.string.slide_2_volunteer_professionals_subtitle,
                R.color.slide_2,  R.drawable.find_slide_2_background,  R.drawable.find_slide_2_front);

        TutorialItem tutorialItem3 = new TutorialItem(R.string.slide_3_title_notification, R.string.slide_3_download_and_go_subtitle,
                R.color.slide_3, R.drawable.find_slide_3_background,  R.drawable.find_slide_3_front);

        TutorialItem tutorialItem4 = new TutorialItem(R.string.slide_4_title_start, R.string.slide_4_different_languages_subtitle,
                R.color.slide_4,  R.drawable.find_slide_4_front, R.drawable.find_slide_4_background);

        ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
        tutorialItems.add(tutorialItem1);
        tutorialItems.add(tutorialItem2);
        tutorialItems.add(tutorialItem3);
        tutorialItems.add(tutorialItem4);

        return tutorialItems;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //    super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            prefManager.setFirstTimeLaunch(true);
            startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            finish();

        }
    }


}