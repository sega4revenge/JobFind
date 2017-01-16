package com.finger.jobfind.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.finger.jobfind.R;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        View aboutPage = new AboutPage(this)


                .isRTL(false)
                .setImage(R.drawable.icon_qj_find_about)
                .addItem(new Element().setTitle("Version 1.1"))
//                .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("sontho4@gmail.com")
                .addFacebook("https://www.facebook.com/groups/727189854084530/")
                .addGitHub("sega4revenge")
                .addWebsite("http://www.finger.co.kr/home/index.jsp")
                .setDescription(getResources().getString(R.string.about_app))
//                .addFacebook("the.medy")
//                .addTwitter("medyo80")
//                .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
//                .addPlayStore("com.ideashower.readitlater.pro")
//                .addInstagram("medyo80")
//                .addGitHub("medyo")
                .addItem(getCopyRightsElement())
                .create();

        setContentView(aboutPage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIcon(R.drawable.about_icon_copy_right);
        copyRightsElement.setColor(ContextCompat.getColor(this, mehdi.sakout.aboutpage.R.color.about_item_icon_color));
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
