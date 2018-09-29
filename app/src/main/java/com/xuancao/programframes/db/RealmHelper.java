package com.xuancao.programframes.db;


import com.xuancao.programframes.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmHelper<T extends RealmObject> {

    public static final String DB_NAME = "inovel.realm";

    public static final long DB_VERSION = 3;

//    private Realm mRealm;

    private T mBean;

    @Inject
    public RealmHelper() {}


    public Realm getRealm(){
        Realm mRealm = Realm.getDefaultInstance();
        return mRealm;
    }

    /**
     * add （增）
     */
    public void add(T object) {
        LogUtil.e("realm add");
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(realm -> {
            realm.copyToRealmOrUpdate(object);
        });
        mRealm.close();
        LogUtil.e("realm add close");
    }

    /**
     * delete （根据主键 ID 删除）
     */
    public void delete(Class<? extends T> clazz, String id) {
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(realm -> {
            realm.where(clazz).equalTo("id", id).findFirst().deleteFromRealm();
        });
        mRealm.close();
    }

    /**
     * delete （根据对象 删除）
     */
    public void delete(Class<? extends T> clazz) {
        LogUtil.e("realm delete");
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(realm -> {
            RealmObject object = realm.where(clazz).findFirst();
            if (object != null) object.deleteFromRealm();
        });
        mRealm.close();
        LogUtil.e("realm delete close");
    }

    /**
     * delete （根据对象 删除所有）
     */
    public void deleteAll(Class<? extends T> clazz) {
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(realm -> {
            realm.where(clazz).findAll().deleteAllFromRealm();
        });
    }

    /**
     * update （改单个属性值）
     * @param clazz
     * @param id
     * @param fieldName
     * @param newValue
     */
    public void update(Class<? extends T> clazz, String id, String fieldName, String newValue) {
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(realm -> {
//            T t = realm.where(clazz).equalTo("id", id).findFirst();
//            Map<String,String> map = new HashMap<String, String>();
//            map.put(fieldName,newValue);
//            BeanRefUtil.setFieldValue(t, map);
        });
    }

    /**
     *
     * @param clazz
     * @param key
     * @param value
     * @param bean
     */
    public void update(Class<? extends T> clazz, String key, String value, T bean) {
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(realm -> {
        });
    }

    /**
     * query （查询所有）
     * @param clazz
     * @return
     */
    public List<T> queryAll(Class<? extends T> clazz) {
        Realm mRealm = Realm.getDefaultInstance();
        List<T> list = new ArrayList<T>();
        mRealm.executeTransaction(realm -> {
            RealmResults<T> results = (RealmResults<T>) realm.where(clazz).findAll();
            if (results != null) {
                for (T t : results) {
                    list.add(mRealm.copyFromRealm(t));
                }
            }
        });
        return list;
    }

    /**
     * query （根据key查询所有）
     * @param clazz
     * @param key
     * @param value
     * @return
     */
    public List<T> queryAllByKey(Class<? extends T> clazz, String key, String value) {
        Realm mRealm = Realm.getDefaultInstance();
        List<T> list = new ArrayList<T>();
        mRealm.executeTransaction(realm -> {
            RealmResults<T> results = (RealmResults<T>) realm.where(clazz).equalTo(key, value).findAll();
            if (results != null) {
                for (T t : results) {
                    list.add(mRealm.copyFromRealm(t));
                }
            }
        });
        return list;
    }

    /**
     * query （根据key查询所有并排序）
     * @param clazz
     * @param key
     * @param value
     * @param sortKey
     * @param sort
     * @return
     */
    public List<T> queryAllByKeyAndSort(Class<? extends T> clazz, String key, String value, String sortKey, Sort sort) {
        Realm mRealm = Realm.getDefaultInstance();
        List<T> list = new ArrayList<T>();
        RealmResults<T> results = (RealmResults<T>) mRealm.where(clazz).equalTo(key, value).findAll().sort(sortKey, sort);
        if (results != null) {
            for (T t : results) {
                list.add(mRealm.copyFromRealm(t));
            }
        }
        return list;
    }

    /**
     * query （查询所有并排序）
     * @param clazz
     * @param sortKey
     * @param sort
     * @return
     */
    public List<T> queryAllBySort(Class<? extends T> clazz, String sortKey, Sort sort) {
        Realm mRealm = Realm.getDefaultInstance();
        List<T> list = new ArrayList<T>();
        RealmResults<T> results = (RealmResults<T>) mRealm.where(clazz).findAll().sort(sortKey, sort);
        if (results != null) {
            for (T t : results) {
                list.add(mRealm.copyFromRealm(t));
            }
        }
        return list;
    }

    /**
     * query (查询单个对象)
     * @param clazz
     * @return
     */
    public T queryFirst(Class<? extends T> clazz) {
        Realm mRealm = Realm.getDefaultInstance();
        T result = null;
        T bean = mRealm.where(clazz).findFirst();
        if (bean!=null) result = mRealm.copyFromRealm(bean);
        mRealm.close();
        return result;
    }

    /**
     * query (根据关键key查询单个对象)
     * @param clazz
     * @param key
     * @param value
     * @return
     */
    public T queryByKey(Class<? extends T> clazz, String key, String value) {
        Realm mRealm = Realm.getDefaultInstance();
        mBean = (T) mRealm.where(clazz).equalTo(key, value).findFirst();
        return mBean;
    }

    /**
     * 根据主键查询对象是否已存在
     * @param id
     * @return
     */
    public boolean isExist(Class<? extends T> clazz, String id){
//        Realm mRealm = Realm.getDefaultInstance();
//        mBean = (T) mRealm.where(clazz).equalTo("id",id).findFirst();
//        close();
//        if (mBean == null){
//            return false;
//        }else {
//            return  true;
//        }
        return false;
    }
}
