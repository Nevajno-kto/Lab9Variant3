package com.example.lab9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Arrays;

public class SoftwareAdapter extends ArrayAdapter<Software> {

    public SoftwareAdapter(Context context, Software ...softwares){
        super(context, android.R.layout.simple_spinner_item, Arrays.asList(softwares));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Software software = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, null);
        }

        String info = software.getName() == "" ? software.getCategories() : "";
        info += software.getName() == "" ? "\t" + software.getSubcategories() : "";
        info += software.getName() != "" ? "\t\t" + software.getName() : "";
        info += software.getVersion() != "" ? " Версия " + software.getVersion() : "";

        ((TextView) convertView.findViewById(android.R.id.text1)).setText(info);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        Software software = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, null);
        }
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(software.getName());
        return convertView;
    }

}
