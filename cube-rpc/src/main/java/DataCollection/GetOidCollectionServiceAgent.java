//
// *****************************************************************************
// Copyright(c) 2017-2019 Juphoon System Software Co., LTD. All rights reserved.
// *****************************************************************************
//
// Auto generated from: .\DataCollection.def
// Warning: do not edit this file.
//
package DataCollection;

public final class GetOidCollectionServiceAgent {
    public final Common.ObjectAgent __agent;

    public GetOidCollectionServiceAgent(Common.ObjectAgent agent) {
        this.__agent = agent;
    }

    public final boolean gteOid(Common.Holder.String oid,final Common.CallParams __params) {
        return gteOid(__agent,oid,__params);
    }

    public final void gteOid_begin(final Common.AgentAsync __async,final Common.CallParams __params,final Object __userdata) {
        gteOid_begin(__agent,__async,__params,__userdata);
    }

    public static final boolean gteOid(final Common.ObjectAgent __agent,Common.Holder.String oid,final Common.CallParams __params) {
        try {
        int __loop = 0;
        while (true) {
            Common.OputStream __oput = new Common.OputStream();
            Common.VerList __vers = __agent.verList("gteOid.GetOidCollectionService.DataCollection");
            short __ver = 0;
            if (__vers != null) {
                __ver = __vers.ver(true);
                if (__ver > 0) __ver = 0;
            }
            switch (__ver) {
            case 0:
                __oput.write((short)1);
                __oput.write((short)__ver);
                break;
            default: throw new Common.Exception("agent-error:ver error");
            }
            Common.IputStream.Holder __iput = new Common.IputStream.Holder();
            int __rslt = __agent.ex_sync("gteOid.GetOidCollectionService.DataCollection",__oput,__iput,__params);
            if ((__rslt>>16) != 0) {
                assert((__rslt>>16) == 1); 
                __loop ++;if (__loop >= 3) throw new Common.Exception("agent-error:vers error");
                continue;
            }
            boolean __ret;
            switch (__rslt) {
            case 0:
                __ret = __iput.value.readBool();
                if (oid == null) oid = new Common.Holder.String();oid.value = __iput.value.readString();
                break;
            default: throw new Common.Exception("agent-error:vers error");
            }
            Common.ObjectAgent.processFinal(__iput.value);
            return __ret;
        }
        }
        catch (Common.Exception ex) {
            Common.ObjectAgent.processFinal(ex);
            if (oid != null) oid.value = "";
            return false;
        }
    }

    public static final void gteOid_begin(final Common.ObjectAgent __agent,final Common.AgentAsync __async,final Common.CallParams __params,final Object __userdata) {
        (new Common.AgentAsync() {
            public final void start() {
                try {
                    Common.OputStream __oput = new Common.OputStream();
                    Common.VerList __vers = __agent.verList("gteOid.GetOidCollectionService.DataCollection");
                    short __ver = 0;
                    if (__vers != null) {
                        __ver = __vers.ver(true);
                        if (__ver > 0) __ver = 0;
                    }
                    switch (__ver) {
                    case 0:
                        __oput.write((short)1);
                        __oput.write((short)__ver);
                        break;
                    default: throw new Common.Exception("agent-error:vers error");
                    }
                    __agent.ex_async(this,"gteOid.GetOidCollectionService.DataCollection",__oput,__params,0);
                }
                catch (Common.Exception ex) {
                    if (__async != null) Common.ObjectAgent.throwException(__async,ex,__userdata);
                }
            }
            public final void cmdResult(int __rslt,Common.IputStream __iput,Object __userdata) {
                if ((__rslt>>16)!=1) {
                    if (__async != null) __async.cmdResult(__rslt,__iput,__userdata);
                    return;
                }
                __loop ++;
                if (__loop >= 3) {
                    if (__async != null) Common.ObjectAgent.throwException(__async,new Common.Exception("agent-error:vers error"),__userdata);
                    return;
                }
                start();
            }
            private int __loop = 0;
        }).start();
    }

    public static final boolean gteOid_end(int __rslt,Common.IputStream __iput,Common.Holder.String oid) {
        try {
            Common.ObjectAgent.processFirst(__rslt,__iput);
            assert((__rslt>>16) == 0);
            boolean __ret;
            switch (__rslt) {
            case 0:
                __ret = __iput.readBool();
                if (oid == null) oid = new Common.Holder.String();oid.value = __iput.readString();
                break;
            default: throw new Common.Exception("agent-error:vers error");
            }
            Common.ObjectAgent.processFinal(__iput);
            return __ret;
        }
        catch (Common.Exception ex) {
            Common.ObjectAgent.processFinal(ex);
            if (oid != null) oid.value = "";
            return false;
        }
    }
}
