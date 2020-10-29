package jp.co.olv.choi.issuerappclonemvvm.Taplayout;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import jp.co.olv.choi.issuerappclonemvvm.PayDetailViewModel;
import jp.co.olv.choi.issuerappclonemvvm.R;
import jp.co.olv.choi.issuerappclonemvvm.RecyclerView.PayDetailRecyclerViewAdapter;
import jp.co.olv.choi.issuerappclonemvvm.realm.LiveRealmData;
import jp.co.olv.choi.issuerappclonemvvm.realm.PayDetail;
import lombok.Getter;

public class RightFragment extends Fragment {

    private PayDetailViewModel payDetailViewModel;
    @Getter
    private List<PayDetail> currentSelectedItems = new ArrayList<>();

    @BindView(R.id.PayDetailRecyclerView)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_right, container, false);
        // ButterKnife連携
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ViewModelのインスタンス生成
        payDetailViewModel = ViewModelProviders.of(this).get(PayDetailViewModel.class);
        // ViewModelから支払明細データのLiveDataを取得
        LiveRealmData<PayDetail> payDetailsLiveData = payDetailViewModel.getAll(2, currentSelectedItems);

        // 支払明細データの更新を監視
        payDetailsLiveData.observe(this, new Observer<RealmResults<PayDetail>>() {
            @Override
            public void onChanged(@Nullable RealmResults<PayDetail> payDetails) {
                Realm realm = Realm.getDefaultInstance();
                List<PayDetail> payDetailList = realm.copyFromRealm(payDetails);
                PayDetailRecyclerViewAdapter recyclerViewAdapter = new PayDetailRecyclerViewAdapter(payDetailList, new OnItemCheckListener() {
                    @Override
                    public void onItemCheck(PayDetail item) {
                        currentSelectedItems.add(item);
                    }

                    @Override
                    public void onItemUncheck(PayDetail item) {
                        currentSelectedItems.remove(item);
                    }
                });

                // RecyclerViewにデータ反映
                recyclerView.setAdapter(recyclerViewAdapter);
            }
        });

        // LiveDataをもとにRecyclerViewのアダプタ生成
        RealmResults<PayDetail> payDetails = payDetailsLiveData.getResults();
        Realm realm = Realm.getDefaultInstance();
        List<PayDetail> payDetailList = realm.copyFromRealm(payDetails);
        PayDetailRecyclerViewAdapter recyclerViewAdapter = new PayDetailRecyclerViewAdapter(payDetailList, new OnItemCheckListener() {
            @Override
            public void onItemCheck(PayDetail item) {
                currentSelectedItems.add(item);
            }

            @Override
            public void onItemUncheck(PayDetail item) {
                currentSelectedItems.remove(item);
            }
        });

        // RecyclerView初期化
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    // チェックした項目を削除
    @OnClick(R.id.removeButtonInRightTap) void removeCheckedItem() {
        payDetailViewModel.delete(currentSelectedItems);
    }
}