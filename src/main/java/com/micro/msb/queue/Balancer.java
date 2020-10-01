package com.micro.msb.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

@Component
public class Balancer extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(Balancer.class);

    @Value("${queue.balancer.name}")
    private String name;    
    @Value("${queue.balancer.router}")
    private String tcpBindRouter;
    @Value("${queue.balancer.dealer}")
    private String tcpBindDealer;

    @Override
    public void run() {

        LOG.info("Initiate Queue "+name+"...");

        Context context = ZMQ.context(1);

        Socket frontend = context.socket(ZMQ.ROUTER);
        frontend.bind(tcpBindRouter);

        Socket backend = context.socket(ZMQ.DEALER);
        backend.bind(tcpBindDealer);

        ZMQ.proxy(frontend, backend, null);

        frontend.close();
        backend.close();
        context.term();
    }
}
