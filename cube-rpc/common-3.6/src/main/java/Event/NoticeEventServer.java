//
// *****************************************************************************
// Copyright(c) 2017-2022 Juphoon System Software Co., LTD. All rights reserved.
// *****************************************************************************
//
// Auto generated from: NoticeEvent.def
// Warning: do not edit this file.
//
package Event;

public abstract class NoticeEventServer extends Common.ObjectServer {
    public abstract void verCode_begin(Common.ServerCall __call,java.util.Map<String,String > params) throws Common.Exception;
    public abstract void verJoinRoom_begin(Common.ServerCall __call,java.util.Map<String,String > params) throws Common.Exception;
    public abstract void roomNotice_begin(Common.ServerCall __call,java.util.Map<String,String > params) throws Common.Exception;
    public abstract void recordSnapshotNotice_begin(Common.ServerCall __call,java.util.Map<String,String > params) throws Common.Exception;
    public abstract void sendOnlineMessageAndNotice_begin(Common.ServerCall __call,String receiverUri,java.util.Map<String,String > params) throws Common.Exception;
    public abstract void keepAlive_begin(Common.ServerCall __call,int type,String username,java.util.Map<String,String > params) throws Common.Exception;

    public final boolean __ex(Common.ServerCall __call,String __cmd,Common.IputStream __iput) throws Common.Exception {
        if (__cmd.compareTo("verCode.NoticeEvent.Event") == 0) { __cmd_verCode(__call,__iput);return true;}
        if (__cmd.compareTo("verJoinRoom.NoticeEvent.Event") == 0) { __cmd_verJoinRoom(__call,__iput);return true;}
        if (__cmd.compareTo("roomNotice.NoticeEvent.Event") == 0) { __cmd_roomNotice(__call,__iput);return true;}
        if (__cmd.compareTo("recordSnapshotNotice.NoticeEvent.Event") == 0) { __cmd_recordSnapshotNotice(__call,__iput);return true;}
        if (__cmd.compareTo("sendOnlineMessageAndNotice.NoticeEvent.Event") == 0) { __cmd_sendOnlineMessageAndNotice(__call,__iput);return true;}
        if (__cmd.compareTo("keepAlive.NoticeEvent.Event") == 0) { __cmd_keepAlive(__call,__iput);return true;}
        return false;
    }

    public static void verCode_end(Common.ServerCall __call,boolean __ret,java.util.Map<String,String > outParams) {
        Common.VerList __vers = __call.verList();
        Common.OputStream __oput = new Common.OputStream();
        short __ver = (__vers != null)?__vers.ver(true):0;
        switch (__ver) {
            case 0:
                __oput.write(__ret);
                Common.StrStrMap.__write(__oput,outParams);
                break;
        }
        __call.cmdResult(__ver,__oput);
    }

    public static void verJoinRoom_end(Common.ServerCall __call,boolean __ret,java.util.Map<String,String > outParams) {
        Common.VerList __vers = __call.verList();
        Common.OputStream __oput = new Common.OputStream();
        short __ver = (__vers != null)?__vers.ver(true):0;
        switch (__ver) {
            case 0:
                __oput.write(__ret);
                Common.StrStrMap.__write(__oput,outParams);
                break;
        }
        __call.cmdResult(__ver,__oput);
    }

    public static void roomNotice_end(Common.ServerCall __call,boolean __ret,java.util.Map<String,String > outParams) {
        Common.VerList __vers = __call.verList();
        Common.OputStream __oput = new Common.OputStream();
        short __ver = (__vers != null)?__vers.ver(true):0;
        switch (__ver) {
            case 0:
                __oput.write(__ret);
                Common.StrStrMap.__write(__oput,outParams);
                break;
        }
        __call.cmdResult(__ver,__oput);
    }

    public static void recordSnapshotNotice_end(Common.ServerCall __call,boolean __ret,java.util.Map<String,String > outParams) {
        Common.VerList __vers = __call.verList();
        Common.OputStream __oput = new Common.OputStream();
        short __ver = (__vers != null)?__vers.ver(true):0;
        switch (__ver) {
            case 0:
                __oput.write(__ret);
                Common.StrStrMap.__write(__oput,outParams);
                break;
        }
        __call.cmdResult(__ver,__oput);
    }

    public static void sendOnlineMessageAndNotice_end(Common.ServerCall __call,boolean __ret,java.util.Map<String,String > outParams) {
        Common.VerList __vers = __call.verList();
        Common.OputStream __oput = new Common.OputStream();
        short __ver = (__vers != null)?__vers.ver(true):0;
        switch (__ver) {
            case 0:
                __oput.write(__ret);
                Common.StrStrMap.__write(__oput,outParams);
                break;
        }
        __call.cmdResult(__ver,__oput);
    }

