package com.a2a.app.a2aapp;

import java.util.ArrayList;

/**
 * Created by Mounika on 4/18/2016.
 */
public interface RoutingListener  {

    void onRoutingFailure(RouteException e);

    void onRoutingStart();

    void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex);

    void onRoutingCancelled();

    void onRouting1Failure(RouteException e);
}
