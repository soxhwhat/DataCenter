//
// *****************************************************************************
// Copyright(c) 2017-2022 Juphoon System Software Co., LTD. All rights reserved.
// *****************************************************************************
//
// Auto generated from: ./DataCollection.def
// Warning: do not edit this file.
//
package DataCollection;

public abstract class StatusCollectionServiceServer extends Common.ObjectServer {
    public abstract void putStatus_begin(Common.ServerCall __call, FlowStatus eventPO) throws Common.Exception;
    public abstract void endStatus_begin(Common.ServerCall __call, FlowStatus eventPO) throws Common.Exception;
    public abstract void putStatusJson_begin(Common.ServerCall __call, FlowStatusJson eventPO) throws Common.Exception;
    public abstract void endStatusJson_begin(Common.ServerCall __call, FlowStatusJson eventPO) throws Common.Exception;
    public abstract void putStatusJson2_begin(Common.ServerCall __call, String topic, FlowStatusJson eventPO) throws Common.Exception;
    public abstract void putStatusListJson2_begin(Common.ServerCall __call,String topic,java.util.List<FlowStatusJson > flowList) throws Common.Exception;

    public final boolean __ex(Common.ServerCall __call,String __cmd,Common.IputStream __iput) throws Common.Exception {
        if (__cmd.compareTo("putStatus.StatusCollectionService.DataCollection") == 0) { __cmd_putStatus(__call,__iput);return true;}
        if (__cmd.compareTo("endStatus.StatusCollectionService.DataCollection") == 0) { __cmd_endStatus(__call,__iput);return true;}
        if (__cmd.compareTo("putStatusJson.StatusCollectionService.DataCollection") == 0) { __cmd_putStatusJson(__call,__iput);return true;}
        if (__cmd.compareTo("endStatusJson.StatusCollectionService.DataCollection") == 0) { __cmd_endStatusJson(__call,__iput);return true;}
        if (__cmd.compareTo("putStatusJson2.StatusCollectionService.DataCollection") == 0) { __cmd_putStatusJson2(__call,__iput);return true;}
        if (__cmd.compareTo("putStatusListJson2.StatusCollectionService.DataCollection") == 0) { __cmd_putStatusListJson2(__call,__iput);return true;}
        return false;
    }

    public static void putStatus_end(Common.ServerCall __call,boolean __ret) {
        Common.VerList __vers = __call.verList();
        Common.OputStream __oput = new Common.OputStream();
        short __ver = (__vers != null)?__vers.ver(true):0;
        switch (__ver) {
        case 0:
            __oput.write(__ret);
            break;
        }
        __call.cmdResult(__ver,__oput);
    }

    public static void endStatus_end(Common.ServerCall __call,boolean __ret) {
        Common.VerList __vers = __call.verList();
        Common.OputStream __oput = new Common.OputStream();
        short __ver = (__vers != null)?__vers.ver(true):0;
        switch (__ver) {
        case 0:
            __oput.write(__ret);
            break;
        }
        __call.cmdResult(__ver,__oput);
    }

    public static void putStatusJson_end(Common.ServerCall __call,boolean __ret) {
        Common.VerList __vers = __call.verList();
        Common.OputStream __oput = new Common.OputStream();
        short __ver = (__vers != null)?__vers.ver(true):0;
        switch (__ver) {
        case 0:
            __oput.write(__ret);
            break;
        }
        __call.cmdResult(__ver,__oput);
    }

    public static void endStatusJson_end(Common.ServerCall __call,boolean __ret) {
        Common.VerList __vers = __call.verList();
        Common.OputStream __oput = new Common.OputStream();
        short __ver = (__vers != null)?__vers.ver(true):0;
        switch (__ver) {
        case 0:
            __oput.write(__ret);
            break;
        }
        __call.cmdResult(__ver,__oput);
    }

    public static void putStatusJson2_end(Common.ServerCall __call,boolean __ret) {
        Common.VerList __vers = __call.verList();
        Common.OputStream __oput = new Common.OputStream();
        short __ver = (__vers != null)?__vers.ver(true):0;
        switch (__ver) {
            case 0:
                __oput.write(__ret);
                break;
        }
        __call.cmdResult(__ver,__oput);
    }

