package com.finger.jobfind.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.login.LoginManager;
import com.finger.jobfind.R;
import com.finger.jobfind.other.CircleTransform;
import com.finger.jobfind.pref.SessionManager;

import java.util.HashMap;


public class SettingActivity extends Fragment {

    private Toolbar toolbar;
    private LinearLayout lnInfo, lnChangePass,lnexit, lnabout;
    private SessionManager session;
    private String emailpref, namepref, logopref, logo="", logo1 ="";;
    private ImageView imAccount;
    private TextView tvName,tvMail;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_setting, container, false);

        lnInfo = (LinearLayout) view.findViewById(R.id.lnInfo);
        lnChangePass = (LinearLayout) view.findViewById(R.id.lnChangePass);
        lnexit = (LinearLayout) view.findViewById(R.id.lnexit);
        lnabout = (LinearLayout) view.findViewById(R.id.lnabout);
        imAccount = (ImageView) view.findViewById(R.id.imAccount);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvMail= (TextView) view.findViewById(R.id.tvMail);
        session = new SessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        emailpref = user.get(SessionManager.KEY_EMAIL);
        namepref= user.get(SessionManager.KEY_NAME);
        logo = user.get(SessionManager.KEY_LOGO);
        if(logo=="")
        {
            logo=MainActivity.logopref;
        }
        loadImage();
        event();

        lnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), InfoActivity.class));
            }
        });
        lnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
                LoginManager.getInstance().logOut();
                Intent s= new Intent(getActivity(), LoginActivity.class);
                s.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(s);
                getActivity().finish();

            }
        });

        lnabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
            }
        });
        return view;
    }

    private void event() {
        lnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s= new Intent(getActivity(), ChangePasswordActivity.class);
                s.putExtra("logo",logo);
                startActivity(s);
            }
        });
    }

    private void loadImage() {

        tvName.setText(namepref);
        tvMail.setText(emailpref);
        if(logo.equals("")) {
            Glide.with(getActivity()).load(R.drawable.user_icon)
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(getActivity()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imAccount);
        }else {
            Glide.with(getActivity()).load(logo)
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(getActivity()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imAccount);
        }


    }

}
