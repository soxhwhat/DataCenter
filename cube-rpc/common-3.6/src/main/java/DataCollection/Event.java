//
// *****************************************************************************
// Copyright(c) 2017-2022 Juphoon System Software Co., LTD. All rights reserved.
// *****************************************************************************
//
// Auto generated from: ./DataCollection.def
// Warning: do not edit this file.
//
package DataCollection;

public final class Event {
    public long timestamp;
    public int type;
    public int eventNumber;
    public String uuid;
    public java.util.List<String > tags;
    public String params;
    public long domainId;
    public long appId;

    public Event() {
        this.timestamp = (long)0;
        this.type = (int)0;
        this.eventNumber = (int)0;
        this.uuid = "";
        this.tags = null;
        this.params = "";
        this.domainId = (long)0;
        this.appId = (long)0;
    }

    public Event(long timestamp,int type,int eventNumber,String uuid,java.util.List<String > tags,String params,long domainId,long appId) {
        this.timestamp = timestamp;
        this.type = type;
        this.eventNumber = eventNumber;
        this.uuid = uuid;
        this.tags = tags;
        this.params = params;
        this.domainId = domainId;
        this.appId = appId;
    }

    public static final class Holder {
        public Holder() {
        }
        public Holder(Event value) {
            this.value = value;
        }
        public Event value;
    }

    public static void __write(Common.OputStream __oput, Event __obj) {
        if (__obj == null) __obj = new Event();
        __oput.write(__obj.timestamp);
        __oput.write(__obj.type);
        __oput.write(__obj.eventNumber);
        __oput.write(__obj.uuid);
        Common.StrVec.__write(__oput,__obj.tags);
        __oput.write(__obj.params);
        __oput.write(__obj.domainId);
        __oput.write(__obj.appId);
    }

    public static Event __read(Common.IputStream __iput) throws Common.Exception {
        Event __obj = new Event();
        __obj.timestamp = __iput.readLong();
        __obj.type = __iput.readInt();
        __obj.eventNumber = __iput.readInt();
        __obj.uuid = __iput.readString();
        __obj.tags = Common.StrVec.__read(__iput);
        __obj.params = __iput.readString();
        __obj.domainId = __iput.readLong();
        __obj.appId = __iput.readLong();
        return __obj;
    }

    public static void __textWrite(Common.OputStream __oput, String __name, Event __obj) {
        if (__obj == null) return;
        __oput.textStart(__name);
        __oput.textWrite("timestamp",__obj.timestamp);
        __oput.textWrite("type",__obj.type);
        __oput.textWrite("eventNumber",__obj.eventNumber);
        __oput.textWrite("uuid",__obj.uuid);
        Common.StrVec.__textWrite(__oput,"tags",__obj.tags);
        __oput.textWrite("params",__obj.params);
        __oput.textWrite("domainId",__obj.domainId);
        __oput.textWrite("appId",__obj.appId);
        __oput.textEnd();
    }

    public static Event __textRead(Common.IputStream __iput,String __name,int __idx) {
        if (!__iput.textStart(__name,__idx)) return null;
        Event __obj = new Event();
        __obj.timestamp = __iput.textReadLong("timestamp",0,(long)0);
        __obj.type = __iput.textReadInt("type",0,(int)0);
        __obj.eventNumber = __iput.textReadInt("eventNumber",0,(int)0);
        __obj.uuid = __iput.textReadString("uuid",0,"");
        __obj.tags = Common.StrVec.__textRead(__iput,"tags",0);
        __obj.params = __iput.textReadString("params",0,"");
        __obj.domainId = __iput.textReadLong("domainId",0,(long)0);
        __obj.appId = __iput.textReadLong("appId",0,(long)0);
        __iput.textEnd();
        return __obj;
    }
}
