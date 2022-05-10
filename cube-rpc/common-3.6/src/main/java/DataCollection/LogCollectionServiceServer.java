//
// *****************************************************************************
// Copyright(c) 2017-2022 Juphoon System Software Co., LTD. All rights reserved.
// *****************************************************************************
//
// Auto generated from: DataCollection.def
// Warning: do not edit this file.
//
package DataCollection;

public abstract class LogCollectionServiceServer extends Common.ObjectServer {
    public abstract void log_begin(Common.ServerCall __call,java.util.List<String > logList) throws Common.Exception;
    public abstract void serverLog_begin(Common.ServerCall __call,java.util.List<String > logList) throws Common.Exception;

    public final boolean __ex(Common.ServerCall __call,String __cmd,Common.IputStream __iput) throws Common.Exception {
        if (__cmd.compareTo("log.LogCollectionService.DataCollection") == 0) { __cmd_log(__call,__iput);return true;}
        if (__cmd.compareTo("log.LogCollectionService.Collection") == 0) { __cmd_log(__call,__iput);return true;}
        if (__cmd.compareTo("serverLog.LogCollectionService.DataCollection") == 0) { __cmd_serverLog(__call,__iput);return true;}
        if (__cmd.compareTo("serverLog.LogCollectionService.Collection") == 0) { __cmd_serverLog(__call,__iput);return true;}
        return false;
    }

    public static void log_end(Common.ServerCall __call,boolean __ret) {
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

    public static void serverLog_end(Common.ServerCall __call,boolean __ret) {
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

    private void __cmd_log(Common.ServerCall __call,Common.IputStream __iput) throws Common.Exception {
        __outer: {
            Common.VerList __vers = __call.verList();
            java.util.List<String > logList;
            switch (__vers.ver(false)) {
            case 0:
                logList = Common.StrVec.__read(__iput);
                break;
            default: break __outer;
            }
            __start(false);
            log_begin(__call,logList);
            return;
        }
        Common.OputStream __oput = new Common.OputStream();
        __oput.write((short)1);
        __oput.write((short)0);
        __call.cmdResult(1<<16,__oput);
    }

    private void __cmd_serverLog(Common.ServerCall __call,Common.IputStream __iput) throws Common.Exception {
        __outer: {
            Common.VerList __vers = __call.verList();
            java.util.List<String > logList;
            switch (__vers.ver(false)) {
            case 0:
                logList = Common.StrVec.__read(__iput);
                break;
            default: break __outer;
            }
            __start(false);
            serverLog_begin(__call,logList);
            return;
        }
        Common.OputStream __oput = new Common.OputStream();
        __oput.write((short)1);
        __oput.write((short)0);
        __call.cmdResult(1<<16,__oput);
    }
}