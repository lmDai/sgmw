package com.uiho.module_palm.presenter;

import android.text.TextUtils;

import com.uiho.module_palm.base.PalmModule;
import com.uiho.module_palm.contract.ParksContract;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.https.observers_extension.ProgressObserver;
import com.uiho.sgmw.common.https.scheduler.RxSchedulers;
import com.uiho.sgmw.common.model.ParkRequestModel;
import com.uiho.sgmw.common.model.RequestModel;
import com.uiho.sgmw.common.model.ResponseContent;
import com.uiho.sgmw.common.utils.GsonUtils;
import com.uiho.sgmw.common.utils.NewAesUtils;
import com.uiho.sgmw.common.utils.SystemUtils;

import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.uiho.sgmw.common.Constants.AUTHER_KEY_PARKING;
import static com.uiho.sgmw.common.Constants.PARKING_KEY;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：停车场
 * 版本：1.0
 * 修订历史：
 */
public class ParksPresenter extends ParksContract.Presenter {

    @Override
    public void getParks(double longtitude, double latitude) {
        RequestModel requestModel = new RequestModel();
        ParkRequestModel parkRequestModel = new ParkRequestModel(longtitude, latitude);
        UUID reqGUID = UUID.randomUUID();
        String reqSign = SystemUtils.encodeMD5(AUTHER_KEY_PARKING + "|" + PARKING_KEY + "|" + reqGUID.toString());
        requestModel.setReqHead(new RequestModel.ReqHeadBean(AUTHER_KEY_PARKING, "AES", reqGUID.toString(), reqSign));
        requestModel.setReqBody(new RequestModel.ReqBodyBean(NewAesUtils.encrypt(GsonUtils.GsonString(parkRequestModel), PARKING_KEY)));
        String postInfoStr = GsonUtils.GsonString(requestModel);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postInfoStr);

        PalmModule.createAppVerSionRetrofit().getParks(body)
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.handResultList())
                .subscribe(new ProgressObserver<String>(this) {
                    @Override
                    public void onSuccess(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            ResponseContent responseContent = GsonUtils.GsonToBean(NewAesUtils.decrypt(result, Constants.PARKING_KEY), ResponseContent.class);
                            getView().getParksSuccess(responseContent.getInfoArray());
                        }
                    }
                });
    }
}
