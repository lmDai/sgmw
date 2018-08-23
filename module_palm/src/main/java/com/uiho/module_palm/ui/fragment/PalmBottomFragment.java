package com.uiho.module_palm.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.uiho.module_palm.R;
import com.uiho.module_palm.R2;
import com.uiho.module_palm.ui.activity.TravelStatisticsActivity;
import com.uiho.sgmw.common.base.BaseFragment;
import com.uiho.sgmw.common.base.RouterPath;
import com.uiho.sgmw.common.eventbus.EventType;
import com.uiho.sgmw.common.model.CarInfoModel;
import com.uiho.sgmw.common.model.UserModel;
import com.uiho.sgmw.common.utils.IntentUtils;
import com.uiho.sgmw.common.utils.StringUtils;
import com.uiho.sgmw.common.utils.UserUtils;
import com.uiho.sgmw.common.utils.glide.ImageLoadUtils;
import com.uiho.sgmw.common.widget.lableview.LabelImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
@Route(path = RouterPath.PALM_BOTTOM_FRAGMENT) // 路由地址，必须注明
public class PalmBottomFragment extends BaseFragment {
    @BindView(R2.id.ll_trip)
    LinearLayout llTrip;
    @BindView(R2.id.label_img)
    LabelImageView labelImg;
    @BindView(R2.id.imghead)
    ImageView imghead;
    @BindView(R2.id.txtxm)
    TextView txtxm;
    @BindView(R2.id.txtphone)
    TextView txtphone;
    @BindView(R2.id.txttime)
    TextView txttime;
    private String vin = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_plam_bottom;
    }

    @Override
    protected void lazyFetchData() {
        UserModel userModel = UserUtils.getUser(mContext);
        ImageLoadUtils.displayRoundError(mContext, imghead, userModel != null ? userModel.getAvatar() : null, R.drawable.user);
        if ((userModel != null && userModel.getVins() != null)) {
            String[] vins = userModel.getVins().split(",");
            vin = vins[0];
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBus(EventType type) {
        if (type != null && type.getType() == EventType.CAR_INFO) {
            if (type.getObject() instanceof CarInfoModel) {
                CarInfoModel model = (CarInfoModel) type.getObject();
                if (!TextUtils.isEmpty(model.getXm()))
                    txtxm.setText("车主姓名：" + model.getXm());
                if (!TextUtils.isEmpty(model.getPhoneNum()))
                    txtphone.setText("车主电话：" + model.getPhoneNum());
                if (!TextUtils.isEmpty(model.getKqrq()))
                    txttime.setText("提车日期：" + model.getKqrq());
                labelImg.setLabelText(StringUtils.filterEmpty(model.getClpp()));
            }

        }
    }

    @OnClick(R2.id.ll_trip)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("vin", vin);
        IntentUtils.get().goActivity(mContext, TravelStatisticsActivity.class, bundle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
