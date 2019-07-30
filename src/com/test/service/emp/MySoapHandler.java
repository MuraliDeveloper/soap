package com.test.service.emp;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

public class MySoapHandler implements SOAPHandler<SOAPMessageContext> {

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		System.out.println("Enter handleMessage");
		Boolean isRequest = (Boolean) context
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (!isRequest) {

			try {
				SOAPMessage soapMsg = context.getMessage();
				// handleHeaders(soapMsg, soapEnv);
				soapMsg.writeTo(System.out);
			} catch (SOAPException e) {
				System.err.println(e);
			} catch (IOException e) {
				System.err.println(e);
			}

		}

		// continue other handler chain
		return true;
	}

	private void handleHeaders(SOAPMessage soapMsg)
			throws SOAPException {
		SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
		SOAPHeader soapHeader = soapEnv.getHeader();
		if (soapHeader == null) {
			soapHeader = soapEnv.addHeader();
			generateSOAPErrMessage(soapMsg, "Error::No SOAP header.");
		}
		Iterator it = soapHeader
				.extractHeaderElements(SOAPConstants.URI_SOAP_ACTOR_NEXT);
		// if no header block for next actor found? throw exception
		if (it == null || !it.hasNext()) {
			  generateSOAPErrMessage(soapMsg,
			 "No header block for next actor.");
		}

		// if no mac address found? throw exception
		Node macNode = (Node) it.next();
		String macValue = (macNode == null) ? null : macNode.getValue();

		if (macValue == null) {
			generateSOAPErrMessage(soapMsg, "No mac address in header block.");
		}

		if (!macValue.equals("90-4C-E5-44-B9-8F")) {
			generateSOAPErrMessage(soapMsg,
					"Invalid mac address, access is denied.");
		}
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {

		System.out.println("Server : handleFault()......");

		return true;
	}

	@Override
	public void close(MessageContext context) {
		System.out.println("Server : close()......");
	}

	@Override
	public Set<QName> getHeaders() {
		System.out.println("Server : getHeaders()......");
		return null;
	}

	private void generateSOAPErrMessage(SOAPMessage msg, String reason) {
		try {
			SOAPBody soapBody = msg.getSOAPPart().getEnvelope().getBody();
			SOAPFault soapFault = soapBody.addFault();
			soapFault.setFaultString(reason);
			throw new SOAPFaultException(soapFault);
		} catch (SOAPException e) {
		}
	}

}