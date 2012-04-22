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

	// �C���e���g�̃f�[�^�󂯓n���L�[��`
    // YouTube����URL
    static enum IntentKey {
        MEDIA_URL, MEDIA_TITLE
    };

    // ��ԕێ��p�L�[��`
    private static enum BundleKey {
        SEARCH_KEYWORD
    };

    // �����L�[���[�h�t�H�[�J�X�ύX�����X�i�[��`
    private OnFocusChangeListener keywordInputOnFocusChangeListener =
        new OnFocusChangeListener() {
            // onFocusChange���\�b�h(�t�H�[�J�X�ύX���C�x���g)
//            @Override
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
            List<YouTubeVideoItem8> items =
                YouTubeDataUtil8.getInstance().getSearchResult(
                    etKeyword.getText().toString());
            
            // toast
//			Toast.makeText(PlaytubeSample8Activity.this, String.valueOf(items.size()), Toast.LENGTH_SHORT).show();
			

            // �������ʂ�\��
            setSearchResult(items);
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

        // �O�y�[�W( < )�{�^���Ƀ��X�i�[�ݒ�
        ImageButton ibtnPrev = (ImageButton) findViewById(R.id.ibtn_prev);
        ibtnPrev.setOnClickListener(prevBtnOnClickListener);

        // ���y�[�W( > )�{�^���Ƀ��X�i�[�ݒ�
        ImageButton ibtnNext = (ImageButton) findViewById(R.id.ibtn_next);
        ibtnNext.setOnClickListener(nextBtnOnClickListener);

    }//public void onCreate(Bundle savedInstanceState)
    
    // setSearchResult���\�b�h�i�������ʂ̃��X�g�ݒ菈��)
    private void setSearchResult(List<YouTubeVideoItem8> items) {

        // �A�_�v�^�N���X�̃C���X�^���X����
        ListAdapter adapter =
            new VideoListAdapter8(this, R.layout.playtube_main8, items);

        // �A�_�v�^�ݒ�
        setListAdapter(adapter);

        // ���惊�X�g�փt�H�[�J�X
        getListView().requestFocus();
    }//private void setSearchResult(List<YouTubeVideoItem8> items)

    // �O�y�[�W( < )�{�^���{�^���N���b�N���X�i�[��`
    private OnClickListener prevBtnOnClickListener = new OnClickListener() {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g)
        @Override
        public void onClick(View v) {

            // �������ʂ̑O�̃y�[�W���擾
            List<YouTubeVideoItem8> items =
                YouTubeDataUtil8.getInstance().getPrevPage();

            // �������ʂ�\��
            setSearchResult(items);
        }
    };//private OnClickListener prevBtnOnClickListener = new OnClickListener()

    // ���y�[�W( > )�{�^���{�^���N���b�N���X�i�[��`
    private OnClickListener nextBtnOnClickListener = new OnClickListener() {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g)
        @Override
        public void onClick(View v) {

            // �������ʂ̎��̃y�[�W���擾
            List<YouTubeVideoItem8> items =
                YouTubeDataUtil8.getInstance().getNextPage();

            // �������ʂ�\��
            setSearchResult(items);
        }
    };//private OnClickListener nextBtnOnClickListener = new OnClickListener()

    // onListItemClick���\�b�h(���惊�X�g���1���I������)
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        // �N���b�N��������̃A�C�e�����擾
        YouTubeVideoItem8 item =
            (YouTubeVideoItem8) l.getItemAtPosition(position);
        
        //debug
        // toast
//		Toast.makeText(PlaytubeSample8Activity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
		

//        // YouTube����Đ�Activity���N��
        Intent intent = new Intent(this, VideoPlayerActivity8.class);

        // �C���e���g�p�����[�^�ɍĐ��Ώۓ����URL��ݒ�
        intent.putExtra(IntentKey.MEDIA_URL.name(), item.getMpeg4spURL());

        // �C���e���g�p�����[�^�ɓ���^�C�g����ݒ�
        intent.putExtra(IntentKey.MEDIA_TITLE.name(), item.getTitle());

        // Activity��\��
        startActivity(intent);
    }

    // onRestoreInstanceState���\�b�h�i��ԕ��A�����j
    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        // �����L�[���[�h���擾
        String keyword = state.getString(BundleKey.SEARCH_KEYWORD.name());

        // �����L�[���[�h��EditText�ɐݒ�
        EditText etKeyword = (EditText) findViewById(R.id.et_keyword);
        etKeyword.setText(keyword);

        // �������ʂ��擾
        List<YouTubeVideoItem8> resultList =
            YouTubeDataUtil8.getInstance().getLastResutList();

        // ���惊�X�g��\��
        setSearchResult(resultList);
    }//protected void onRestoreInstanceState(Bundle state)

    // onSaveInstanceState���\�b�h�i��ԕێ�����)
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // �����L�[���[�hEditText�擾
        EditText etKeyword = (EditText) findViewById(R.id.et_keyword);

        // �����L�[���[�h��ۑ�
        outState.putString(BundleKey.SEARCH_KEYWORD.name(), etKeyword.getText()
            .toString());
    }//protected void onSaveInstanceState(Bundle outState)

}