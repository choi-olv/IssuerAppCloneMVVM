package jp.co.olv.choi.issuerappclonemvvm;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import jp.co.olv.choi.issuerappclonemvvm.realm.LiveRealmData;
import jp.co.olv.choi.issuerappclonemvvm.realm.PayDetail;
import jp.co.olv.choi.issuerappclonemvvm.web.HttpClient;
import jp.co.olv.choi.issuerappclonemvvm.web.RequestApiService;
import jp.co.olv.choi.issuerappclonemvvm.web.commentsResponse;
import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayDetailRepository {

    // APIより支払明細の全データを再取得
    @SneakyThrows
    public LiveRealmData<PayDetail> getAll(Realm realm, Integer postId) {

        RequestApiService apiService = HttpClient.getRequestApiService();
        apiService.getCommentsByPostId(postId).enqueue(new Callback<List<commentsResponse>>() {
            @Override
            public void onResponse(Call<List<commentsResponse>> call, final Response<List<commentsResponse>> responses) {
                // API実行結果をログに出力
                if (responses.isSuccessful()) {
                    Log.d("succeeded", responses.toString());
                } else {
                    Log.e("failed", responses.toString());
                }

                Realm realm = Realm.getDefaultInstance();
                // レスポンスをRealmに保存
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        // 一旦データをリセットしてから入れる
                        realm.deleteAll();
                        for (Integer i = 0; i < responses.body().size(); i++) {
                            commentsResponse response = responses.body().get(i);
                            realm.copyToRealmOrUpdate(new PayDetail(i, response.content.substring(0, 10), response.id, response.createdAt, response.PostId));
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<List<commentsResponse>> call, Throwable t) {
                Log.e("failed", "");
            }
        });

        // Realmに保存したデータをLiveDataとして返却
        RealmResults<PayDetail> payDetailsFromRealm = realm.where(PayDetail.class).findAll();
        return new LiveRealmData<PayDetail>(payDetailsFromRealm);
    }

    // リストに含まれるレコードを削除
    public LiveRealmData<PayDetail> delete(Realm realm, final List<PayDetail> items) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (PayDetail item: items) {
                    RealmResults<PayDetail> payDetailsFromRealm = realm.where(PayDetail.class).equalTo("id", item.getId()).findAll();
                    payDetailsFromRealm.deleteAllFromRealm();
                }
            }
        });

        // Realmに保存したデータをLiveDataとして返却
        RealmResults<PayDetail>payDetailsFromRealm = realm.where(PayDetail.class).findAll();
        return new LiveRealmData<PayDetail>(payDetailsFromRealm);
    }
}