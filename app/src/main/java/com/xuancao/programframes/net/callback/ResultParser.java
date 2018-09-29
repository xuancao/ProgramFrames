package com.xuancao.programframes.net.callback;

import com.xuancao.programframes.net.callback.Result;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class ResultParser {

    public static Object checkResponse(String jsonString) {
        Object result = null;
        if (jsonString != null) {
            jsonString = jsonString.trim();
            if (jsonString.startsWith("{")) {
                try {
                    result = new JSONTokener(jsonString).nextValue();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static Result parseResponse(String jsonString) {
        Object jsonResponse = checkResponse(jsonString);
        Result mResult = null;
        if (jsonResponse instanceof JSONObject) {
            JSONObject resultJson = (JSONObject) jsonResponse;
            try {
                mResult = parse(resultJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mResult;
    }

    public static Result parse(JSONObject json) throws JSONException {
        Result mResult = new Result();

        if (json.has("retcode")) {
            mResult.setRetcode(json.optInt("retcode"));
        }
        if (json.has("retmsg")) {
            mResult.setMessage(json.optString("retmsg"));
        }
        if (json.has("result")) {
            mResult.setResult(json.optString("result"));
        }
        return mResult;
    }
}
