package com.jason.zeroenglish.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jason.zeroenglish.R;
import com.jason.zeroenglish.util.ScreenUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wx on 2017/7/26.
 */

public class EnglishCardAdapter extends RecyclerView.Adapter<EnglishCardAdapter.ViewHolder> {

    private static final String TAG = EnglishCardAdapter.class.getSimpleName();

    private static final int EACH_CARD_WORD_COUNT = 5;

    private Context mContext;
    private List<String> mEnglishWords;

    private int mCardCount;

    private CardWordOnClickListener mListener;

    public EnglishCardAdapter(Context context, List<String> englishWords) {
        this.mContext = context;
        this.mEnglishWords = englishWords;

        this.mCardCount = getCardCount(englishWords.size());

    }

    public void setEnglishWords(List<String> englishWords) {
        this.mEnglishWords = englishWords;
        this.mCardCount = getCardCount(englishWords.size());
        notifyDataSetChanged();
    }

    public void setCardWordOnClickListener(CardWordOnClickListener listener) {
        this.mListener = listener;
    }

    public CardWordOnClickListener getCardWordOnClickListener(){
        return mListener;
    }

    private int getCardCount(int count) {
        int cardCount = count / EACH_CARD_WORD_COUNT;
        if (count % EACH_CARD_WORD_COUNT != 0) {
            cardCount++;
        }
        return cardCount;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.english_card_item, parent, false);

        //Log.e(TAG, "onCreateViewHolder " );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Log.i(TAG, "onBindViewHolder: "+position);
        int wordSize = mEnglishWords.size();

        int index = position * EACH_CARD_WORD_COUNT;
        int index1 = index;
        if (index1 < wordSize) {
            holder.word1.setText(mEnglishWords.get(index1));
        } else {
            holder.word1.setVisibility(View.INVISIBLE);
        }

        int index2 = index + 1;
        if (index2 < wordSize) {
            holder.word2.setText(mEnglishWords.get(index2));
        } else {
            holder.word2.setVisibility(View.INVISIBLE);
        }

        int index3 = index + 2;
        if (index3 < wordSize) {
            holder.word3.setText(mEnglishWords.get(index3));
        } else {
            holder.word3.setVisibility(View.INVISIBLE);
        }

        int index4 = index + 3;
        if (index4 < wordSize) {
            holder.word4.setText(mEnglishWords.get(index4));
        } else {
            holder.word4.setVisibility(View.INVISIBLE);
        }

        int index5 = index + 4;
        if (index5 < wordSize) {
            holder.word5.setText(mEnglishWords.get(index5));
        } else {
            holder.word5.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mCardCount;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.word1)
        TextView word1;
        @BindView(R.id.word2)
        TextView word2;
        @BindView(R.id.word3)
        TextView word3;
        @BindView(R.id.word4)
        TextView word4;
        @BindView(R.id.word5)
        TextView word5;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.width = ScreenUtil.getScreenWidth(mContext) / 2;

        }

        @OnClick({R.id.word1, R.id.word2, R.id.word3, R.id.word4, R.id.word5})
        public void onClicked(TextView view) {
            switch (view.getId()) {
                case R.id.word1:
                    Log.e(TAG, "onClicked: word1");
                    break;
                case R.id.word2:
                    Log.e(TAG, "onClicked: word2");
                    break;
                case R.id.word3:
                    Log.e(TAG, "onClicked: word3");
                    break;
                case R.id.word4:
                    Log.e(TAG, "onClicked: word4");
                    break;
                case R.id.word5:
                    Log.e(TAG, "onClicked: word5");
                    break;
            }
            if (mListener != null) {
                mListener.onClick(view, view.getText().toString());
            }
        }

    }

    public interface CardWordOnClickListener {
        void onClick(TextView view, String text);
    }

}
