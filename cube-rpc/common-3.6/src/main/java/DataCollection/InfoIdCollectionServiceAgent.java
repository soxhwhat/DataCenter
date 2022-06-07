//
// *****************************************************************************
// Copyright(c) 2017-2022 Juphoon System Software Co., LTD. All rights reserved.
// *****************************************************************************
//
// Auto generated from: ./DataCollection.def
// Warning: do not edit this file.
//
package DataCollection;

public final class InfoIdCollectionServiceAgent {
    public final Common.ObjectAgent __agent;

    public InfoIdCollectionServiceAgent(Common.ObjectAgent agent) {
        this.__agent = agent;
    }

    public final boolean getInfoId(final Info info,Common.Holder.String id,final Common.CallParams __params) {
        return getInfoId(__agent,info,id,__params);
    }

    public final void getInfoId_begin(final Common.AgentAsync __async,final Info info,final Common.CallParams __params,final Object __userdata) {
        getInfoId_begin(__agent,__async,info,__params,__userdata);
    }

    public static final boolean getInfoId(final Common.ObjectAgent __agent,final Info info,Common.Holder.String id,final Common.CallParams __params) {
        try {
            int __loop = 0;
            while (true) {
                Common.OputStream __oput = new Common.OputStream();
                Common.VerList __vers = __agent.verList("getInfoId.InfoIdCollectionService.DataCollection");
                short __ver = 0;
                if (__vers != null) {
                    __ver = __vers.ver(true);
                    if (__ver > 0) __ver = 0;
                }
                switch (__ver) {
                case 0:
                    __oput.write((short)1);
                    __oput.write((short)__ver);
                    Info.__write(__oput,info);
                    break;
                default:
                    throw new Common.CallException(Common.ObjectAgent.versionError("getInfoId.InfoIdCollectionService.DataCollection"));
                }
                Common.IputStream.Holder __iput = new Common.IputStream.Holder();
                Common.CallError.Holder __error = new Common.CallError.Holder();
                int __rslt = __agent.ex_sync("getInfoId.InfoIdCollectionService.DataCollection",__oput,__iput,__params,__error);
                if (__rslt == -1)
                    throw new Common.CallException(__error.value);
                if ((__rslt>>16) != 0) {
                    assert((__rslt>>16) == 1);
                    __loop ++;
                    if (__loop >= 3)
                        throw new Common.CallException(Common.ObjectAgent.versionError("getInfoId.InfoIdCollectionService.DataCollection"));
                    continue;
                }
                boolean __ret;
                switch (__rslt) {
                case 0:
                    __ret = __iput.value.readBool();
                    if (id == null) id = new Common.Holder.String();id.value = __iput.value.readString();
                    break;
                default:
                    throw new Common.CallException(Common.ObjectAgent.versionError("getInfoId.InfoIdCollectionService.DataCollection"));
                }
                Common.ObjectAgent.processFinal(__iput.value);
                return __ret;
            }
        } catch (Common.CallException ex) {
            Common.ObjectAgent.processFinal(ex);
            if (id != null) id.value = "";
            return false;
        } catch (Common.Exception ex) {
            Common.ObjectAgent.processFinal(ex);
            if (id != null) id.value = "";
            return false;
        }
    }

    public static final void getInfoId_begin(final Common.ObjectAgent __agent,final Common.AgentAsync __async,final Info info,final Common.CallParams __params,final Object __userdata) {
        (new Common.AgentAsync() {
            public final void start() {
                try {
                    Common.OputStream __oput = new Common.OputStream();
                    Common.VerList __vers = __agent.verList("getInfoId.InfoIdCollectionService.DataCollection");
                    short __ver = 0;
                    if (__vers != null) {
                        __ver = __vers.ver(true);
                        if (__ver > 0) __ver = 0;
                    }
                    switch (__ver) {
                    case 0:
                        __oput.write((short)1);
                        __oput.write((short)__ver);
                        Info.__write(__oput,info);
                        break;
                    default:
                        throw new Common.CallException(Common.ObjectAgent.versionError("getInfoId.InfoIdCollectionService.DataCollection"));
                    }
                    __agent.ex_async(this,"getInfoId.InfoIdCollectionService.DataCollection",__oput,__params,0);
                } catch (Common.CallException ex) {
                    if (__async != null)
                        Common.ObjectAgent.throwException(__async,ex,__userdata);
                } catch (Common.Exception ex) {
                    if (__async != null)
                        Common.ObjectAgent.throwException(__async,ex,__userdata);
                }
            }
            public final void cmdResult(int __rslt,Common.IputStream __iput,Object __userdata) {
                if ((__rslt>>16)!=1) {
                    if (__async != null)
                        __async.cmdResult(__rslt,__iput,__userdata);
                    return;
                }
                __loop ++;
                if (__loop >= 3) {
                    if (__async != null) {
                        try {
                            Common.ObjectAgent.throwException(__async,new Common.CallException(Common.ObjectAgent.versionError("getInfoId.InfoIdCollectionService.DataCollection")),__userdata);
                        } catch (Common.Exception ex) {
                            Common.ObjectAgent.throwException(__async,ex,__userdata);
                        }
                    }
                    return;
                }
                start();
            }
            private int __loop = 0;
        }).start();
    }

    public static final boolean getInfoId_end(int __rslt,Common.IputStream __iput,Common.Holder.String id) {
        try {
            Common.CallError.Holder __error = new Common.CallError.Holder();
            if (Common.ObjectAgent.processException(__rslt,__iput,__error))
                throw new Common.CallException(__error.value);
            assert((__rslt>>16) == 0);
            boolean __ret;
            switch (__rslt) {
                case 0:
                    __ret = __iput.readBool();
                    if (id == null) id = new Common.Holder.String();id.value = __iput.readString();
                    break;
            default:
                throw new Common.CallException(Common.ObjectAgent.versionError("getInfoId.InfoIdCollectionService.DataCollection"));
            }
            Common.ObjectAgent.processFinal(__iput);
            return __ret;
        } catch (Common.CallException ex) {
            Common.ObjectAgent.processFinal(ex);
            if (id != null) id.value = "";
            return false;
        } catch (Common.Exception ex) {
            Common.ObjectAgent.processFinal(ex);
            if (id != null) id.value = "";
            return false;
        }
    }
}
