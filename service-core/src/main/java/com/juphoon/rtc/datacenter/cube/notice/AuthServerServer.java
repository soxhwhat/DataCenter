////
//// ****************************************************************
//// Copyright(c) 2008-2013 uvisual,inc. All rights reserved.
//// ****************************************************************
////
//// Auto generated from: UserAuth.def
//// Warning: do not edit this file.
////
//package Event;
//
//@SuppressWarnings("PMD")
//public abstract class AuthServerServer extends Common.ObjectServer {
//    public abstract boolean authUser(Common.ServerCall __call,String account,String random,String md5pwd) throws Common.Exception;
//
//    public final boolean __ex(Common.ServerCall __call,String __cmd,Common.IputStream __iput) throws Common.Exception {
//        if (__cmd.compareTo("authUser.AuthServer.Account") == 0) { __cmd_authUser(__call,__iput);return true;}
//        return false;
//    }
//
//    public static void authUser_end(Common.ServerCall __call,boolean __ret) {
//        Common.VerList __vers = __call.verList();
//        Common.OputStream __oput = new Common.OputStream();
//        short __ver = (__vers != null)?__vers.ver(true):0;
//        switch (__ver) {
//        case 0:
//            __oput.write(__ret);
//            break;
//        }
//        __call.cmdResult(__ver,__oput);
//    }
//
//    private void __cmd_authUser(Common.ServerCall __call,Common.IputStream __iput) throws Common.Exception {
//        __outer: {
//            Common.VerList __vers = __call.verList();
//            boolean __ret;
//            String account;
//            String random;
//            String md5pwd;
//            switch (__vers.ver(false)) {
//            case 0:
//                account = __iput.readString();
//                random = __iput.readString();
//                md5pwd = __iput.readString();
//                break;
//            default: break __outer;
//            }
//            __start(false);
//            __ret = authUser(__call,account,random,md5pwd);
//            authUser_end(__call,__ret);
//            return;
//        }
//        Common.OputStream __oput = new Common.OputStream();
//        __oput.write((short)1);
//        __oput.write((short)0);
//        __call.cmdResult(1<<16,__oput);
//    }
//}