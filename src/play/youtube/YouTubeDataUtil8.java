package play.youtube;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class YouTubeDataUtil8 {

    // YouTubeDataUtil�C���X�^���X
    private static final YouTubeDataUtil8 INSTANCE = new YouTubeDataUtil8();

    // �Ō�Ɍ��������L�[���[�h
    private static String lastKeyword;

    // �Ō�ɕ\�������������ʂ̃y�[�W
    private static Integer lastPageNum;

    // �Ō�Ɍ�����������
    private static List<YouTubeVideoItem8> lastResultList;

    // �R���X�g���N�^
    private YouTubeDataUtil8() {
        // �������ʂ��i�[���郊�X�g
        lastResultList = new ArrayList<YouTubeVideoItem8>();
    }

    // YouTubeDataUtil�C���X�^���X��Ԃ����\�b�h
    public static YouTubeDataUtil8 getInstance() {
        return INSTANCE;
    }

    // YouTube�������ʂ�1�y�[�W�ڂ����X�g�����ĕԂ����\�b�h
    public List<YouTubeVideoItem8> getSearchResult(String keyword) {
        // �������ʂ�1�y�[�W�ڂ��擾
        lastKeyword = keyword;
        lastPageNum = 1;
        return getSearchResult(keyword, lastPageNum);
    }

    // YouTube�������ʂ��A�O�̃y�[�W�����X�g�����ĕԂ����\�b�h
    public List<YouTubeVideoItem8> getPrevPage() {
        // �������ʂ̑O�y�[�W���擾
        lastPageNum--;
        return getSearchResult(lastKeyword, lastPageNum);
    }

    // YouTube�������ʂ��A���̃y�[�W�����X�g�����ĕԂ����\�b�h
    public List<YouTubeVideoItem8> getNextPage() {
        // �������ʂ̎��y�[�W���擾
        lastPageNum++;
        return getSearchResult(lastKeyword, lastPageNum);
    }

    // �Ō�Ɍ����������ʂ�Ԃ����\�b�h
    public List<YouTubeVideoItem8> getLastResutList() {
        return lastResultList;
    }

    // YouTube�������ʂ����X�g�����ĕԂ����\�b�h
    private List<YouTubeVideoItem8> getSearchResult(String keyword,
        Integer pageNum) {

        // �������ʂ��N���A
        lastResultList.clear();

        try {
            // �����L�[���[�h���܂�HTTP���N�G�X�gURL���擾
            String url = getQueryURL(keyword, pageNum);

            // GET���N�G�X�g�C���X�^���X����
            HttpUriRequest httpGet = new HttpGet(url);

            // �N���C�A���g�C���X�^���X����
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

            // HTTP�ʐM�����X�|���X���擾
            HttpResponse httpResponse = defaultHttpClient.execute(httpGet);

            // HTTP���X�|���X������łȂ��ꍇ
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                // ��̃��X�g��Ԃ�
                return lastResultList;
            }

            // HTTP���X�|���X�̃R���e���c���擾
            HttpEntity entity = httpResponse.getEntity();

            // JSON�t�H�[�}�b�g��HTTP���X�|���X���擾
            JSONObject json = new JSONObject(EntityUtils.toString(entity));

            // ��������("items"�v�f)���擾
            JSONArray jsonArray =
                json.getJSONObject("data").getJSONArray("items");

            // �������ʂ�1�����J��Ԃ�
            for (int i = 0; i < jsonArray.length(); ++i) {
                // ����1���̏����擾
                JSONObject obj = jsonArray.getJSONObject(i);

                // ����^�C�g��
                String title = obj.getString("title");

                // ����URL
                // (���o�C����������̍Đ��p RTSP �X�g���[�~���O URL �ł��B
                // MPEG-4 SP ����i�ő� 176x144�j�� AAC �����ł��B)
                String mpeg4spURL = obj.getJSONObject("content").getString("6");

                // �T���l�C���摜
                String thumbnailURL =
                    obj.getJSONObject("thumbnail").getString("hqDefault");

                // ���惊�X�g�P���𐶐�
                YouTubeVideoItem8 item =
                    new YouTubeVideoItem8(title, mpeg4spURL, thumbnailURL);

                // ���惊�X�g�ɒǉ�
                lastResultList.add(item);
            }

        } catch (JSONException e) {
            Log.w(getClass().getSimpleName(), "JSON�f�[�^�̉�͂Ɏ��s���܂����B", e);
        } catch (IOException e) {
            Log.w(getClass().getSimpleName(), "HTTP���X�|���X�̎擾�Ɏ��s���܂����B", e);
        }

        // �������ʂ�Ԃ�
        return lastResultList;
    }

    // HTTP ���N�G�X�g URL�𐶐����郁�\�b�h
    private String getQueryURL(String keyword, Integer pageNum) {
        StringBuilder sb = new StringBuilder();

        // YouTube gdata Server URL
        sb.append("http://gdata.youtube.com");

        // Video Feed URL
        sb.append("/feeds/api/videos");

        // API�o�[�W�����w��
        sb.append("?v=2");

        // �Ԃ����t�B�[�h�̃t�H�[�}�b�g���w��
        sb.append("&alt=jsonc");

        // ����t�H�[�}�b�g���w��
        // (���o�C����������̍Đ��p RTSP �X�g���[�~���O URL �ł��B
        // MPEG-4 SP ����i�ő� 176x144�j�� AAC �����ł��B)
        sb.append("&format=6");

        // ��������1�y�[�W������̌���
        sb.append("&max-results=10");

        // �������ʎ擾�y�[�W
        sb.append("&start-index=" + pageNum);

        // �����L�[���[�h
        try {
            sb.append("&q=" + URLEncoder.encode(keyword, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            Log.w(getClass().getSimpleName(), "getQueryURL�F�����L�[���[�h�̕ϊ��Ɏ��s���܂����B",
                e);
        }
        return sb.toString();
    }

}
