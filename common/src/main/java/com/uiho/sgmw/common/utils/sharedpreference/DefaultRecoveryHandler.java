package com.uiho.sgmw.common.utils.sharedpreference;

import android.content.SharedPreferences;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.List;

/**
 * 作者：uiho_mac
 * 时间：2018/4/17
 * 描述：
 * 版本：1.0
 * 修订历史：
 */


public class DefaultRecoveryHandler extends RecoveryHandler {
    @Override
    protected boolean recover(Exception e, KeyStore keyStore, List<String> keyAliases, SharedPreferences preferences) {
        e.printStackTrace();

        try {
            clearKeyStore(keyStore, keyAliases);
            clearPreferences(preferences);
            return true;
        } catch (KeyStoreException e1) {
            e1.printStackTrace();
        }

        return false;
    }
}
