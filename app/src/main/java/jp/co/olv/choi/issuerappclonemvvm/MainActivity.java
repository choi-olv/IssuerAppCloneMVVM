package jp.co.olv.choi.issuerappclonemvvm;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import jp.co.olv.choi.issuerappclonemvvm.realm.MyMigration;
import jp.co.olv.choi.issuerappclonemvvm.realm.PayDetail;
import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {

    private  PayDetailViewModel payDetailViewModel;

    @BindView(R.id.testTextView)
    TextView testTextView;
    @BindView(R.id.testButton)
    Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ButterKnife連携
        ButterKnife.bind(this);
        // Realm初期化
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .schemaVersion(0)
                .migration(new MyMigration())
                .build());

        // ViewModelのインスタンス生成
        payDetailViewModel = ViewModelProviders.of(this).get(PayDetailViewModel.class);

        // 支払明細情報の更新を監視
        payDetailViewModel.getAll().observe(this, new Observer<RealmResults<PayDetail>>() {
            @Override
            public void onChanged(@Nullable RealmResults<PayDetail> payDetail) {
                // TODO 支払明細のリストビュー処理に差し替える。
                testTextView.setText("更新されました。");
            }
        });
    }

    // LiveDataの挙動確認のために仮実装
    @SneakyThrows
    @OnClick(R.id.testButton) void clickButton() {
        payDetailViewModel.getAll();
    }

    // LiveDataの挙動確認のために仮実装
    @OnLongClick(R.id.testButton) boolean longClickButton() {
        payDetailViewModel.delete(1);
        return true;
    }
}