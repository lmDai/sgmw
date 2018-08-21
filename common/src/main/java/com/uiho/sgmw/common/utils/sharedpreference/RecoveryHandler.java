package com.uiho.sgmw.common.utils.sharedpreference;

import android.content.SharedPreferences;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Collections;
import java.util.List;

/**
 * 作者：uiho_mac
 * 时间：2018/4/17
 * 描述：
 * 版本：1.0
 * 修订历史：
 */

public abstract class RecoveryHandler {
    protected abstract boolean recover(Exception e, KeyStore keyStore, List<String> keyAliases, SharedPreferences preferences);

    void clearKeyStore(KeyStore keyStore, List<String> aliases) throws KeyStoreException {
        if(keyStore != null && aliases != null){
            for(String alias:aliases){
                if(keyStore.containsAlias(alias)) keyStore.deleteEntry(alias);
            }
        }
    }

    void clearKeystore(KeyStore keyStore) throws KeyStoreException {
        if(keyStore != null){
            List<String> aliases = Collections.list(keyStore.aliases());
            for(String alias:aliases){
                if(keyStore.containsAlias(alias)) keyStore.deleteEntry(alias);
            }
        }
    }

    void clearPreferences(SharedPreferences preferences){
        if(preferences != null) preferences.edit().clear().apply();
    }
}
