package com.osframework.framework.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtility {

	
    public static String hashPassword(byte[] buffer) throws NoSuchAlgorithmException {
    	byte[] key = "OSRMTLOGINCONTROLLER".getBytes();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(buffer);
        return new String(base64Encode(md5.digest(key)));
    }

    public final static byte[] base64Encode(byte[] byteData) {
    	if (byteData == null)  return  null;
    	int iSrcIdx;      // index into source (byteData)
    	int iDestIdx;     // index into destination (byteDest)
    	byte byteDest[] = new byte[((byteData.length+2)/3)*4];

    	for (iSrcIdx=0, iDestIdx=0; iSrcIdx < byteData.length-2; iSrcIdx += 3) {
    	byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx] >>> 2) & 077);
    	byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx+1] >>> 4) & 017 |
    	                                        (byteData[iSrcIdx] << 4) & 077);
    	byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx+2] >>> 6) & 003 |
    	                                        (byteData[iSrcIdx+1] << 2) & 077);
    	byteDest[iDestIdx++] = (byte) (byteData[iSrcIdx+2] & 077);
    	}

    	if (iSrcIdx < byteData.length) {
    	byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx] >>> 2) & 077);
    	if (iSrcIdx < byteData.length-1) {
    	byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx+1] >>> 4) & 017 |
    	                                           (byteData[iSrcIdx] << 4) & 077);
    	byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx+1] << 2) & 077);
    	}
    	else
    	byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx] << 4) & 077);
    	}

    	for (iSrcIdx = 0; iSrcIdx < iDestIdx; iSrcIdx++) {
    	if      (byteDest[iSrcIdx] < 26)  byteDest[iSrcIdx] = (byte)(byteDest[iSrcIdx] + 'A');
    	else if (byteDest[iSrcIdx] < 52)  byteDest[iSrcIdx] = (byte)(byteDest[iSrcIdx] + 'a'-26);
    	else if (byteDest[iSrcIdx] < 62)  byteDest[iSrcIdx] = (byte)(byteDest[iSrcIdx] + '0'-52);
    	else if (byteDest[iSrcIdx] < 63)  byteDest[iSrcIdx] = '+';
    	else                              byteDest[iSrcIdx] = '/';
    	}

    	for ( ; iSrcIdx < byteDest.length; iSrcIdx++)
    byteDest[iSrcIdx] = '=';

    	return byteDest;
    	}



}
