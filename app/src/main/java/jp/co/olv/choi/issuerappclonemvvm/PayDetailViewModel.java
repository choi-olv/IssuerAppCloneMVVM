package jp.co.olv.choi.issuerappclonemvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.List;

import io.realm.Realm;
import jp.co.olv.choi.issuerappclonemvvm.realm.LiveRealmData;
import jp.co.olv.choi.issuerappclonemvvm.realm.PayDetail;

import static android.content.Context.MODE_PRIVATE;

public class PayDetailViewModel extends AndroidViewModel {

    private Realm realm = Realm.getDefaultInstance();
    private PayDetailRepository repository = new PayDetailRepository();

    public PayDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveRealmData<PayDetail> getAll(Integer postId, List<PayDetail> currentSelectedItems) {

        // チェックボックスの選択状態をリセット
        currentSelectedItems.clear();

        // SharedPreferencesに保存されている前回APIで使用したパラメータを取得
        // 前回未実行の時は0
        SharedPreferences pref = getApplication().getSharedPreferences("pref",MODE_PRIVATE);
        Integer lastPostId = pref.getInt("postId",0);

        // APIで使うパラメータが0以外の有効な値の時にはSharedPreferencesに保存
        if (postId != 0) {
            pref.edit().putInt("postId", postId).commit();
        }

        return repository.getAll(realm, lastPostId, postId);
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