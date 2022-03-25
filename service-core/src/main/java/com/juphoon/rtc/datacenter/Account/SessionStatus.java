//
// ****************************************************************
// Copyright(c) 2008-2013 uvisual,inc. All rights reserved.
// ****************************************************************
//
// Auto generated from: AccountPub1.def
// Warning: do not edit this file.
//
package com.juphoon.rtc.datacenter.Account;

public final class SessionStatus {
    public static final class Holder {
        public Holder() {
        }
        public Holder(java.util.Map<String,Integer > value) {
            this.value = value;
        }
        public java.util.Map<String,Integer > value;
    }

    public static void __write(Common.OputStream __oput,java.util.Map<String,Integer > __obj) {
        if (__obj == null)
            __oput.write((int)0);
        else {
            __oput.write((int)__obj.size());
            for(java.util.Map.Entry<String,Integer > m:__obj.entrySet()) {
                __oput.write(m.getKey());
                __oput.write(m.getValue());
            }
        }
    }

    public static java.util.Map<String,Integer > __read(Common.IputStream __iput) throws Common.Exception {
        java.util.Map<String,Integer > __obj = new java.util.HashMap<String,Integer >();
        int size = __iput.readInt();
        for (int i=0;i<size;i++) {
            String k = __iput.readString();
            Integer v = __iput.readInt();
            __obj.put(k,v);
        }
        return __obj;
    }

    public static void __textWrite(Common.OputStream __oput,String __name,java.util.Map<String,Integer > __obj) {
        if (__obj == null) return;
        __oput.textStart(__name);
        for(java.util.Map.Entry<String,Integer > m:__obj.entrySet())
            __oput.textWrite(m.getKey(),m.getValue());
        __oput.textEnd();
    }

    public static java.util.Map<String,Integer > __textRead(Common.IputStream __iput,String __name,int __idx) {
        java.util.Map<String,Integer > __obj = new java.util.HashMap<String,Integer >();
        if (__iput.textStart(__name,__idx)) {
            String[] ks = __iput.textList();
            for (String k:ks) {
                Integer v;
                if ((v = __iput.textReadInt(k,0,null)) != null)
                    __obj.put(k,v);
            }
            __iput.textEnd();
        }
        return __obj;
    }
}
