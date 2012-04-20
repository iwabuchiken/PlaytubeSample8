package play.youtube;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;

public class PlaytubeSample8Activity extends ListActivity {

    // 検索キーワードフォーカス変更時リスナー定義
    private OnFocusChangeListener keywordInputOnFocusChangeListener =
        new OnFocusChangeListener() {
            // onFocusChangeメソッド(フォーカス変更時イベント)
            @Override
            public void onFocusChange(View v, boolean isFocused) {
                // 前ページ( < )ボタンと次ページ( > )ボタンオブジェクト取得
                ImageButton ibtnPrev =
                    (ImageButton) findViewById(R.id.ibtn_prev);
                ImageButton ibtnNext =
                    (ImageButton) findViewById(R.id.ibtn_next);
                // 検索キーワードにフォーカスされた場合
                if (isFocused) {
                    // Prev・Nextボタンを非表示
                    ibtnPrev.setVisibility(View.GONE);
                    ibtnNext.setVisibility(View.GONE);
                } else {
                    // Prev・Nextボタンを表示
                    ibtnPrev.setVisibility(View.VISIBLE);
                    ibtnNext.setVisibility(View.VISIBLE);
                }//if (isFocused)
            }//public void onFocusChange(View v, boolean isFocused)
        };
    ///private OnFocusChangeListener keywordInputOnFocusChangeListener	
    
        // 検索ボタンクリックリスナー定義
    private OnClickListener searchBtnOnClickListener = new OnClickListener() {
        // onClickメソッド(ボタンクリック時イベント)
        @Override
        public void onClick(View v) {
            // 検索キーワードを取得
            EditText etKeyword = (EditText) findViewById(R.id.et_keyword);

            // 検索結果を取得
            List<YouTubeVideoItem8> items;// =
//                YouTubeDataUtil8.getInstance().getSearchResult(
//                    etKeyword.getText().toString());
//
//            // 検索結果を表示
//            setSearchResult(items);
        }//public void onClick(View v)
    };//private OnClickListener searchBtnOnClickListener = new OnClickListener()

	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playtube_main8);
        
        // 検索キーワード入力用EditTextにリスナー設定
        EditText etKeyword = (EditText) findViewById(R.id.et_keyword);
        etKeyword.setOnFocusChangeListener(keywordInputOnFocusChangeListener);

        // 検索ボタンにリスナー設定
        ImageButton ibtnSearch = (ImageButton) findViewById(R.id.ibtn_search);
        ibtnSearch.setOnClickListener(searchBtnOnClickListener);

    }//public void onCreate(Bundle savedInstanceState)
}