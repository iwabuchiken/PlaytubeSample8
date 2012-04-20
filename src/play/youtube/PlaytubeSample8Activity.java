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

    // �����L�[���[�h�t�H�[�J�X�ύX�����X�i�[��`
    private OnFocusChangeListener keywordInputOnFocusChangeListener =
        new OnFocusChangeListener() {
            // onFocusChange���\�b�h(�t�H�[�J�X�ύX���C�x���g)
            @Override
            public void onFocusChange(View v, boolean isFocused) {
                // �O�y�[�W( < )�{�^���Ǝ��y�[�W( > )�{�^���I�u�W�F�N�g�擾
                ImageButton ibtnPrev =
                    (ImageButton) findViewById(R.id.ibtn_prev);
                ImageButton ibtnNext =
                    (ImageButton) findViewById(R.id.ibtn_next);
                // �����L�[���[�h�Ƀt�H�[�J�X���ꂽ�ꍇ
                if (isFocused) {
                    // Prev�ENext�{�^�����\��
                    ibtnPrev.setVisibility(View.GONE);
                    ibtnNext.setVisibility(View.GONE);
                } else {
                    // Prev�ENext�{�^����\��
                    ibtnPrev.setVisibility(View.VISIBLE);
                    ibtnNext.setVisibility(View.VISIBLE);
                }//if (isFocused)
            }//public void onFocusChange(View v, boolean isFocused)
        };
    ///private OnFocusChangeListener keywordInputOnFocusChangeListener	
    
        // �����{�^���N���b�N���X�i�[��`
    private OnClickListener searchBtnOnClickListener = new OnClickListener() {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g)
        @Override
        public void onClick(View v) {
            // �����L�[���[�h���擾
            EditText etKeyword = (EditText) findViewById(R.id.et_keyword);

            // �������ʂ��擾
            List<YouTubeVideoItem8> items;// =
//                YouTubeDataUtil8.getInstance().getSearchResult(
//                    etKeyword.getText().toString());
//
//            // �������ʂ�\��
//            setSearchResult(items);
        }//public void onClick(View v)
    };//private OnClickListener searchBtnOnClickListener = new OnClickListener()

	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playtube_main8);
        
        // �����L�[���[�h���͗pEditText�Ƀ��X�i�[�ݒ�
        EditText etKeyword = (EditText) findViewById(R.id.et_keyword);
        etKeyword.setOnFocusChangeListener(keywordInputOnFocusChangeListener);

        // �����{�^���Ƀ��X�i�[�ݒ�
        ImageButton ibtnSearch = (ImageButton) findViewById(R.id.ibtn_search);
        ibtnSearch.setOnClickListener(searchBtnOnClickListener);

    }//public void onCreate(Bundle savedInstanceState)
}