    public static void putStatusListJson2_end(Common.ServerCall __call,boolean __ret) {
        Common.VerList __vers = __call.verList();
        Common.OputStream __oput = new Common.OputStream();
        short __ver = (__vers != null)?__vers.ver(true):0;
        switch (__ver) {
            case 0:
                __oput.write(__ret);
                break;
        }
        __call.cmdResult(__ver,__oput);
    }

    private void __cmd_putStatus(Common.ServerCall __call,Common.IputStream __iput) throws Common.Exception {
        __outer: {
            Common.VerList __vers = __call.verList();
            FlowStatus eventPO;
            switch (__vers.ver(false)) {
            case 0:
                eventPO = FlowStatus.__read(__iput);
                break;
            default: break __outer;
            }
            __start(false);
            putStatus_begin(__call,eventPO);
            return;
        }
        Common.OputStream __oput = new Common.OputStream();
        __oput.write((short)1);
        __oput.write((short)0);
        __call.cmdResult(1<<16,__oput);
    }

    private void __cmd_endStatus(Common.ServerCall __call,Common.IputStream __iput) throws Common.Exception {
        __outer: {
            Common.VerList __vers = __call.verList();
            FlowStatus eventPO;
            switch (__vers.ver(false)) {
            case 0:
                eventPO = FlowStatus.__read(__iput);
                break;
            default: break __outer;
            }
            __start(false);
            endStatus_begin(__call,eventPO);
            return;
        }
        Common.OputStream __oput = new Common.OputStream();
        __oput.write((short)1);
        __oput.write((short)0);
        __call.cmdResult(1<<16,__oput);
    }

    private void __cmd_putStatusJson(Common.ServerCall __call,Common.IputStream __iput) throws Common.Exception {
        __outer: {
            Common.VerList __vers = __call.verList();
            FlowStatusJson eventPO;
            switch (__vers.ver(false)) {
            case 0:
                eventPO = FlowStatusJson.__read(__iput);
                break;
            default: break __outer;
            }
            __start(false);
            putStatusJson_begin(__call,eventPO);
            return;
        }
        Common.OputStream __oput = new Common.OputStream();
        __oput.write((short)1);
        __oput.write((short)0);
        __call.cmdResult(1<<16,__oput);
    }

    private void __cmd_endStatusJson(Common.ServerCall __call,Common.IputStream __iput) throws Common.Exception {
        __outer: {
            Common.VerList __vers = __call.verList();
            FlowStatusJson eventPO;
            switch (__vers.ver(false)) {
            case 0:
                eventPO = FlowStatusJson.__read(__iput);
                break;
            default: break __outer;
            }
            __start(false);
            endStatusJson_begin(__call,eventPO);
            return;
        }
        Common.OputStream __oput = new Common.OputStream();
        __oput.write((short)1);
        __oput.write((short)0);
        __call.cmdResult(1<<16,__oput);
    }

    private void __cmd_putStatusJson2(Common.ServerCall __call,Common.IputStream __iput) throws Common.Exception {
        __outer: {
            Common.VerList __vers = __call.verList();
            String topic;
            DataCollection.FlowStatusJson eventPO;
            switch (__vers.ver(false)) {
                case 0:
                    topic = __iput.readString();
                    eventPO = DataCollection.FlowStatusJson.__read(__iput);
                    break;
                default: break __outer;
            }
            __start(false);
            putStatusJson2_begin(__call,topic,eventPO);
            return;
        }
        Common.OputStream __oput = new Common.OputStream();
        __oput.write((short)1);
        __oput.write((short)0);
        __call.cmdResult(1<<16,__oput);
    }

    private void __cmd_putStatusListJson2(Common.ServerCall __call,Common.IputStream __iput) throws Common.Exception {
        __outer: {
            Common.VerList __vers = __call.verList();
            String topic;
            java.util.List<DataCollection.FlowStatusJson > flowList;
            switch (__vers.ver(false)) {
                case 0:
                    topic = __iput.readString();
                    flowList = DataCollection.FlowList.__read(__iput);
                    break;
                default: break __outer;
            }
            __start(false);
            putStatusListJson2_begin(__call,topic,flowList);
            return;
        }
        Common.OputStream __oput = new Common.OputStream();
        __oput.write((short)1);
        __oput.write((short)0);
        __call.cmdResult(1<<16,__oput);
    }
}