    public static void keepAlive_end(Common.ServerCall __call,boolean __ret,java.util.Map<String,String > outParams) {
        Common.VerList __vers = __call.verList();
        Common.OputStream __oput = new Common.OputStream();
        short __ver = (__vers != null)?__vers.ver(true):0;
        switch (__ver) {
            case 0:
                __oput.write(__ret);
                Common.StrStrMap.__write(__oput,outParams);
                break;
        }
        __call.cmdResult(__ver,__oput);
    }

    private void __cmd_verCode(Common.ServerCall __call,Common.IputStream __iput) throws Common.Exception {
        __outer: {
            Common.VerList __vers = __call.verList();
            java.util.Map<String,String > params;
            switch (__vers.ver(false)) {
                case 0:
                    params = Common.StrStrMap.__read(__iput);
                    break;
                default: break __outer;
            }
            __start(false);
            verCode_begin(__call,params);
            return;
        }
        Common.OputStream __oput = new Common.OputStream();
        __oput.write((short)1);
        __oput.write((short)0);
        __call.cmdResult(1<<16,__oput);
    }

    private void __cmd_verJoinRoom(Common.ServerCall __call,Common.IputStream __iput) throws Common.Exception {
        __outer: {
            Common.VerList __vers = __call.verList();
            java.util.Map<String,String > params;
            switch (__vers.ver(false)) {
                case 0:
                    params = Common.StrStrMap.__read(__iput);
                    break;
                default: break __outer;
            }
            __start(false);
            verJoinRoom_begin(__call,params);
            return;
        }
        Common.OputStream __oput = new Common.OputStream();
        __oput.write((short)1);
        __oput.write((short)0);
        __call.cmdResult(1<<16,__oput);
    }

    private void __cmd_roomNotice(Common.ServerCall __call,Common.IputStream __iput) throws Common.Exception {
        __outer: {
            Common.VerList __vers = __call.verList();
            java.util.Map<String,String > params;
            switch (__vers.ver(false)) {
                case 0:
                    params = Common.StrStrMap.__read(__iput);
                    break;
                default: break __outer;
            }
            __start(false);
            roomNotice_begin(__call,params);
            return;
        }
        Common.OputStream __oput = new Common.OputStream();
        __oput.write((short)1);
        __oput.write((short)0);
        __call.cmdResult(1<<16,__oput);
    }

    private void __cmd_recordSnapshotNotice(Common.ServerCall __call,Common.IputStream __iput) throws Common.Exception {
        __outer: {
            Common.VerList __vers = __call.verList();
            java.util.Map<String,String > params;
            switch (__vers.ver(false)) {
                case 0:
                    params = Common.StrStrMap.__read(__iput);
                    break;
                default: break __outer;
            }
            __start(false);
            recordSnapshotNotice_begin(__call,params);
            return;
        }
        Common.OputStream __oput = new Common.OputStream();
        __oput.write((short)1);
        __oput.write((short)0);
        __call.cmdResult(1<<16,__oput);
    }

    private void __cmd_sendOnlineMessageAndNotice(Common.ServerCall __call,Common.IputStream __iput) throws Common.Exception {
        __outer: {
            Common.VerList __vers = __call.verList();
            String receiverUri;
            java.util.Map<String,String > params;
            switch (__vers.ver(false)) {
                case 0:
                    receiverUri = __iput.readString();
                    params = Common.StrStrMap.__read(__iput);
                    break;
                default: break __outer;
            }
            __start(false);
            sendOnlineMessageAndNotice_begin(__call,receiverUri,params);
            return;
        }
        Common.OputStream __oput = new Common.OputStream();
        __oput.write((short)1);
        __oput.write((short)0);
        __call.cmdResult(1<<16,__oput);
    }

    private void __cmd_keepAlive(Common.ServerCall __call,Common.IputStream __iput) throws Common.Exception {
        __outer: {
            Common.VerList __vers = __call.verList();
            int type;
            String username;
            java.util.Map<String,String > params;
            switch (__vers.ver(false)) {
                case 0:
                    type = __iput.readInt();
                    username = __iput.readString();
                    params = Common.StrStrMap.__read(__iput);
                    break;
                default: break __outer;
            }
            __start(false);
            keepAlive_begin(__call,type,username,params);
            return;
        }
        Common.OputStream __oput = new Common.OputStream();
        __oput.write((short)1);
        __oput.write((short)0);
        __call.cmdResult(1<<16,__oput);
    }
}
