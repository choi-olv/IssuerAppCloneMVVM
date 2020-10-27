package jp.co.olv.choi.issuerappclonemvvm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import jp.co.olv.choi.issuerappclonemvvm.realm.LiveRealmData;
import jp.co.olv.choi.issuerappclonemvvm.realm.PayDetail;
import jp.co.olv.choi.issuerappclonemvvm.web.RestApiTask;
import jp.co.olv.choi.issuerappclonemvvm.web.commentsResponse;
import lombok.SneakyThrows;

public class PayDetailRepository {

    // APIより全レコードを再取得
    @SneakyThrows
    public LiveRealmData<PayDetail> getAll(Realm realm, Integer postId) {
        final List<commentsResponse> responses = (List<commentsResponse>) new RestApiTask().execute(postId).get();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // 一旦データをリセットしてから入れる
                realm.deleteAll();
                for (Integer i = 0; i < responses.size(); i++) {
                    commentsResponse response = responses.get(i);
                    realm.copyToRealmOrUpdate(new PayDetail(i, response.content.substring(0, 10), response.id, response.createdAt, response.PostId));
                }
            }
        });

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

        RealmResults<PayDetail>payDetailsFromRealm = realm.where(PayDetail.class).findAll();

        return new LiveRealmData<PayDetail>(payDetailsFromRealm);
    }
}