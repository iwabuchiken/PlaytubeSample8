package play.youtube;

import java.util.List;

//import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener; 
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

public class PlaytubeSample8Activity extends ListActivity {

	// インテントのデータ受け渡しキー定義
    // YouTube動画URL
    static enum IntentKey {
        MEDIA_URL, MEDIA_TITLE
    };

    // 状態保持用キー定義
    private static enum BundleKey {
        SEARCH_KEYWORD
    };

    // 検索キーワードフォーカス変更時リスナー定義
    private OnFocusChangeListener keywordInputOnFocusChangeListener =
        new OnFocusChangeListener() {
            // onFocusChangeメソッド(フォーカス変更時イベント)
//            @Override
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
            List<YouTubeVideoItem8> items =
                YouTubeDataUtil8.getInstance().getSearchResult(
                    etKeyword.getText().toString());
            
            // toast
//			Toast.makeText(PlaytubeSample8Activity.this, String.valueOf(items.size()), Toast.LENGTH_SHORT).show();
			

            // 検索結果を表示
            setSearchResult(items);
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

        // 前ページ( < )ボタンにリスナー設定
        ImageButton ibtnPrev = (ImageButton) findViewById(R.id.ibtn_prev);
        ibtnPrev.setOnClickListener(prevBtnOnClickListener);

        // 次ページ( > )ボタンにリスナー設定
        ImageButton ibtnNext = (ImageButton) findViewById(R.id.ibtn_next);
        ibtnNext.setOnClickListener(nextBtnOnClickListener);

    }//public void onCreate(Bundle savedInstanceState)
    
    // setSearchResultメソッド（検索結果のリスト設定処理)
    private void setSearchResult(List<YouTubeVideoItem8> items) {

        // アダプタクラスのインスタンス生成
        ListAdapter adapter =
            new VideoListAdapter8(this, R.layout.playtube_main8, items);

        // アダプタ設定
        setListAdapter(adapter);

        // 動画リストへフォーカス
        getListView().requestFocus();
    }//private void setSearchResult(List<YouTubeVideoItem8> items)

    // 前ページ( < )ボタンボタンクリックリスナー定義
    private OnClickListener prevBtnOnClickListener = new OnClickListener() {
        // onClickメソッド(ボタンクリック時イベント)
        @Override
        public void onClick(View v) {

            // 検索結果の前のページを取得
            List<YouTubeVideoItem8> items =
                YouTubeDataUtil8.getInstance().getPrevPage();

            // 検索結果を表示
            setSearchResult(items);
        }
    };//private OnClickListener prevBtnOnClickListener = new OnClickListener()

    // 次ページ( > )ボタンボタンクリックリスナー定義
    private OnClickListener nextBtnOnClickListener = new OnClickListener() {
        // onClickメソッド(ボタンクリック時イベント)
        @Override
        public void onClick(View v) {

            // 検索結果の次のページを取得
            List<YouTubeVideoItem8> items =
                YouTubeDataUtil8.getInstance().getNextPage();

            // 検索結果を表示
            setSearchResult(items);
        }
    };//private OnClickListener nextBtnOnClickListener = new OnClickListener()

    // onListItemClickメソッド(動画リストより1件選択処理)
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        // クリックした動画のアイテムを取得
        YouTubeVideoItem8 item =
            (YouTubeVideoItem8) l.getItemAtPosition(position);
        
        //debug
        // toast
//		Toast.makeText(PlaytubeSample8Activity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
		

//        // YouTube動画再生Activityを起動
        Intent intent = new Intent(this, VideoPlayerActivity8.class);

        // インテントパラメータに再生対象動画のURLを設定
        intent.putExtra(IntentKey.MEDIA_URL.name(), item.getMpeg4spURL());

        // インテントパラメータに動画タイトルを設定
        intent.putExtra(IntentKey.MEDIA_TITLE.name(), item.getTitle());

        // Activityを表示
        startActivity(intent);
    }

    // onRestoreInstanceStateメソッド（状態復帰処理）
    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        // 検索キーワードを取得
        String keyword = state.getString(BundleKey.SEARCH_KEYWORD.name());

        // 検索キーワードをEditTextに設定
        EditText etKeyword = (EditText) findViewById(R.id.et_keyword);
        etKeyword.setText(keyword);

        // 検索結果を取得
        List<YouTubeVideoItem8> resultList =
            YouTubeDataUtil8.getInstance().getLastResutList();

        // 動画リストを表示
        setSearchResult(resultList);
    }//protected void onRestoreInstanceState(Bundle state)

    // onSaveInstanceStateメソッド（状態保持処理)
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // 検索キーワードEditText取得
        EditText etKeyword = (EditText) findViewById(R.id.et_keyword);

        // 検索キーワードを保存
        outState.putString(BundleKey.SEARCH_KEYWORD.name(), etKeyword.getText()
            .toString());
    }//protected void onSaveInstanceState(Bundle outState)

}