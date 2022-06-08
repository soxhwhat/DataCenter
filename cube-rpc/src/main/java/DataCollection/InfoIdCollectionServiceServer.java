//
// *****************************************************************************
// Copyright(c) 2017-2022 Juphoon System Software Co., LTD. All rights reserved.
// *****************************************************************************
//
package DataCollection;

public abstract class InfoIdCollectionServiceServer extends Common.ObjectServer {
    public abstract void getInfoId_begin(Common.ServerCall __call, Info info) throws Common.Exception;

    public final boolean __ex(Common.ServerCall __call,String __cmd,Common.IputStream __iput) throws Common.Exception {
        if (__cmd.compareTo("getInfoId.InfoIdCollectionService.DataCollection") == 0) { __cmd_getInfoId(__call,__iput);return true;}
        return false;
    }

    public static void getInfoId_end(Common.ServerCall __call,boolean __ret,String id) {
        Common.VerList __vers = __call.verList();
        Common.OputStream __oput = new Common.OputStream();
        short __ver = (__vers != null)?__vers.ver(true):0;
        switch (__ver) {
        case 0:
            __oput.write(__ret);
            __oput.write(id);
            break;
        }
        __call.cmdResult(__ver,__oput);
    }

    private void __cmd_getInfoId(Common.ServerCall __call,Common.IputStream __iput) throws Common.Exception {
        __outer: {
            Common.VerList __vers = __call.verList();
            Info info;
            switch (__vers.ver(false)) {
            case 0:
                info = Info.__read(__iput);
                break;
            default: break __outer;
            }
            __start(false);
            getInfoId_begin(__call,info);
            return;
        }
        Common.OputStream __oput = new Common.OputStream();
        __oput.write((short)1);
        __oput.write((short)0);
        __call.cmdResult(1<<16,__oput);
    }
}