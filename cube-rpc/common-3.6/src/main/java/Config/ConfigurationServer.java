//
// *****************************************************************************
// Copyright(c) 2017-2022 Juphoon System Software Co., LTD. All rights reserved.
// *****************************************************************************
//
// Auto generated from: ClientConfig.def
// Warning: do not edit this file.
//
package Config;

public abstract class ConfigurationServer extends Common.ObjectServer {
    public abstract void getClientConfig_begin(Common.ServerCall __call, ConfigInfo configInfo) throws Common.Exception;

    public final boolean __ex(Common.ServerCall __call,String __cmd,Common.IputStream __iput) throws Common.Exception {
        if (__cmd.compareTo("getClientConfig.Configuration.Config") == 0) { __cmd_getClientConfig(__call,__iput);return true;}
        return false;
    }

    public static void getClientConfig_end(Common.ServerCall __call,boolean __ret,java.util.Map<String,String > configMap,long timestamps) {
        Common.VerList __vers = __call.verList();
        Common.OputStream __oput = new Common.OputStream();
        short __ver = (__vers != null)?__vers.ver(true):0;
        switch (__ver) {
        case 0:
            __oput.write(__ret);
            Common.StrStrMap.__write(__oput,configMap);
            __oput.write(timestamps);
            break;
        }
        __call.cmdResult(__ver,__oput);
    }

    private void __cmd_getClientConfig(Common.ServerCall __call,Common.IputStream __iput) throws Common.Exception {
        __outer: {
            Common.VerList __vers = __call.verList();
            ConfigInfo configInfo;
            switch (__vers.ver(false)) {
            case 0:
                configInfo = ConfigInfo.__read(__iput);
                break;
            default: break __outer;
            }
            __start(false);
            getClientConfig_begin(__call,configInfo);
            return;
        }
        Common.OputStream __oput = new Common.OputStream();
        __oput.write((short)1);
        __oput.write((short)0);
        __call.cmdResult(1<<16,__oput);
    }
}
