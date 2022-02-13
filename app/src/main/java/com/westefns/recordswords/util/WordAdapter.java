package com.westefns.recordswords.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.westefns.recordswords.R;
import com.westefns.recordswords.model.PhraseExample;
import com.westefns.recordswords.model.RecordWord;

import java.util.List;

public class WordAdapter extends BaseAdapter {

    private Context context;
    private List<RecordWord> listRecordWord;
    private LayoutInflater layoutInflater;

    public WordAdapter(Context context, List<RecordWord> listRecordWord) {
        this.context = context;
        this.listRecordWord = listRecordWord;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listRecordWord.size();
    }

    @Override
    public Object getItem(int position) {
        return listRecordWord.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listRecordWord.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.activity_word_item, null);

        TextView tvItemWord = convertView.findViewById(R.id.tvItemWord);
        TextView tvItemClassification = convertView.findViewById(R.id.tvItemClassification);

        tvItemWord.setText(listRecordWord.get(position).getWord());
        tvItemClassification.setText(listRecordWord.get(position).getClassification());

        return convertView;
    }
}




