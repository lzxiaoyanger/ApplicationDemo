//package com.zz.personal.Utils;
//
//import com.zbj.commons.exception.CodeMeta;
//import com.zhubajie.xxh.common.exception.BizException;
//import org.springframework.util.StringUtils;
//
//import java.util.List;
//
//public class VerifyUtil {
//
//    public static void isNull(Object obj, CodeMeta codeMeta) throws BizException {
//        if (null == obj) {
//            throw new BizException(codeMeta.getMsg());
//        }
//    }
//
//    public static void isEmpty(String str, CodeMeta codeMeta) throws BizException {
//        if (StringUtils.isEmpty(str)) {
//            throw new BizException(codeMeta.getMsg());
//        }
//    }
//
//    public static void isEmpty(Integer integer, CodeMeta codeMeta) throws BizException {
//        if (null == integer || integer.equals(0)) {
//            throw new BizException(codeMeta.getMsg());
//        }
//    }
//
//    public static void isEmpty(List list, CodeMeta codeMeta) throws BizException {
//        if (null == list || list.isEmpty()) {
//            throw new BizException(codeMeta.getMsg());
//        }
//    }
//
//}
