//
// *****************************************************************************
// Copyright(c) 2017-2022 Juphoon System Software Co., LTD. All rights reserved.
// *****************************************************************************
//
// Auto generated from: DataCollection.def
// Warning: do not edit this file.
//
package DataCollection;

public final class EventList {
    public static final class Holder {
        public Holder() {
        }
        public Holder(java.util.List<Event > value) {
            this.value = value;
        }
        public java.util.List<Event > value;
    }

    public static void __write(Common.OputStream __oput,java.util.List<Event > __obj) {
        if (__obj == null)
            __oput.write((int)0);
        else {
            __oput.write((int)__obj.size());
            for(Event m:__obj)
                Event.__write(__oput,m);
        }
    }

    public static java.util.List<Event > __read(Common.IputStream __iput) throws Common.Exception {
        java.util.List<Event > __obj = new java.util.ArrayList<Event >();
        int size = __iput.readInt();
        for (int i=0;i<size;i++) {
            Event m = Event.__read(__iput);
            __obj.add(m);
        }
        return __obj;
    }

    public static void __textWrite(Common.OputStream __oput,String __name,java.util.List<Event > __obj) {
        if (__obj == null) return;
        __oput.textArray(__name);
        for(Event m:__obj) {
            Event.__textWrite(__oput,__name,m);
        }
    }

    public static java.util.List<Event > __textRead(Common.IputStream __iput, String __name, int __idx) {
        java.util.List<Event > __obj = new java.util.ArrayList<Event >();
        int size = __iput.textCount(__name);
        for (int i=0;i<size;i++) {
            Event m;
            if ((m = Event.__textRead(__iput,__name,i)) != null)
                __obj.add(m);
        }
        return __obj;
    }
}

