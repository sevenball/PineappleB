package com.wangshiqi.pineappleb.ui.activity.mine;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fuqianla.paysdk.FuQianLa;
import com.fuqianla.paysdk.FuQianLaPay;
import com.fuqianla.paysdk.bean.FuQianLaResult;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.ui.activity.AbsBaseActivity;
import com.wangshiqi.pineappleb.utils.Merchant;
import com.wangshiqi.pineappleb.utils.OrderUtils;

/**
 * Created by dllo on 16/10/26.
 */
public class PayPlayActivity extends AbsBaseActivity implements View.OnClickListener {
    private EditText etCommodity, etDetails, etAmount;
    private Button payBtn;
    private String jdToken = "";
    @Override
    protected int setLayout() {
        return R.layout.activity_pay;
    }

    @Override
    protected void initViews() {
        etCommodity = byView(R.id.et_commodity);
        etDetails = byView(R.id.et_details);
        etAmount = byView(R.id.et_amount);
        payBtn = byView(R.id.btn_pay);
        payBtn.setOnClickListener(this);
        etCommodity.setText("商品名称");
        etDetails.setText("支付详情");
        etAmount.setText("0.01");
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay:
                String subject = etCommodity.getText().toString();
                String amount = etAmount.getText().toString();
                String body = etDetails.getText().toString();

                if (TextUtils.isEmpty(subject)
                        || TextUtils.isEmpty(amount)
                        || TextUtils.isEmpty(body))
                    return;


                //支付核心代码
                FuQianLaPay pay = new FuQianLaPay.Builder(this)
                        .partner(Merchant.MERCHANT_NO)//商户号
                        .alipay(true)
                        .wxpay(true)
                        .baidupay(true)
                        .unionpay(true,2)
                        .jdpay(true, 1)
                        .orderID(OrderUtils.getOutTradeNo())//订单号
                        .amount(Double.parseDouble(amount))//金额
                        .subject(subject)//商品名称
                        .body(body)//商品交易详情
                        .notifyUrl(Merchant.MERCHANT_NOTIFY_URL)
                        .build();
                pay.startPay();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //返回结果
        if (requestCode == FuQianLa.REQUESTCODE
                && resultCode == FuQianLa.RESULTCODE
                && data != null) {
            FuQianLaResult result = data.getParcelableExtra(FuQianLa.PAYRESULT_KEY);
            Toast.makeText(getApplicationContext(), result.payCode, Toast.LENGTH_SHORT).show();
            if (FuQianLa.JD.equals(result.payChannel)) {
                jdToken = result.payMessage;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
