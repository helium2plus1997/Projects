package com.a2a.app.a2aapp;

import java.util.ArrayList;

/**
 * Created by Mounika on 4/18/2016.
 */
public interface Routing1Listener extends RoutingListener {

    void onRouting1Start();

    void onRouting1Success(ArrayList<Route> route, int shortestRouteIndex);

    void onRouting1Cancelled();

}
