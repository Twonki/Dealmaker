package com.dealmaker.hacknotts.dealmaker;

import java.util.List;

/**
 * Created by morgan on 16/11/2019.
 */

public interface networkListener {
    void onResponseGet(List<Profile> profiles);
}
