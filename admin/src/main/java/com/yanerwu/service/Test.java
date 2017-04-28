package com.yanerwu.service;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author: Zuz
 * @Description:
 * @Date: 2017/4/28 09:30
 */
public class Test {
    public static void main(String[] args) throws UnknownHostException {
        new InetSocketTransportAddress(InetAddress.getByName("106.14.171.91"), 9300);


        // on startup

        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("106.14.171.91"), 9300));

        // on shutdown

        client.close();
    }
}
