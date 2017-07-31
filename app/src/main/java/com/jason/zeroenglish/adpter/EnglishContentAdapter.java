package com.jason.zeroenglish.adpter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jason.zeroenglish.R;
import com.jason.zeroenglish.ui.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wx on 2017/7/27.
 */

public class EnglishContentAdapter extends RecyclerView.Adapter<EnglishContentAdapter.ViewHolder> {

    private static final String TAG = EnglishContentAdapter.class.getSimpleName();

    private static final String DATABASE_NAME = "english_word.db";

    private Context mContext;

    private List<List<String>> mDataLists;

    private SingleWordOnClickListener mListener;

    public EnglishContentAdapter(Context context, List<String> data, int eachRowCount) {
        this.mContext = context;

        mDataLists = initData(data, eachRowCount);

    }

    private List<List<String>> initData(List<String> data, int eachRowCount) {
        List<List<String>> lists = new ArrayList<>();

        int dataSize = data.size();
        int rowCount = dataSize / eachRowCount;
        if (dataSize % eachRowCount != 0) {
            rowCount++;
        }
        for (int i = 0; i < rowCount; i++) {
            int fromIndex = i * eachRowCount;
            int toIndex = (i + 1) * eachRowCount;
            if (toIndex > dataSize) {
                toIndex = dataSize;
            }

            lists.add(data.subList(fromIndex, toIndex));

        }

        return lists;
    }

    public void setSingleWordOnClickListener(SingleWordOnClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.english_content_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.contentItemNumber.setText(String.valueOf(position + 1));

        EnglishCardAdapter cardAdapter = (EnglishCardAdapter) holder.contentRecyclerView.getAdapter();

        if (cardAdapter == null) {
            cardAdapter = new EnglishCardAdapter(mContext, mDataLists.get(position));
            holder.contentRecyclerView.setAdapter(cardAdapter);
        } else {
            cardAdapter.setEnglishWords(mDataLists.get(position));
        }

        if (cardAdapter.getCardWordOnClickListener() == null) {
            cardAdapter.setCardWordOnClickListener(new EnglishCardAdapter.CardWordOnClickListener() {
                @Override
                public void onClick(TextView view, String text) {
                    if (mListener != null) {
                        mListener.onWordClick(view, text);
                    }
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mDataLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.content_item_number)
        TextView contentItemNumber;
        @BindView(R.id.content_item_recycle_view)
        RecyclerView contentRecyclerView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            contentRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            contentRecyclerView.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.HORIZONTAL, 30));
        }
    }

    public interface SingleWordOnClickListener {
        void onWordClick(TextView View, String text);
    }

}
