package com.vhackclub.oliu.base;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.vhackclub.oliu.models.Restaurant;

/**
 * Created by geruk on 7/26/15.
 */
@ParseClassName("BaseLocation")
public class BaseLocation extends ParseObject {

    public static enum TYPE {
        RESTAURANT(Restaurant.class);
        public final Class<? extends BaseLocation> mClass;
        TYPE(Class<? extends BaseLocation> restaurantClass) {
            mClass = restaurantClass;
        }
    }

    public static ParseQuery<BaseLocation> getQuery() {
        return ParseQuery.getQuery(BaseLocation.class);
    }
}
