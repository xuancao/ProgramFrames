package com.xuancao.programframes.db;

import com.xuancao.programframes.utils.LogUtil;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmSchema;


public class InovelMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        LogUtil.e("oldVersion:" + oldVersion + ";new:" + newVersion);
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 1) {
            schema.get("User")
                    .addField("is_new_member", Integer.class, FieldAttribute.REQUIRED);
            oldVersion++;
        }

        if (oldVersion == 2) {
            schema.create("CategoryRealm")
                    .addField("id", Integer.class, FieldAttribute.REQUIRED)
                    .addField("name", String.class)
                    .addField("gender", String.class);
            schema.get("BookInfoRealm").addRealmObjectField("category", schema.get("CategoryRealm"));
            oldVersion++;
        }
    }

    @Override
    public int hashCode() {
        return 33;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        return (o instanceof InovelMigration);
    }
}
