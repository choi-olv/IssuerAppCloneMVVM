package jp.co.olv.choi.issuerappclonemvvm;

import android.arch.lifecycle.ViewModel;

import io.realm.Realm;
import jp.co.olv.choi.issuerappclonemvvm.realm.LiveRealmData;
import jp.co.olv.choi.issuerappclonemvvm.realm.PayDetail;

public class PayDetailViewModel extends ViewModel {

    private Realm realm;
    private PayDetailRepository repository = new PayDetailRepository();

    public PayDetailViewModel() {
        realm = Realm.getDefaultInstance();
    }

    public LiveRealmData<PayDetail> getAll(Integer postId) {
        return repository.getAll(realm, postId);
    }

    public LiveRealmData<PayDetail> delete(int position) {
        return repository.delete(realm, position);
    }

    @Override
    protected void onCleared() {
        realm.close();
        super.onCleared();
    }
}
