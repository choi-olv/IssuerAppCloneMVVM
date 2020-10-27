package jp.co.olv.choi.issuerappclonemvvm.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import jp.co.olv.choi.issuerappclonemvvm.R;
import jp.co.olv.choi.issuerappclonemvvm.Taplayout.OnItemCheckListener;
import jp.co.olv.choi.issuerappclonemvvm.realm.PayDetail;

public class PayDetailRecyclerViewAdapter extends RecyclerView.Adapter<PayDetailViewHolder> {

    private List<PayDetail> items;
    private OnItemCheckListener onItemCheckListener;

    public PayDetailRecyclerViewAdapter(List<PayDetail> items, OnItemCheckListener onItemCheckListener) {
        this.items = items;
        this.onItemCheckListener = onItemCheckListener;
    }

    @Override
    public PayDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.pay_detail, parent,false);
        return new PayDetailViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final PayDetailViewHolder holder, int position) {
        holder.shopNameView.setText(items.get(position).getShopName());
        holder.amountView.setText(items.get(position).getAmount() + "円");
        holder.payDateView.setText(items.get(position).getPayDate());
        holder.payCountView.setText(items.get(position).getPayCount() + "回払い");

        // チェックボックスの動作
        final PayDetail currentItem = items.get(position);
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkBox.setChecked(!holder.checkBox.isChecked());
                if (holder.checkBox.isChecked()) {
                    onItemCheckListener.onItemCheck(currentItem);
                } else {
                    onItemCheckListener.onItemUncheck(currentItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}