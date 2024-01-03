/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.nercis.isscp.idl;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 下发任务队列任务信息
 * 
 */
public class AppSendMessage implements org.apache.thrift.TBase<AppSendMessage, AppSendMessage._Fields>, java.io.Serializable, Cloneable, Comparable<AppSendMessage> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("AppSendMessage");

  private static final org.apache.thrift.protocol.TField MISSION_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("missionId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField USER_APPS_FIELD_DESC = new org.apache.thrift.protocol.TField("userApps", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField RULES_FIELD_DESC = new org.apache.thrift.protocol.TField("rules", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField PLOTS_FIELD_DESC = new org.apache.thrift.protocol.TField("plots", org.apache.thrift.protocol.TType.LIST, (short)4);
  private static final org.apache.thrift.protocol.TField JOB_PRIORITY_FIELD_DESC = new org.apache.thrift.protocol.TField("jobPriority", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField TASK_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("taskInfo", org.apache.thrift.protocol.TType.MAP, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new AppSendMessageStandardSchemeFactory());
    schemes.put(TupleScheme.class, new AppSendMessageTupleSchemeFactory());
  }

  public String missionId; // required
  public List<UserApp> userApps; // required
  public List<String> rules; // required
  public List<PlotsType> plots; // required
  /**
   * 
   * @see JobPriority
   */
  public JobPriority jobPriority; // required
  public Map<String,String> taskInfo; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MISSION_ID((short)1, "missionId"),
    USER_APPS((short)2, "userApps"),
    RULES((short)3, "rules"),
    PLOTS((short)4, "plots"),
    /**
     * 
     * @see JobPriority
     */
    JOB_PRIORITY((short)5, "jobPriority"),
    TASK_INFO((short)6, "taskInfo");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // MISSION_ID
          return MISSION_ID;
        case 2: // USER_APPS
          return USER_APPS;
        case 3: // RULES
          return RULES;
        case 4: // PLOTS
          return PLOTS;
        case 5: // JOB_PRIORITY
          return JOB_PRIORITY;
        case 6: // TASK_INFO
          return TASK_INFO;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.MISSION_ID, new org.apache.thrift.meta_data.FieldMetaData("missionId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.USER_APPS, new org.apache.thrift.meta_data.FieldMetaData("userApps", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, UserApp.class))));
    tmpMap.put(_Fields.RULES, new org.apache.thrift.meta_data.FieldMetaData("rules", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.PLOTS, new org.apache.thrift.meta_data.FieldMetaData("plots", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, PlotsType.class))));
    tmpMap.put(_Fields.JOB_PRIORITY, new org.apache.thrift.meta_data.FieldMetaData("jobPriority", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, JobPriority.class)));
    tmpMap.put(_Fields.TASK_INFO, new org.apache.thrift.meta_data.FieldMetaData("taskInfo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(AppSendMessage.class, metaDataMap);
  }

  public AppSendMessage() {
  }

  public AppSendMessage(
    String missionId,
    List<UserApp> userApps,
    List<String> rules,
    List<PlotsType> plots,
    JobPriority jobPriority,
    Map<String,String> taskInfo)
  {
    this();
    this.missionId = missionId;
    this.userApps = userApps;
    this.rules = rules;
    this.plots = plots;
    this.jobPriority = jobPriority;
    this.taskInfo = taskInfo;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public AppSendMessage(AppSendMessage other) {
    if (other.isSetMissionId()) {
      this.missionId = other.missionId;
    }
    if (other.isSetUserApps()) {
      List<UserApp> __this__userApps = new ArrayList<UserApp>(other.userApps.size());
      for (UserApp other_element : other.userApps) {
        __this__userApps.add(new UserApp(other_element));
      }
      this.userApps = __this__userApps;
    }
    if (other.isSetRules()) {
      List<String> __this__rules = new ArrayList<String>(other.rules);
      this.rules = __this__rules;
    }
    if (other.isSetPlots()) {
      List<PlotsType> __this__plots = new ArrayList<PlotsType>(other.plots.size());
      for (PlotsType other_element : other.plots) {
        __this__plots.add(other_element);
      }
      this.plots = __this__plots;
    }
    if (other.isSetJobPriority()) {
      this.jobPriority = other.jobPriority;
    }
    if (other.isSetTaskInfo()) {
      Map<String,String> __this__taskInfo = new HashMap<String,String>(other.taskInfo);
      this.taskInfo = __this__taskInfo;
    }
  }

  public AppSendMessage deepCopy() {
    return new AppSendMessage(this);
  }

  @Override
  public void clear() {
    this.missionId = null;
    this.userApps = null;
    this.rules = null;
    this.plots = null;
    this.jobPriority = null;
    this.taskInfo = null;
  }

  public String getMissionId() {
    return this.missionId;
  }

  public AppSendMessage setMissionId(String missionId) {
    this.missionId = missionId;
    return this;
  }

  public void unsetMissionId() {
    this.missionId = null;
  }

  /** Returns true if field missionId is set (has been assigned a value) and false otherwise */
  public boolean isSetMissionId() {
    return this.missionId != null;
  }

  public void setMissionIdIsSet(boolean value) {
    if (!value) {
      this.missionId = null;
    }
  }

  public int getUserAppsSize() {
    return (this.userApps == null) ? 0 : this.userApps.size();
  }

  public java.util.Iterator<UserApp> getUserAppsIterator() {
    return (this.userApps == null) ? null : this.userApps.iterator();
  }

  public void addToUserApps(UserApp elem) {
    if (this.userApps == null) {
      this.userApps = new ArrayList<UserApp>();
    }
    this.userApps.add(elem);
  }

  public List<UserApp> getUserApps() {
    return this.userApps;
  }

  public AppSendMessage setUserApps(List<UserApp> userApps) {
    this.userApps = userApps;
    return this;
  }

  public void unsetUserApps() {
    this.userApps = null;
  }

  /** Returns true if field userApps is set (has been assigned a value) and false otherwise */
  public boolean isSetUserApps() {
    return this.userApps != null;
  }

  public void setUserAppsIsSet(boolean value) {
    if (!value) {
      this.userApps = null;
    }
  }

  public int getRulesSize() {
    return (this.rules == null) ? 0 : this.rules.size();
  }

  public java.util.Iterator<String> getRulesIterator() {
    return (this.rules == null) ? null : this.rules.iterator();
  }

  public void addToRules(String elem) {
    if (this.rules == null) {
      this.rules = new ArrayList<String>();
    }
    this.rules.add(elem);
  }

  public List<String> getRules() {
    return this.rules;
  }

  public AppSendMessage setRules(List<String> rules) {
    this.rules = rules;
    return this;
  }

  public void unsetRules() {
    this.rules = null;
  }

  /** Returns true if field rules is set (has been assigned a value) and false otherwise */
  public boolean isSetRules() {
    return this.rules != null;
  }

  public void setRulesIsSet(boolean value) {
    if (!value) {
      this.rules = null;
    }
  }

  public int getPlotsSize() {
    return (this.plots == null) ? 0 : this.plots.size();
  }

  public java.util.Iterator<PlotsType> getPlotsIterator() {
    return (this.plots == null) ? null : this.plots.iterator();
  }

  public void addToPlots(PlotsType elem) {
    if (this.plots == null) {
      this.plots = new ArrayList<PlotsType>();
    }
    this.plots.add(elem);
  }

  public List<PlotsType> getPlots() {
    return this.plots;
  }

  public AppSendMessage setPlots(List<PlotsType> plots) {
    this.plots = plots;
    return this;
  }

  public void unsetPlots() {
    this.plots = null;
  }

  /** Returns true if field plots is set (has been assigned a value) and false otherwise */
  public boolean isSetPlots() {
    return this.plots != null;
  }

  public void setPlotsIsSet(boolean value) {
    if (!value) {
      this.plots = null;
    }
  }

  /**
   * 
   * @see JobPriority
   */
  public JobPriority getJobPriority() {
    return this.jobPriority;
  }

  /**
   * 
   * @see JobPriority
   */
  public AppSendMessage setJobPriority(JobPriority jobPriority) {
    this.jobPriority = jobPriority;
    return this;
  }

  public void unsetJobPriority() {
    this.jobPriority = null;
  }

  /** Returns true if field jobPriority is set (has been assigned a value) and false otherwise */
  public boolean isSetJobPriority() {
    return this.jobPriority != null;
  }

  public void setJobPriorityIsSet(boolean value) {
    if (!value) {
      this.jobPriority = null;
    }
  }

  public int getTaskInfoSize() {
    return (this.taskInfo == null) ? 0 : this.taskInfo.size();
  }

  public void putToTaskInfo(String key, String val) {
    if (this.taskInfo == null) {
      this.taskInfo = new HashMap<String,String>();
    }
    this.taskInfo.put(key, val);
  }

  public Map<String,String> getTaskInfo() {
    return this.taskInfo;
  }

  public AppSendMessage setTaskInfo(Map<String,String> taskInfo) {
    this.taskInfo = taskInfo;
    return this;
  }

  public void unsetTaskInfo() {
    this.taskInfo = null;
  }

  /** Returns true if field taskInfo is set (has been assigned a value) and false otherwise */
  public boolean isSetTaskInfo() {
    return this.taskInfo != null;
  }

  public void setTaskInfoIsSet(boolean value) {
    if (!value) {
      this.taskInfo = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case MISSION_ID:
      if (value == null) {
        unsetMissionId();
      } else {
        setMissionId((String)value);
      }
      break;

    case USER_APPS:
      if (value == null) {
        unsetUserApps();
      } else {
        setUserApps((List<UserApp>)value);
      }
      break;

    case RULES:
      if (value == null) {
        unsetRules();
      } else {
        setRules((List<String>)value);
      }
      break;

    case PLOTS:
      if (value == null) {
        unsetPlots();
      } else {
        setPlots((List<PlotsType>)value);
      }
      break;

    case JOB_PRIORITY:
      if (value == null) {
        unsetJobPriority();
      } else {
        setJobPriority((JobPriority)value);
      }
      break;

    case TASK_INFO:
      if (value == null) {
        unsetTaskInfo();
      } else {
        setTaskInfo((Map<String,String>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case MISSION_ID:
      return getMissionId();

    case USER_APPS:
      return getUserApps();

    case RULES:
      return getRules();

    case PLOTS:
      return getPlots();

    case JOB_PRIORITY:
      return getJobPriority();

    case TASK_INFO:
      return getTaskInfo();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case MISSION_ID:
      return isSetMissionId();
    case USER_APPS:
      return isSetUserApps();
    case RULES:
      return isSetRules();
    case PLOTS:
      return isSetPlots();
    case JOB_PRIORITY:
      return isSetJobPriority();
    case TASK_INFO:
      return isSetTaskInfo();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof AppSendMessage)
      return this.equals((AppSendMessage)that);
    return false;
  }

  public boolean equals(AppSendMessage that) {
    if (that == null)
      return false;

    boolean this_present_missionId = true && this.isSetMissionId();
    boolean that_present_missionId = true && that.isSetMissionId();
    if (this_present_missionId || that_present_missionId) {
      if (!(this_present_missionId && that_present_missionId))
        return false;
      if (!this.missionId.equals(that.missionId))
        return false;
    }

    boolean this_present_userApps = true && this.isSetUserApps();
    boolean that_present_userApps = true && that.isSetUserApps();
    if (this_present_userApps || that_present_userApps) {
      if (!(this_present_userApps && that_present_userApps))
        return false;
      if (!this.userApps.equals(that.userApps))
        return false;
    }

    boolean this_present_rules = true && this.isSetRules();
    boolean that_present_rules = true && that.isSetRules();
    if (this_present_rules || that_present_rules) {
      if (!(this_present_rules && that_present_rules))
        return false;
      if (!this.rules.equals(that.rules))
        return false;
    }

    boolean this_present_plots = true && this.isSetPlots();
    boolean that_present_plots = true && that.isSetPlots();
    if (this_present_plots || that_present_plots) {
      if (!(this_present_plots && that_present_plots))
        return false;
      if (!this.plots.equals(that.plots))
        return false;
    }

    boolean this_present_jobPriority = true && this.isSetJobPriority();
    boolean that_present_jobPriority = true && that.isSetJobPriority();
    if (this_present_jobPriority || that_present_jobPriority) {
      if (!(this_present_jobPriority && that_present_jobPriority))
        return false;
      if (!this.jobPriority.equals(that.jobPriority))
        return false;
    }

    boolean this_present_taskInfo = true && this.isSetTaskInfo();
    boolean that_present_taskInfo = true && that.isSetTaskInfo();
    if (this_present_taskInfo || that_present_taskInfo) {
      if (!(this_present_taskInfo && that_present_taskInfo))
        return false;
      if (!this.taskInfo.equals(that.taskInfo))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(AppSendMessage other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetMissionId()).compareTo(other.isSetMissionId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMissionId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.missionId, other.missionId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUserApps()).compareTo(other.isSetUserApps());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserApps()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userApps, other.userApps);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRules()).compareTo(other.isSetRules());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRules()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.rules, other.rules);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPlots()).compareTo(other.isSetPlots());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPlots()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.plots, other.plots);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetJobPriority()).compareTo(other.isSetJobPriority());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetJobPriority()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.jobPriority, other.jobPriority);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTaskInfo()).compareTo(other.isSetTaskInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTaskInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.taskInfo, other.taskInfo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("AppSendMessage(");
    boolean first = true;

    sb.append("missionId:");
    if (this.missionId == null) {
      sb.append("null");
    } else {
      sb.append(this.missionId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("userApps:");
    if (this.userApps == null) {
      sb.append("null");
    } else {
      sb.append(this.userApps);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("rules:");
    if (this.rules == null) {
      sb.append("null");
    } else {
      sb.append(this.rules);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("plots:");
    if (this.plots == null) {
      sb.append("null");
    } else {
      sb.append(this.plots);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("jobPriority:");
    if (this.jobPriority == null) {
      sb.append("null");
    } else {
      sb.append(this.jobPriority);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("taskInfo:");
    if (this.taskInfo == null) {
      sb.append("null");
    } else {
      sb.append(this.taskInfo);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (missionId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'missionId' was not present! Struct: " + toString());
    }
    if (userApps == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'userApps' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class AppSendMessageStandardSchemeFactory implements SchemeFactory {
    public AppSendMessageStandardScheme getScheme() {
      return new AppSendMessageStandardScheme();
    }
  }

  private static class AppSendMessageStandardScheme extends StandardScheme<AppSendMessage> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, AppSendMessage struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MISSION_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.missionId = iprot.readString();
              struct.setMissionIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // USER_APPS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list90 = iprot.readListBegin();
                struct.userApps = new ArrayList<UserApp>(_list90.size);
                for (int _i91 = 0; _i91 < _list90.size; ++_i91)
                {
                  UserApp _elem92;
                  _elem92 = new UserApp();
                  _elem92.read(iprot);
                  struct.userApps.add(_elem92);
                }
                iprot.readListEnd();
              }
              struct.setUserAppsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // RULES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list93 = iprot.readListBegin();
                struct.rules = new ArrayList<String>(_list93.size);
                for (int _i94 = 0; _i94 < _list93.size; ++_i94)
                {
                  String _elem95;
                  _elem95 = iprot.readString();
                  struct.rules.add(_elem95);
                }
                iprot.readListEnd();
              }
              struct.setRulesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PLOTS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list96 = iprot.readListBegin();
                struct.plots = new ArrayList<PlotsType>(_list96.size);
                for (int _i97 = 0; _i97 < _list96.size; ++_i97)
                {
                  PlotsType _elem98;
                  _elem98 = PlotsType.findByValue(iprot.readI32());
                  struct.plots.add(_elem98);
                }
                iprot.readListEnd();
              }
              struct.setPlotsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // JOB_PRIORITY
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.jobPriority = JobPriority.findByValue(iprot.readI32());
              struct.setJobPriorityIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // TASK_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map99 = iprot.readMapBegin();
                struct.taskInfo = new HashMap<String,String>(2*_map99.size);
                for (int _i100 = 0; _i100 < _map99.size; ++_i100)
                {
                  String _key101;
                  String _val102;
                  _key101 = iprot.readString();
                  _val102 = iprot.readString();
                  struct.taskInfo.put(_key101, _val102);
                }
                iprot.readMapEnd();
              }
              struct.setTaskInfoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, AppSendMessage struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.missionId != null) {
        oprot.writeFieldBegin(MISSION_ID_FIELD_DESC);
        oprot.writeString(struct.missionId);
        oprot.writeFieldEnd();
      }
      if (struct.userApps != null) {
        oprot.writeFieldBegin(USER_APPS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.userApps.size()));
          for (UserApp _iter103 : struct.userApps)
          {
            _iter103.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.rules != null) {
        oprot.writeFieldBegin(RULES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.rules.size()));
          for (String _iter104 : struct.rules)
          {
            oprot.writeString(_iter104);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.plots != null) {
        oprot.writeFieldBegin(PLOTS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, struct.plots.size()));
          for (PlotsType _iter105 : struct.plots)
          {
            oprot.writeI32(_iter105.getValue());
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.jobPriority != null) {
        oprot.writeFieldBegin(JOB_PRIORITY_FIELD_DESC);
        oprot.writeI32(struct.jobPriority.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.taskInfo != null) {
        oprot.writeFieldBegin(TASK_INFO_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, struct.taskInfo.size()));
          for (Map.Entry<String, String> _iter106 : struct.taskInfo.entrySet())
          {
            oprot.writeString(_iter106.getKey());
            oprot.writeString(_iter106.getValue());
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class AppSendMessageTupleSchemeFactory implements SchemeFactory {
    public AppSendMessageTupleScheme getScheme() {
      return new AppSendMessageTupleScheme();
    }
  }

  private static class AppSendMessageTupleScheme extends TupleScheme<AppSendMessage> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, AppSendMessage struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.missionId);
      {
        oprot.writeI32(struct.userApps.size());
        for (UserApp _iter107 : struct.userApps)
        {
          _iter107.write(oprot);
        }
      }
      BitSet optionals = new BitSet();
      if (struct.isSetRules()) {
        optionals.set(0);
      }
      if (struct.isSetPlots()) {
        optionals.set(1);
      }
      if (struct.isSetJobPriority()) {
        optionals.set(2);
      }
      if (struct.isSetTaskInfo()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetRules()) {
        {
          oprot.writeI32(struct.rules.size());
          for (String _iter108 : struct.rules)
          {
            oprot.writeString(_iter108);
          }
        }
      }
      if (struct.isSetPlots()) {
        {
          oprot.writeI32(struct.plots.size());
          for (PlotsType _iter109 : struct.plots)
          {
            oprot.writeI32(_iter109.getValue());
          }
        }
      }
      if (struct.isSetJobPriority()) {
        oprot.writeI32(struct.jobPriority.getValue());
      }
      if (struct.isSetTaskInfo()) {
        {
          oprot.writeI32(struct.taskInfo.size());
          for (Map.Entry<String, String> _iter110 : struct.taskInfo.entrySet())
          {
            oprot.writeString(_iter110.getKey());
            oprot.writeString(_iter110.getValue());
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, AppSendMessage struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.missionId = iprot.readString();
      struct.setMissionIdIsSet(true);
      {
        org.apache.thrift.protocol.TList _list111 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.userApps = new ArrayList<UserApp>(_list111.size);
        for (int _i112 = 0; _i112 < _list111.size; ++_i112)
        {
          UserApp _elem113;
          _elem113 = new UserApp();
          _elem113.read(iprot);
          struct.userApps.add(_elem113);
        }
      }
      struct.setUserAppsIsSet(true);
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list114 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.rules = new ArrayList<String>(_list114.size);
          for (int _i115 = 0; _i115 < _list114.size; ++_i115)
          {
            String _elem116;
            _elem116 = iprot.readString();
            struct.rules.add(_elem116);
          }
        }
        struct.setRulesIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list117 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, iprot.readI32());
          struct.plots = new ArrayList<PlotsType>(_list117.size);
          for (int _i118 = 0; _i118 < _list117.size; ++_i118)
          {
            PlotsType _elem119;
            _elem119 = PlotsType.findByValue(iprot.readI32());
            struct.plots.add(_elem119);
          }
        }
        struct.setPlotsIsSet(true);
      }
      if (incoming.get(2)) {
        struct.jobPriority = JobPriority.findByValue(iprot.readI32());
        struct.setJobPriorityIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TMap _map120 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.taskInfo = new HashMap<String,String>(2*_map120.size);
          for (int _i121 = 0; _i121 < _map120.size; ++_i121)
          {
            String _key122;
            String _val123;
            _key122 = iprot.readString();
            _val123 = iprot.readString();
            struct.taskInfo.put(_key122, _val123);
          }
        }
        struct.setTaskInfoIsSet(true);
      }
    }
  }

}

