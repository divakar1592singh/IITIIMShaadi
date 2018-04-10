package com.senzecit.iitiimshaadi.viewController;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

class StethoInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
