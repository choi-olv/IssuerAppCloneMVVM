package jp.co.olv.choi.issuerappclonemvvm;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.realm.Realm;
import jp.co.olv.choi.issuerappclonemvvm.realm.LiveRealmData;
import jp.co.olv.choi.issuerappclonemvvm.realm.PayDetail;

public class PayDetailViewModel extends ViewModel {

    private Realm realm = Realm.getDefaultInstance();
    private PayDetailRepository repository = new PayDetailRepository();

    public LiveRealmData<PayDetail> getAll(Integer postId) {
        return repository.getAll(realm, postId);
    }

    public LiveRealmData<PayDetail> delete(List<PayDetail> items) {
        return repository.delete(realm, items);
    }

    @Override
    protected void onCleared() {
        realm.close();
        super.onCleared();
    }
}