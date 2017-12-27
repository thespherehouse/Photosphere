package com.suhel.photosphere.service.rx;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxHelper {

    public static <U> SingleTransformer<U, U> applySchedulers() {
        return new SingleTransformer<U, U>() {

            @Override
            public SingleSource<U> apply(Single<U> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }

        };
    }

}
