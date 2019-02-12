package org.paramedic.homeless.currenciestest.service.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.paramedic.homeless.currenciestest.R;

import java.lang.reflect.Type;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposables;

class Database {
    private SharedPreferences mPrivatePrefs;
    private final Gson gson;

    <T>Flowable<T> onChangeObjectSubscription(String objectKey, Type type, boolean emmitOnSubscribe, T defaultValue) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> e) throws Exception {
                final SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
                        if (objectKey.equals(key)) {
                            e.onNext(getObject(key, type, defaultValue));
                        }
                    }
                };
                e.setDisposable(Disposables.fromAction(() -> {
                    mPrivatePrefs.unregisterOnSharedPreferenceChangeListener(listener);
                }));
                e.setCancellable(() -> {
                    mPrivatePrefs.unregisterOnSharedPreferenceChangeListener(listener);
                });

                mPrivatePrefs.registerOnSharedPreferenceChangeListener(listener);

                if (emmitOnSubscribe && (mPrivatePrefs.contains(objectKey))) {
                    e.onNext(getObject(objectKey, type, defaultValue));
                }
            }
        }, BackpressureStrategy.LATEST );
    }

    Database(Context context, Gson gson) {
        this.gson = gson;
        if (mPrivatePrefs == null)
            mPrivatePrefs = context.getSharedPreferences(context.getString(R.string.prefs_name), 0);
    }

    void putObject(final String key, final Object object) {
        String json = gson.toJson(object);
        mPrivatePrefs.edit().putString(key, json).apply();
    }

    <T> T getObject(final String key, Type type, T defaultValue) {
            String jsonData = mPrivatePrefs.getString(key, null);
            if (jsonData != null) {
                    return gson.fromJson(jsonData, type);
            }
            return defaultValue;
    }

    void putString(final String key, final String value) {
        {
            mPrivatePrefs.edit().putString(key, value).apply();
        }
    }

    String getString(final String key, String defValue) {
        {
            return mPrivatePrefs.getString(key, defValue);
        }
    }
}
