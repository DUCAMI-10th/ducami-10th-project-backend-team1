package com.ducami.ducamiproject.global.log.aop;

import com.ducami.ducamiproject.domain.admin.log.enums.AdminAction;
import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;

import java.util.Map;

public class LogActivityContext {
  private TargetType target;
  private AdminAction action;
  private String template;

  private Map<String, Object> targetEntities;
  private Map<String, Object> params;

}
