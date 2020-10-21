package jp.co.olv.choi.issuerappclonemvvm;

import android.arch.lifecycle.ViewModel;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;
import lombok.SneakyThrows;

public class PayDetailViewModel extends ViewModel {

    private Realm realm;

    public PayDetailViewModel() {
        realm = Realm.getDefaultInstance();
    }

    @SneakyThrows
    public LiveRealmData<PayDetail> getAll() {
        final List<commentsResponse> responses = (List<commentsResponse>) new RestApiTask().execute().get();

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

    @Override
    protected void onCleared() {
        realm.close();
        super.onCleared();
    }
}
