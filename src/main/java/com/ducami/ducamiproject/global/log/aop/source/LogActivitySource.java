package com.ducami.ducamiproject.global.log.aop.source;

import com.ducami.ducamiproject.global.log.annotation.LogActivity;
import com.ducami.ducamiproject.global.log.annotation.target.LogTargetEntity;

import java.lang.reflect.Method;
import java.util.List;

public interface LogActivitySource {
    LogActivity getLogActivity(Method method);
}
