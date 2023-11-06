package com.example.findmyclassmates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ClassSelectionAdapter extends ArrayAdapter<ClassModel> {

    private Context context;
    private List<ClassModel> classList;

    public ClassSelectionAdapter(Context context, List<ClassModel> classList) {
        super(context, 0, classList);
        this.context = context;
        this.classList = classList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_class_selection, parent, false);
        }

        ClassModel classModel = classList.get(position);

        TextView classNameTextView = convertView.findViewById(R.id.classNameTextView);

        classNameTextView.setText(classModel.getClassName());

        return convertView;
    }
}
