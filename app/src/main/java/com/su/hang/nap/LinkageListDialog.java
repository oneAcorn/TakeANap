//package com.su.hang.nap;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.Display;
//import android.view.Gravity;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//
//
//public class LinkageListDialog extends Dialog implements View.OnClickListener {
//    private Context context;
//
//    public LinkageListDialog(Context context) {
//        super(context);
//        this.context = context;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.dialog_linkage_list);
//        init();
//
//        Window window = this.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        WindowManager wm = ((Activity) context).getWindowManager();
//        Display d = wm.getDefaultDisplay(); // 闁兼儳鍢茶ぐ鍥╀沪韫囨挾顔庨悗鐟邦潟閿熸垝绶氶悵顕�鎮介敓锟�
//        // lp.height = (int) (d.getHeight() * 0.4); // 濡ゅ倹锚鐎瑰磭鎷嬮崜褏鏋�
////        lp.width = (int) (d.getWidth() * 1); // 閻庣妫勭�瑰磭鎷嬮崜褏鏋�
//        lp.width = WindowManager.LayoutParams.FILL_PARENT;
//        // window.setGravity(Gravity.LEFT | Gravity.TOP);
//        window.setGravity(Gravity.BOTTOM);
//        // dialog濮掓稒顭堥鑽や焊鏉堛劍绠抪adding 閻庝絻澹堥崵褎淇婇崒娑氫函濞戞挸绉风换鏍ㄧ▕閸綆鍟庣紓鍐惧枤濞堟垹鎷犻敓锟�
//        // dialog婵ɑ鐡曠换娆愮▔瀹ュ牆鍘撮柛蹇嬪妼閻拷
//        window.getDecorView().setPadding(0, 0, 0, 0);
//        // lp.x = 100; // 闁哄倿顣︾紞鍛磾閻㈡棃宕搁幇顓犲灱
//        // lp.y = 100; // 闁哄倿顣︾紞鍛磾閻㈡洟宕搁幇顓犲灱
//        // lp.height = 30;
//        // lp.width = 20;
//        window.setAttributes(lp);
//
//        //
//        window.setBackgroundDrawableResource(android.R.color.white);
//    }
//
//
//    private void init() {
//        calculateResultTv = (TextView) findViewById(R.id.calculate_result_tv);
//        investMoneyEt = (EditText) findViewById(R.id.invest_money_et);
//        investUnitTv = (TextView) findViewById(R.id.invest_unit_tv);
//        investPeriodEt = (EditText) findViewById(R.id.invest_period_et);
//        investPeriodUnitTv = (TextView) findViewById(R.id.invest_period_unit_tv);
//        interestRateEt = (EditText) findViewById(R.id.interest_rate_et);
//        interestRateUnitTv = (TextView) findViewById(R.id.interest_rate_unit_tv);
//        raiseInterestEt = (EditText) findViewById(R.id.raise_interest_et);
//        raiseInterestUnitTv = (TextView) findViewById(R.id.raise_interest_unit_tv);
//        calculateBtn = (Button) findViewById(R.id.calculate_btn);
//        cleanBtn = (Button) findViewById(R.id.clean_btn);
//        investMoneyEt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                limitDot(s.toString(), investMoneyEt);
//            }
//        });
//        interestRateEt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                limitDot(s.toString(), interestRateEt);
//            }
//        });
//        raiseInterestEt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                limitDot(s.toString(), raiseInterestEt);
//            }
//        });
//        calculateBtn.setOnClickListener(this);
//        cleanBtn.setOnClickListener(this);
//
////        onFocusChangeListener = new View.OnFocusChangeListener() {
////            @Override
////            public void onFocusChange(View v, boolean hasFocus) {
////
////                if (hasFocus) {
////                    res = investPeriodEt.getText().toString();
////                    investPeriodEt.setText("");
////                    investPeriodEt.setText(res);
////                    investPeriodEt.setSelection(res.length());
////                }
////                if (hasFocus) {
////                    res = interestRateEt.getText().toString();
////                    interestRateEt.setText("");
////                    interestRateEt.setText(res);
////                    interestRateEt.setSelection(res.length());
////                }
////                if (hasFocus) {
////                    res = raiseInterestEt.getText().toString();
////                    raiseInterestEt.setText("");
////                    raiseInterestEt.setText(res);
////                    raiseInterestEt.setSelection(res.length());
////                }
////            }
////        };
//        final int delayTime = 100;
//        investMoneyEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
////                    String res = investMoneyEt.getText().toString();
////                    investMoneyEt.setText("");
////                    investMoneyEt.setText(res);
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            investMoneyEt.setSelection(investMoneyEt.getText().toString().length());
//                        }
//                    }, delayTime);//n秒后执行Runnable中的run方法
//
//                }
//            }
//        });
//        investPeriodEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
////                    String res = investMoneyEt.getText().toString();
////                    investMoneyEt.setText("");
////                    investMoneyEt.setText(res);
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            investPeriodEt.setSelection(investPeriodEt.getText().toString().length());
//                        }
//                    }, delayTime);//n秒后执行Runnable中的run方法
//
//                }
//            }
//        });
//        interestRateEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
////                    String res = investMoneyEt.getText().toString();
////                    investMoneyEt.setText("");
////                    investMoneyEt.setText(res);
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            interestRateEt.setSelection(interestRateEt.getText().toString().length());
//                        }
//                    }, delayTime);//n秒后执行Runnable中的run方法
//
//                }
//            }
//        });
//        raiseInterestEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
////                    String res = investMoneyEt.getText().toString();
////                    investMoneyEt.setText("");
////                    investMoneyEt.setText(res);
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            raiseInterestEt.setSelection(raiseInterestEt.getText().toString().length());
//                        }
//                    }, delayTime);//n秒后执行Runnable中的run方法
//
//                }
//            }
//        });
////        investPeriodEt.setOnFocusChangeListener(onFocusChangeListener);
////        interestRateEt.setOnFocusChangeListener(onFocusChangeListener);
////        raiseInterestEt.setOnFocusChangeListener(onFocusChangeListener);
//    }
//
//    private void limitDot(String s, EditText et) {
//        if (!et.isFocused()) {
//            return;
//        }
//        if ("0.00".equals(s)) {
//            return;
//        }
//        try {
//            if (TextUtils.isEmpty(s)) {
//                return;
//            }
//            //限制两位小数
//            if (!s.equals(NumberUtil.cutNum(s))) {
//                //为解决有小数点时 如6.这样的情况会直接被和谐掉的问题
//                if (!s.substring(s
//                        .length() - 1, s.length()).equals(".")) {
//                    if (!s.substring(s
//                            .length() - 2, s.length()).equals(".0")) {
//                        String res = NumberUtil.cutNum(s);
//                        et.setText(res);
//                        et.setSelection(res.length());
//                    }
//                }
//            }
//        } catch (Exception e) {
//
//        }
//    }
//
//    private void addDot(EditText et) {
//        String res = et.getText().toString().trim();
//        if (TextUtils.isEmpty(res)) {
//            return;
//        }
//        if (res.contains(".")) {
//            return;
//        }
//        et.setText(res + ".00");
//    }
//
//    public void setData(String investNum, String investPeriod, String rate, String raiseRate) {
//        investMoneyEt.setText(NumberUtil.doubleDecimals(investNum));
//        investPeriodEt.setText(NumberUtil.cutNum(investPeriod));
//        if (TextUtils.isEmpty(rate)) {
//            interestRateEt.setText("");
//        } else {
//            interestRateEt.setText(NumberUtil.doubleDecimals(rate));
//        }
//        if (TextUtils.isEmpty(raiseRate) || Float.valueOf(raiseRate) == 0) {
//            raiseInterestEt.setText("");
//        } else {
//            raiseInterestEt.setText(NumberUtil.doubleDecimals(raiseRate));
//        }
//    }
//
//    private void calculateEarning() {
//        float couponRate = 0f;
//        if (!TextUtils.isEmpty(raiseInterestEt.getText().toString().trim())) {
//            couponRate = getEtFloat(raiseInterestEt);
//        }
//        float earning = getEtFloat(investMoneyEt) *
//                getEtFloat(investPeriodEt) *
//                ((getEtFloat(interestRateEt) + couponRate) / 365 / 100);
//        calculateResultTv.setText(NumberUtil.toCommaNum(earning + ""));
//        if (TextUtils.isEmpty(raiseInterestEt.getText().toString())) {
//            raiseInterestEt.setText("0.00");
//        }
//    }
//
//    private float getEtFloat(EditText et) {
//        try {
//            String str = et.getText().toString().replaceAll(",", "").trim();
//            return Float.valueOf(str);
//        } catch (Exception e) {
//            return 0f;
//        }
//    }
//
//    private void reset() {
//        investMoneyEt.setText("");
//        investPeriodEt.setText("");
//        interestRateEt.setText("");
//        raiseInterestEt.setText("");
//        calculateResultTv.setText("0.00");
//    }
//
//    private boolean checkData() {
//        PeanutToast baseToast = new PeanutToast(context);
//        if (TextUtils.isEmpty(investMoneyEt.getText().toString())) {
//            baseToast.showToast("请输入出借金额");
//            return false;
//        }
//        if (TextUtils.isEmpty(investPeriodEt.getText().toString())) {
//            baseToast.showToast("请输入出借期限");
//            return false;
//        }
//        if (TextUtils.isEmpty(interestRateEt.getText().toString())) {
//            baseToast.showToast("请输入7日年化收益率");
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.calculate_btn:
//                if (checkData()) {
//                    calculateEarning();
//                }
//                break;
//            case R.id.clean_btn:
//                reset();
//                break;
//            case R.id.invest_money_et:
//            case R.id.invest_period_et:
//            case R.id.interest_rate_et:
//            case R.id.raise_interest_et:
//
//                break;
//        }
//    }
//}
