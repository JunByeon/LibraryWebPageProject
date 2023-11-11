package com.luv2code.springbootlibrary.utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/*
Okta를 사용하여
login을 한 계정마다 RequestHeader의 필드에
특정 attribute를 붙인 후
이것을 백엔드에서 Token으로 받은 후
해석하여
Authentication과 Authorization은 물론
admin user와 general user까지 구분 시킬 수 있다
* */
public class ExtractJWT {

    public static String payloadJWTExtraction(String token, String extraction) {

        token.replace("Bearer ", "");

        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));

        String[] entries = payload.split(",");
        Map<String, String> map = new HashMap<String, String>();

        for (String entry : entries) {
            String[] keyValue = entry.split(":");
            if (keyValue[0].equals(extraction)){
                int remove = 1;
                if (keyValue[1].endsWith("}")) {
                    remove = 2;
                }
                keyValue[1] = keyValue[1].substring(0, keyValue[1].length() - remove);
                keyValue[1] = keyValue[1].substring(1);

                map.put(keyValue[0], keyValue[1]);
            }

        }
        if (map.containsKey(extraction)) {
            return map.get(extraction);
        }
        return null;
    }

}
