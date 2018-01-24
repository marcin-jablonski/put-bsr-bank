package com.bank.client;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.PortInfo;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomHeaderHandler implements HandlerResolver, SOAPHandler<SOAPMessageContext> {
    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty) {
            context.getMessage();
            try {
                SOAPMessage message = context.getMessage();
                SOAPPart part = message.getSOAPPart();
                SOAPEnvelope envelope = part.getEnvelope();

                if (envelope.getHeader() != null) {
                    envelope.getHeader().detachNode();
                }

                SOAPHeader header = envelope.addHeader();
                SOAPElement authorizationHeaderElement = header.addChildElement("AuthorizationHeader", "bank", "http://www.bank.com/types");
                SOAPElement usernameElement = authorizationHeaderElement.addChildElement("username", "bank", "http://www.bank.com/types");
                usernameElement.addTextNode(CredentialsManager.getInstance().getUsername());
                SOAPElement passwordElement = authorizationHeaderElement.addChildElement("password", "bank", "http://www.bank.com/types");
                passwordElement.addTextNode(CredentialsManager.getInstance().getPassword());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                context.getMessage();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return outboundProperty;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    @Override
    public void close(MessageContext context) {

    }

    @Override
    public List<Handler> getHandlerChain(PortInfo portInfo) {
        List<Handler> chain = new ArrayList<>();
        CustomHeaderHandler handler = new CustomHeaderHandler();
        chain.add(handler);
        return chain;
    }
}
