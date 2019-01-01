package com.demo.admin.scheduler;

import io.netty.util.internal.StringUtil;

public final class AccessTokenWrapper {
 
    private static AccessTokenWrapper INSTANCE;
    private String accessToken = StringUtil.EMPTY_STRING;
     
    private AccessTokenWrapper() {        
    }
     
    public static AccessTokenWrapper getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new AccessTokenWrapper();
        }
         
        return INSTANCE;
    }

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
 
    // getters and setters
}