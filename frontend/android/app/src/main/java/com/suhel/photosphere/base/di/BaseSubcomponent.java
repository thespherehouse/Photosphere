package com.suhel.photosphere.base.di;

public interface BaseSubcomponent {

    interface Builder<C extends BaseSubcomponent, M extends BaseModule> {

        Builder<C, M> addModule(M module);

        C build();

    }

}
