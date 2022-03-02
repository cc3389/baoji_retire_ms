package com.wit.baojims.controller;

import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MemberControllerTest {

    @Test
    void out() {
        String host = "";
        String ip = "";

        InetAddress ia = null;
        try {
            ia = InetAddress.getLocalHost();

            host = ia.getHostName();//获取计算机名字

            ip = ia.getHostAddress();//获取IP
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        System.out.println(host);
        System.out.println(ip);
    }
}