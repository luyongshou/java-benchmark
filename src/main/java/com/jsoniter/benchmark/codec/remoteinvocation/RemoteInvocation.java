package com.jsoniter.benchmark.codec.remoteinvocation;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RemoteInvocation implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(RemoteInvocation.class);
    private static final long serialVersionUID = 6876024250231820554L;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] arguments;
    private Map<String, Serializable> attributes;
    private Map<String, String> header;

    public RemoteInvocation() {
    }

    public static RemoteInvocation createRemoteInvocation() {
        String content = "{\"methodName\":\"reverse\",\"parameterTypes\":[\"java.util.List\"],\"arguments\":[null],\"attributes\":{\"iris_rpc_http_headers\":{\"a00\":\"4oQ5zMhWk0e3IQi6XI2Gz0iv3zAz_WiZPckrMAhFiB4wMDAwTDZZVFlFWTVGVVpQWEUwMDAwYDI5MTAwMzMxNDY3NjE4MDhgMDAwMEw2WVRZRVk1RlVaUFhFMDAwMGA5OWVhNzY1NS0wMGEyLTRiZGEtYjIzYy0xOWFkZTM3ZWE1NzRgMWBgZTZiNThiZThhZjk1ZTc4ZWFmZTVhMjgzZTdhZWExZTc5MDg2ZTU5MTk4MzEzMTMxYGBgMTUyNTY5NTQ2MDIxMDYzODg1OGBmYWxzZWBgMTY4ODQ1NDEzMzUxNmB5bXNzZXM6NWZlMWJiZmJhOWIxNjdlNGMyMThkZDQ2YzNmYTI1OTlgZGl3b3JrYA\",\"multilingualFlag\":\"true\",\"traceId\":\"4eff1f3a5a078c02\",\"pSpanId\":\"@b7db01dd72ab7be\",\"languages\":\"1_3-2_1-3_1\",\"newArch\":\"true\",\"timezone\":\"UTC+08:00\",\"n_f_f\":\"true\",\"language\":\"001\",\"locale\":\"zh_CN\",\"defaultOrg\":\"1530661876902920193\",\"spanId\":\"9f2c16a786f0cc39\",\"X-callId\":\"yonbip-fi-ctmcmp\",\"x-forwarded-clientip\":\"10.6.213.250\",\"x-biz-outForwarded\":\"false\",\"yht_access_token\":\"bttQVA2ZCtTQ0s1NTlEMkVROTBhMmN4RkR0TWFXbVJXMzB2Z2E3ZHVFdllYVXRvUHJKYVNNZkc3T3E1Q1hRdTRDWV9fYmlwLXRlc3QueXl1YXAuY29t__efe01ac3165cf69d3c729add33bd2680_1688454133508dccore0iuap-apcom-workbench1257a4c4YT\"},\"serviceCode\":\"ficmp0026\",\"rpcDomainContextKey\":\"yonbip-fi-ctmcmp\",\"ytsParamIgnore\":true,\"RPCToken\":\"925ff2a2-991e-429f-9309-e9cc82f5fa4f\",\"serverProviderId\":\"c87e2267-1001-4c70-bb2a-ab41f3b81aa3\",\"providerId\":\"c87e2267-1001-4c70-bb2a-ab41f3b81aa3\",\"ServiceInterface\":\"com.yonyoucloud.fi.fieaai.busievent.api.v1.IBusiReceiveService\",\"appId\":\"yonbip-fi-ctmcmp\",\"rpcToken\":\"bttQVA2ZCtTQ0s1NTlEMkVROTBhMmN4RkR0TWFXbVJXMzB2Z2E3ZHVFdllYVXRvUHJKYVNNZkc3T3E1Q1hRdTRDWV9fYmlwLXRlc3QueXl1YXAuY29t__efe01ac3165cf69d3c729add33bd2680_1688454133508dccore0iuap-apcom-workbench1257a4c4YT\",\"serverAppId\":\"yonbip-fi-eeac\",\"alias\":\"\",\"rpc.destInst\":\"yonbip-fi-eeac@c87e2267-1001-4c70-bb2a-ab41f3b81aa3@test\",\"dubboFuse\":true}}";
        System.out.println("len:" + content.length());
        RemoteInvocation remoteInvocation = JSON.parseObject(content, RemoteInvocation.class);
        return remoteInvocation;
    }

    public RemoteInvocation(String methodName, Class<?>[] parameterTypes, Object[] arguments) {
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.arguments = arguments;
    }

    public Map<String, String> getHeader() {
        return this.header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return this.parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArguments() {
        return this.arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public void addAttribute(String key, Serializable value) {
        if (this.attributes == null) {
            this.attributes = new HashMap();
        }

        if (this.attributes.containsKey(key)) {
            logger.error("key:{} 被覆盖，请检查", key);
        }

        this.attributes.put(key, value);
    }

    public void removeAttribute(String key) {
        this.attributes.remove(key);
    }

    public Serializable getAttribute(String key) {
        return this.attributes == null ? null : (Serializable) this.attributes.get(key);
    }

    public void setAttributes(Map<String, Serializable> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Serializable> getAttributes() {
        return this.attributes;
    }

    public String toString() {
        return "RemoteInvocation: method name '" + this.methodName + "'; parameter types " + (null == this.parameterTypes ? "" : this.parameterTypes);
    }

    public Method findMethod(Object targetObject) throws NoSuchMethodException, SecurityException {
        return targetObject.getClass().getMethod(this.methodName, this.parameterTypes);
    }

    public Object invoke(Object targetObject) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = targetObject.getClass().getMethod(this.methodName, this.parameterTypes);
        return method.invoke(targetObject, this.arguments);
    }
}