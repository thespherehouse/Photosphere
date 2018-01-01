package com.suhel.photosphere.application.instance;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.suhel.photosphere.application.contract.AppContract;
import com.suhel.photosphere.service.rest.ApiSubscriber;
import com.suhel.photosphere.service.rest.RestService;

public class SimpleFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        getRestService().fcm(FirebaseInstanceId.getInstance().getToken(), new ApiSubscriber.Callback<Void>() {


        });
    }

    private RestService getRestService() {
        return ((AppContract) getApplication()).getRestService();
    }

}
