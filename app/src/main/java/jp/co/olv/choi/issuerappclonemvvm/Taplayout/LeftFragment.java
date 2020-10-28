package jp.co.olv.choi.issuerappclonemvvm.Taplayout;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.co.olv.choi.issuerappclonemvvm.PayDetailViewModel;
import jp.co.olv.choi.issuerappclonemvvm.R;

public class LeftFragment extends Fragment {

    private PayDetailViewModel payDetailViewModel;

    @BindView(R.id.parameter)
    EditText editText;
//    左タブ上のRecyclerView項目削除機能の追加保留
//    @Nullable
//    @BindView(R.id.PayDetailRecyclerView)
//    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_left, container, false);
        // ButterKnife連携
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ViewModelのインスタンス生成
        payDetailViewModel = ViewModelProviders.of(this).get(PayDetailViewModel.class);
    }

    // パラメータを指定してAPI実行
    @OnClick(R.id.execute) void executeApiWithParameter() {

        String postId = editText.getText().toString();
        if (!postId.isEmpty()) {
            payDetailViewModel.getAll(Integer.parseInt(postId));
        }
    }

//    左タブ上のRecyclerView項目削除機能の追加保留
//    @OnClick(R.id.removeButtonInLeftTap) void removeCheckedItem() {
//
//        RecyclerView recyclerView = getActivity().findViewById(R.id.PayDetailRecyclerView);
//        PayDetailRecyclerViewAdapter recyclerViewAdapter = (PayDetailRecyclerViewAdapter) recyclerView.getAdapter();
//        List<PayDetail> items = recyclerViewAdapter.;
//        payDetailViewModel.delete(items);
//    }
}