package jp.co.olv.choi.issuerappclonemvvm.realm;

import android.arch.lifecycle.MutableLiveData;

import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;
import lombok.Getter;

// RealmResultsをLiveDataとして扱えるようにするラッパークラス
@Getter
public class LiveRealmData<T extends RealmModel> extends MutableLiveData<RealmResults<T>> {

    private RealmResults<T> results;
    private final RealmChangeListener<RealmResults<T>> listener =
            new RealmChangeListener<RealmResults<T>>() {
                @Override
                public void onChange(RealmResults<T> results) { setValue(results);}
            };

    public LiveRealmData(RealmResults<T> realmResults) {
        results = realmResults;
    }

    @Override
    protected void onActive() {
        results.addChangeListener(listener);
    }

    @Override
    protected void onInactive() {
        results.removeChangeListener(listener);
    }
}