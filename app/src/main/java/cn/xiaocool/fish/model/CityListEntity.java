package cn.xiaocool.fish.model;

import android.support.annotation.NonNull;

import java.util.List;

public class CityListEntity {
    public List<CityInfoEntity> city;

    public static class CityInfoEntity implements Comparable {
        public String id;
        public String area_en;
        public String area;
        public String city;
        public String province;

        @Override
        public int compareTo(@NonNull Object another) {
            return this.id.compareTo(((CityInfoEntity) another).id);
        }
    }
}
