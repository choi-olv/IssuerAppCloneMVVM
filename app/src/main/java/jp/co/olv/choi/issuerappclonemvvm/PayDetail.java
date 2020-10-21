package jp.co.olv.choi.issuerappclonemvvm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayDetail extends RealmObject {

    @PrimaryKey
    private Integer id;
    private String shopName;
    private String amount;
    private String payDate;
    private String payCount;

    public PayDetail() {
    }

    public PayDetail(String shopName, String amount, String payDate, String payCount) {
        this.id = null;
        this.shopName = shopName;
        this.amount = amount;
        this.payDate = payDate;
        this.payCount = payCount;
    }

    public PayDetail(Integer id, String shopName, String amount, String payDate, String payCount) {
        this.id = id;
        this.shopName = shopName;
        this.amount = amount;
        this.payDate = payDate;
        this.payCount = payCount;
    }
}