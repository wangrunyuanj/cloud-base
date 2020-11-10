package com.runyuanj.config;

import org.springframework.cloud.gateway.support.ipresolver.RemoteAddressResolver;
import org.springframework.cloud.gateway.support.ipresolver.XForwardedRemoteAddressResolver;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    RemoteAddressResolver resolver = XForwardedRemoteAddressResolver.maxTrustedIndex(1);

}
