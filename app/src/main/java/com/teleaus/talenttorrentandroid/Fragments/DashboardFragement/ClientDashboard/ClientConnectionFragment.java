package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teleaus.talenttorrentandroid.R;

public class ClientConnectionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_connection, container, false);

        return view;
    }
}