package jp.co.olv.choi.issuerappclonemvvm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import jp.co.olv.choi.issuerappclonemvvm.realm.PayDetail;

public class PayDetailRecyclerViewAdapter extends RecyclerView.Adapter<PayDetailViewHolder> {
    private List<PayDetail> list;

    public PayDetailRecyclerViewAdapter(List<PayDetail> list) {
        this.list = list;
    }

    @Override
    public PayDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.pay_detail, parent,false);
        return new PayDetailViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(PayDetailViewHolder holder, int position) {
        holder.detailView.setText(list.get(position).getShopName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}