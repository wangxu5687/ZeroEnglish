package com.jason.zeroenglish;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jason.zeroenglish.adpter.EnglishContentAdapter;
import com.jason.zeroenglish.bean.WordInfo;
import com.jason.zeroenglish.db.DBHelperAssets;
import com.jason.zeroenglish.ui.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements EnglishContentAdapter.SingleWordOnClickListener {

    private static final String TAG = "ZeroEnglish";

    private static final String DATABASE_NAME = "zero_english.db";

    @BindView(R.id.container_recycler_view)
    RecyclerView containerRecyclerView;

    DBHelperAssets mDbHelperAssets;

    private Dialog mWordTranslateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelperAssets = new DBHelperAssets(this, DATABASE_NAME);

        List<String> strings = mDbHelperAssets.queryWordNameByLimitOfPage(1, 300);

        EnglishContentAdapter adapter = new EnglishContentAdapter(this, strings, 30);
        adapter.setSingleWordOnClickListener(this);

        containerRecyclerView.setAdapter(adapter);
        containerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        containerRecyclerView.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL, 35));


    }

    @Override
    public void onWordClick(TextView View, String text) {
        Log.i(TAG, "onWordClick: text = " + text);
        WordInfo wordInfo = mDbHelperAssets.queryWordInfoByWord(text);
        show(wordInfo);
        Log.i(TAG, "onWordClick: text = " + wordInfo.getTranslate());
    }


    private void show(WordInfo wordInfo) {
        if (mWordTranslateDialog == null) {
            initDialog();
        }

        TextView wordTextView = (TextView) mWordTranslateDialog.findViewById(R.id.word_textView);
        TextView phonogramTextView = (TextView) mWordTranslateDialog.findViewById(R.id.phonetic_symbol_textView);
        TextView translateTextView = (TextView) mWordTranslateDialog.findViewById(R.id.word_translate_textView);

        wordTextView.setText(wordInfo.getWord());
        phonogramTextView.setText(wordInfo.getPhonogram());
        translateTextView.setText(wordInfo.getTranslate());

        mWordTranslateDialog.show();

    }


    private void initDialog() {
        View contentView = getLayoutInflater().inflate(R.layout.word_translate_message, null);

        mWordTranslateDialog = new Dialog(this, R.style.BottomDialog);
        mWordTranslateDialog.setContentView(contentView);
        mWordTranslateDialog.setCanceledOnTouchOutside(true);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        mWordTranslateDialog.getWindow().setGravity(Gravity.BOTTOM);
        mWordTranslateDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);

    }


}
